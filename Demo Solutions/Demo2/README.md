# How to use this Demo2?

Just run demo2.py which is the center program for this project, before successfully run the codes, you need to ensure the required modules has been installed, the python environment for this code is Python==3.7, the required modules include:

easygui==0.98.3

face_recognition==1.3.0

opencv_python==3.4.2.17

pytesseract==0.3.10

It need to be clarified that you may meet some problem when downloading the face_recognition module, you may need to download dlib and cmake module before it, here is a reference (https://blog.csdn.net/qq_43466323/article/details/120855699) for installing them, we use the third method to solve this problem. Also, we recommend to create an virtual environment with Anaconda to avoid version conflict. Also, we use pytesseract to solve the OCR problem, which only works well on English-based identification, you can try other methods if you want to imply it to other languages.

# What can you do with this Demo?

When you run the demo2.py, there are three buttons in the main menu: "register & appoint", "meet with doctor", and "exit". "register & appoint" is to make account registration and then relate the account to individual appointments with the user's face recorded. "meet with doctor" is for patients to get their face matched with the one recorded during Appointment Stage. Phase 1: Account Registration After clicking the "register & appoint" button, the user can then click "register", the patient need to select the path of id card photo and circle the area of the id part, the system can generate the same unique ID accodring to your card photo and then users' faces are recorded. Phase 2: Make an appointment After the registration, the app will ask the user whether to continue for appointment? If the user clicks "yes", then face collection starts. Phase 3: Meet with doctor If the patient hope to meet with the doctor, after entering the registration ID. The program will compare the patient's face with registrant's face. If the same, successful. If different, mistaken.

There is a simple design structure for this demo, you can find it under this menu.
