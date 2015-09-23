# Ghost Word Game Design Document

* Michiel Boswijk, michiel.boswijk@gmail.com
* University of Amsterdam, 10332553

This is a design document for an implementation of the Ghost game (https://en.wikipedia.org/wiki/Ghost_(game)). The application, called Ghost Word Game, will run on Android devices (both smartphones and tablets). For a list of features and UI walkthrough, please check out the README file in the Ghost Word Game project.

## Controllers / Activitiy classes with menu items

##### IntroScreen

![Intro](/Sketches/intro.JPG)

##### Highscores

![Highscores](/Sketches/highscores.JPG)

##### Settings

![Settings](/Sketches/settings.JPG)

##### NameInput

![Names](/Sketches/names.JPG)

##### GameScreen

![Game](/Sketches/game.JPG)

##### WinnerScreen

![Winner](/Sketches/winner.JPG)

## Additional Classes

##### Classes with some variables / functions

![Classes](/Sketches/classes.JPG)

##### Some interactions between classes

![interactions](/Sketches/interactions.JPG)

## Visual Representation

Color Palette: Grey/Green or Grey/Orange (two themes)

Drawables:

* Ghost Icon (Launcher and IntroScreen)
* Play Icon (IntroScreen, Highscores, NameInput)
* Highscores icon (IntroScreen, WinnerScreen)
* Information icon (IntroScreen and HowToPlay dialog)
* Settings icon (Introscreen)
* Enter icon (GameScreen)
* Star icon: gold, silver, bronze, and green
* Ribbon image/icon (Highscores)
* Cup image/icon (WinnerScreen)
* (Optional: ) Handshake icon (Acknowledgements dialog)

### Acknowledgements

* (Future) Icons designed by Mees Daalder