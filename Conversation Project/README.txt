README::

How the system is executed:
The system must be run on Windows.  The file is called "Survivor.jar".  

To run the file... 
--open up the command prompt and navigate to the exact folder containing the .jar file.
--Type "Java -jar Survivor.jar".  The program should run in the command prompt console.  It ends after 30 turns.

How the system works:
The System has Nine classes, including a main class, a super state, an initial state, and six other states.  Each state extends the 'State' class and implements most of the methods in this class itself.  When the user runs the program, it is in the initial state.  The program always begins the conversation to set the topic.  The user then enters his input, which the main class, Survivor sends to the current state.  The current state identifies its key words in the user's input, ignoring all other words.  It then checks which of its hard-coded outputs to generate.  If certain key words are detected, the program will generate a String output "*CHANGE_STATE=<newstate> <user's input here>".  When the main class sees the '*' at the beginning of the String, it changes its current state to "<newstate>" and sends the user's input to that state.  The new state then handles this input.

Source files:
Survivor.java: The main class.
State.java: The "super state" class which all classes extend.  This is also used as the pointer to the current state in the main class
InitialState.java: The program starts off in this state.  The program can go from this state to any other state (apart from the super state)
GeneralBeliefState.java: When the program suspects the user believes its story, it is in this state.  All outputs for this state are determined by this class.
GeneralDisbeliefState.java: When the program suspects the user does not believe its story, it is in this state.  All outputs for this state are determined by this class.
CrazyState.java: When the program believes the user thinks it is crazy, it is in this state.  All outputs for this state are determined by this class.
LyingState.java: When the program believes the user thinks it is lying, it is in this state.  All outputs for this state are determined by this class.
HelpState.java: When the program believes the user is trying to help it, it is in this state.  All outputs for this state are determined by this class.
ComfortState.java: When the program believes the user is trying to console it, it is in this state.  All outputs for this state are determined by this class.

The program does not use any external libraries or APIs.

Known bugs and issues:

In the "Comfort" state, when the user talks about Nightmares, the program generates the default response rather than correctly mentioning that it does not have nightmares.

In the GeneralDisbelief state, when the user asks why it should believe the program, the program generates the default response rather than answering the question

When the user enters: "I honestly wouldn't care if you were mauled by a rabid bear. I don't know you so go die in a plane crash."  The program outputs the state transition string, which is not meant to be seen by the user, and is meant to be handled by the main class.

In the General Belief state, when the user enters "We should do something", the program outputs the default response instead of correclty changing states to "HelpState"

The program is also in dire need of refactoring.  It is badly organized and implemented.  Many of the afforementioned issues may not exist if the code was easier to understand and modify.