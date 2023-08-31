import easygui as gui
import os
import sys
import cv2 as cv
import face_recognition


# collect existing ids to decide whether the enter id exists
def ids():
    id_path = './data/'
    id_name = os.listdir(id_path)
    id_name.append('')
    return id_name


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

