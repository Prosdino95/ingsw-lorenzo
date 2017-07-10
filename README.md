# Prova Finale (Ingegneria del Software)

## Implemented project requisites

### Game-specific requisites

We chose to implement the complete rules of the game (the only missing
thing is the bonus tile choice before the start of the game, using
advanced rules).

### Game-agnostic requisites

* All the server requisites.
* All the client requisites.
* All the configuration requisites (for the specific files see a later section).
* All the before start and during the game requisites.

## Running the game

To get the server up and running, first run the server class located
in the reti.server package.  By running the mainView class in the
gameview package you will be presented with the Socket/RMI and the
GUI/CLI choices.  The latter will run one of these two classes:
GUIView in gameview.gui or CLIView in gameview.cli (running one of
these directly will by default choose the socket connection).

When started, the server will automatically create a GameManager
object which holds the players that are going to participate in the
next game. When 4 players join, the game might start with 2 or 3
players, if the "join delay" time passed without anyone joining the game.

From now on, the server will notify the player that should play
next. If he doesn't send a request before the "action delay" time passes,
he will be marked as dead and the game will proceed normally: the
"dead" player will just pass the turn without doing any action.
If the game is running he might still be able to rejoin the game by
sending any request.
The player will be marked as dead even if he gets disconnected. In
this case, he won't be able to rejoin the game.

### The UI

Playing in the CLI is sufficiently self-explanatory.

Playing with the GUI needs a bit of an explanation.

The GUI can be showing one of these 4 screens:

* Board screen
* Request screen
* First player board screen
* Second player board screen

The user can navigate between the screens using the A and D keys.

The GUI can be in one of these 3 states:

* Question state
* Action state
* Idle state

When in the **question state**, the user will see the question and the
various choices on the left part of the request screen. He should
choose the answer and send it by pushing the Send Request button.

The **action state** means that it's the user's turn, he can place a
family member by selecting an action space and a family member by
pressing on them in the board screen, adding servants if needed and
sending the request. He might also want to do a leader card action, by
clicking repeatedly on a leader card he can choose between one of the
possible leader card actions.  Once the user is satisfied, he can push
the Finish Action button.  The action might also terminate if the user
(probably trying to understand the UI) lets the "action delay" time
pass.

During both the question and action state in the request screen the
user can see the request that's getting formed in the upper right
corner.

The **idle state** means it's someone else's turn, the player can
still navigate in the GUI but he can't send requests to the server.
Hopefully the user will not, in the hours waiting for other users to
play, test our application's robustness by trying every possible
button combination or by trying to clog the server by sending
multiple inconsistent requests.


## Configuration

The configuration files are written in the json format and are located
in the Config directory.
Configurability is limited and as such it needs to be handled with
care: unexpected changes may lead to incoherences or may cause a
crash.

## UML

We have uploaded 4 UML diagrams, one for each of the model, view and
controller. One also roughly showing the interconnections between the
three of them. Due to the complexity of the diagrams, we choosed to
not have a unique diagram spanning the whole MVC. For the same reason,
for the Model UML we have decided not to put much emphasis on the
interconnections between the single classes but to show more generally
which packages are the most in relation to which.
