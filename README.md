# Ghost Word Game Application Proposal

* Michiel Boswijk, michiel.boswijk@gmail.com
* University of Amsterdam, 10332553

This is a proposal for an implementation of the Ghost game (https://en.wikipedia.org/wiki/Ghost_(game)). The application, called Ghost Word Game, will run on Android devices (both smartphones and tablets). It will be a two-player game with the following features.

## Features

* Players are remembered after playing
* Scores are remembered after playing (Highscore List)
* Dutch and English language support
* Allows for in-game language switch
* Choice of themes
* Allows for in-game theme switch
* Sound effects
* Allows for disabling of sound effects
* Option to clear highscores/names
* Prevents loss of gamestate after focus switch of device
* Ability to start new game at all times
* Ability to change names at all times

##### Uncertain features

* Versus Computer Option
* Sound selection

### User Experience

The user is first presented with the initial screen (Screen 1). From here it has the options to start the game (Screen 2), check how to play (Extra screen 1), apply some settings (Extra screen 2), or check the highscores (Screen 5).  When the user presses the 'Play' button, it is take to a screen to select his name (Screen 2). There the user (each of the two users that is) has the option to select an existing name, or input a new game (which will take the user to Screen 2a/b). After all names are selected, the main screen is loaded (Screen 3). The name of the player (upper right and left of the screen) is in bold font when it is that users turn, and the word is displayed in the middle. When the game ends, the players are taken to the congratulations screen (Screen 4). Here the player has the option go directly to the hihscores list (or it can select other options for the menu, which are shown on Extra screen 3). From the highscore list, the player can directly start a new game, or again select from the options menu.

##### Sketches:

![First three screens](/Sketches/initial_three_screens.jpg?raw=true "Sketch 1.")

![Second three screens](/Sketches/second_three_screens.jpg)

![Extra screens](/Sketches/extra_screens.jpg)

### Acknowledgements

* Icons designed by Mees Daalder
