import easygui as gui
import sys
import demo3_register
import demo3_matcher


# main running function for demo2
def demo3_run():
    enter = gui.buttonbox(msg='Welcome to Chat Health, what can we do for you?', title='Chat Health platform',
                          choices=('register & appoint', 'meet with doctor', 'quit'))
    # for register and appoint function
    if enter == 'register & appoint':
        demo3_register.start()
    # for face matching
    elif enter == 'meet with doctor':
        demo3_matcher.start()
    # end the use of demo2
    else:
        gui.msgbox(msg='The platform service is ending', title='Chat Health platform', ok_button='OK')
        sys.exit(0)


demo3_run()
