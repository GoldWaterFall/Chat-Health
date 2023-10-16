import easygui as gui
import os
import cv2 as cv
import face_recognition
import pytesseract

global img, point1, point2, text


# collect existing ids to decide whether the enter id exists
def ids():
    id_path = './data/'
    id_name = os.listdir(id_path)
    id_name.append('')
    return id_name


# select the area of if

def on_mouse(event, x, y, flags, param):
    global img, point1, point2, text
    img2 = img.copy()
    if event == cv.EVENT_LBUTTONDOWN:
        point1 = (x, y)
        cv.circle(img2, point1, 10, (0, 255, 0), 1)
    elif event == cv.EVENT_MOUSEMOVE and (flags & cv.EVENT_FLAG_LBUTTON):
        cv.rectangle(img2, point1, (x, y), (255, 0, 0), 1)
    elif event == cv.EVENT_LBUTTONUP:
        point2 = (x, y)
        cv.rectangle(img2, point1, point2, (0, 0, 255), 1)
        min_x = min(point1[0], point2[0])
        min_y = min(point1[1], point2[1])
        width = abs(point1[0] - point2[0])
        height = abs(point1[1] - point2[1])
        cut_img = img[min_y:min_y + height, min_x:min_x + width]
        cv.imshow('card', cut_img)
        text = pytesseract.image_to_string(cut_img, lang="eng")


# read image and use ocr to identify the id on it
def get_id(path):
    global img, text
    img = cv.imread(path)
    cv.namedWindow('id card')
    id = cv.setMouseCallback('id card', on_mouse)
    cv.imshow('id card', img)
    cv.waitKey(0)
    return text[: -1]


# function for collecting face photo
def take_photo(id, period):
    gui.msgbox(msg='Start taking photo for you', title='Face photo collection', ok_button='OK')
    cap = cv.VideoCapture(0)
    while cap.isOpened():
        ret, frame = cap.read()
        cv.imshow('Face capture', frame)
        k = cv.waitKey(1) & 0xFF
        if k == ord('s'):
            cv.imwrite('./data/' + id + '/' + period + '.jpg', frame)
            gui.msgbox(msg='Photo has been collected', title='Face photo collection', ok_button='OK',
                       image='./data/' + id + '/' + period + '.jpg')
            break
    cap.release()


# function for face detection and comparison
def match(path):
    ap_image = face_recognition.load_image_file(path + 'appoint.jpg')
    ap_image = cv.cvtColor(ap_image, cv.COLOR_BGR2RGB)
    ap_image = cv.resize(ap_image, (0, 0), None, 0.5, 0.5)
    ma_image = face_recognition.load_image_file(path + 'match.jpg')
    ma_image = cv.cvtColor(ma_image, cv.COLOR_BGR2RGB)
    ma_image = cv.resize(ma_image, (0, 0), None, 0.5, 0.5)

    face = face_recognition.face_locations(ap_image)[0]
    ap_encode = face_recognition.face_encodings(ap_image)[0]
    cv.rectangle(ap_image, (face[3], face[0]), (face[1], face[2]), (255, 0, 255), 2)

    face = face_recognition.face_locations(ma_image)[0]
    ma_encode = face_recognition.face_encodings(ma_image)[0]
    cv.rectangle(ma_image, (face[3], face[0]), (face[1], face[2]), (255, 0, 255), 2)

    results = face_recognition.compare_faces([ap_encode], ma_encode, 0.4)
    cv.imshow('left', ap_image)
    cv.imshow('right', ma_image)
    cv.waitKey(600)

    if results[0]:
        return True
    else:
        return False
