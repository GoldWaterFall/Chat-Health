import easygui as gui
import random
import os
import demo2_library

def start():
    enter = gui.buttonbox(msg='What service do you need?', title='Chat Health platform',
                          choices=('register', 'appoint', 'quit'))
    # for register function
    if enter == 'register':
        gui.msgbox(msg='Please upload your card photo', title='Chat Health platform', ok_button='OK')
        # generate unique id for new user
        path = gui.fileopenbox(msg='Please select your card photo file', title='Chat Health platform')
        new_id = demo2_library.get_id(path)
        gui.msgbox(msg='Your unique ID is ' + new_id, title='Chat Health platform', ok_button='OK')
        path = './data/' + new_id
        os.mkdir(path)
        demo2_library.take_photo(new_id, 'register')
        if gui.ccbox('Do you wish to continue for appointment?', title='Chat Health platform',
                     choices=('yes', 'no')):
            demo2_library.take_photo(new_id, 'appoint')
    # for appoint function
    elif enter == 'appoint':
        # enter the unique id
        enter_id = gui.enterbox(msg='Please enter your unique ID', title='Chat Health platform')
        if enter_id not in demo2_library.ids():
            gui.msgbox(msg='Sorry, the unique ID does not exist, please firstly register',
                       title='Chat Health platform', ok_button='OK')
        else:
            demo2_library.take_photo(enter_id, 'appoint')
    # end the use of demo2
    else:
        gui.msgbox(msg='The platform service is ending', title='Chat Health platform', ok_button='OK')

