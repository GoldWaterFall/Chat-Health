import easygui as gui
import demo3_library

def start():
    enter = gui.buttonbox(msg='What service do you need?', title='Chat Health platform',
                          choices=('meet with the doctor', 'quit'))
    # for face matching
    if enter == 'meet with the doctor':
        # enter the unique ID
        enter_id = gui.enterbox(msg='Please enter your unique ID', title='Unique ID system')
        if enter_id not in demo3_library.ids():
            gui.msgbox(msg='Sorry, the unique ID does not exist, please firstly register',
                       title='Unique ID system', ok_button="OK")
        else:
            # compare face to decide whether the same person
            demo3_library.take_photo(enter_id, 'match')
            match_result = demo3_library.match('./data/' + enter_id + '/')
            if match_result:
                gui.msgbox(msg='You are the same person', title='Face photo collection', ok_button='OK')
            else:
                gui.msgbox(msg='You are not the same person', title='Face photo collection', ok_button='OK')
    # end the use of demo3
    else:
        gui.msgbox(msg='The platform service is ending', title='Chat Health platform', ok_button='OK')


