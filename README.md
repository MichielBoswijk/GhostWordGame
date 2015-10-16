# Ghost Word Game

* Michiel Boswijk, michiel.boswijk@gmail.com
* University of Amsterdam, 10332553

This is the final version (Alpha release) of the Ghost Word Game.

To Download the project, go to tab `releases', and then click on the desired release. The most recent release is called 'Alpha1.0'. After that you can download the project. Since this project was made in Android Studio, you will need this software to run the project. After you have downloaded the project, extract it and place the project where you want. Now in Android Studio, press CTRL + O to open a project, navigate to the project folder and click on the GhostWordGame folder. After you press OK, you will be able to run the application on a device or emulator (by pressing Shift + F10).

#### Version
1.0

#### Requirements

An android device with minimal SKD 14 (from this version the game guarantees a stable version).
The targeted SDK is 23.
A device with at least 1 GB of RAM is adviced.

## Features

* Players are remembered after playing
* Scores are remembered after playing (Highscore List)
* Dutch and English language support
* Choice of themes
* Option to clear highscores/names
* Prevents loss of gamestate after focus switch of devicees

##### Possible future features

* Versus Computer Option
* Sound options

### Inital Draft of User Experience

The user is first presented with the initial screen (Screen 1). From here it has the options to start the game (Screen 2), check how to play (Extra screen 1), apply some settings (Extra screen 2), or check the highscores (Screen 5).  When the user presses the 'Play' button, it is take to a screen to select his name (Screen 2). There the user (each of the two users that is) has the option to select an existing name, or input a new game (which will take the user to Screen 2a/b). After all names are selected, the main screen is loaded (Screen 3). The name of the player (upper right and left of the screen) is in bold font when it is that users turn, and the word is displayed in the middle. When the game ends, the players are taken to the congratulations screen (Screen 4). Here the player has the option go directly to the hihscores list (or it can select other options for the menu, which are shown on Extra screen 3). From the highscore list, the player can directly start a new game, or again select from the options menu.

##### Initial Sketches:

![First three screens](/Sketches/initial_three_screens.JPG?raw=true "Sketch 1.")

![Second three screens](/Sketches/second_three_screens.JPG)

![Extra screens](/Sketches/extra_screens.JPG)

### Acknowledgements

* (Future) Icons designed by Mees Daalder

