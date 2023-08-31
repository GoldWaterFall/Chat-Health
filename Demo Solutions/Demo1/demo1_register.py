import easygui as gui
import random
import os
import demo1_library

def start():
    enter = gui.buttonbox(msg='What service do you need?', title='Chat Health platform',
                          choices=('register', 'appoint', 'quit'))
    # for register function
    if enter == 'register':
        gui.msgbox(msg='Start create your unique ID', title='Chat Health platform', ok_button='OK')
        # generate unique id for new user
        new_id = ''
        while new_id in demo1_library.ids():
            seed = '1234567890'
            id = []
            new_id = ''
            for i in range(4):
                id.append(random.choice(seed))
                new_id = ''.join(id)
        gui.msgbox(msg='Your unique ID is ' + new_id, title='Chat Health platform', ok_button='OK')
        path = './data/' + new_id
        os.mkdir(path)
        demo1_library.take_photo(new_id, 'register')
        if gui.ccbox('Do you wish to continue for appointment?', title='Chat Health platform',
                     choices=('yes', 'no')):
            demo1_library.take_photo(new_id, 'appoint')
    # for appoint function
    elif enter == 'appoint':
        # enter the unique id
        enter_id = gui.enterbox(msg='Please enter your unique ID', title='Chat Health platform')
        if enter_id not in demo1_library.ids():
            gui.msgbox(msg='Sorry, the unique ID does not exist, please firstly register',
                       title='Chat Health platform', ok_button='OK')
        else:
            demo1_library.take_photo(enter_id, 'appoint')
    # end the use of demo1
    else:
        gui.msgbox(msg='The platform service is ending', title='Chat Health platform', ok_button='OK')

