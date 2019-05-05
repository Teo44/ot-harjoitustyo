# Testing document

## Unit and integration testing

The FxPlayer, Browser and DAO-classes have all been tested with JUnit tests. The Audio class has been omitted from the testing due to problems with 
its audio playback on some systems, and it only being used in the command-line interface anymore.

### File browsing

File browsing is tested with the included audio files and folders. The browser tests also include integration testing, which test playing audio 
using both the browser and the player-classes. The nextFile() and previousFile() methods aren't tested very well due to files changing order between systems, making testing these difficult. 

### Audio playback

In addition to the integration tests with the file browser, the audio player is tested in a vacuum using the included audio files. Due to the class 
using the JavaFX Mediaplayer, the JavaFxThreadingRule-class is used for the initialisation of JavaFX during testing.

### DAO-classes

The NoteDAO and FileDAO classes are both tested using a test database file "test_db". Exceptions when accessing the database are not tested.

### Test coverage

Test coverage, with the UI-classes and the deprecated, problematic Audio-class ignored.

<img src="https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/screenshots/jacoco.png">

While the row coverage is decent at 88%, the branch coverage is fairly low at 67%. This is mainly because of some interactions with files being difficult to test on multiple systems, and some JavaFX methods not working in tests, although working when manually tested.


## Manual testing

The program as a whole has been extensively tested by hand, on different Linux distributions, OSX and Windows. Both installing and using the 
program have been tested in these different environments. 

## Remaining problems

The program does not currently function optimally on Windows, due to the file system functioning differently with java. On Windows the file browsing 
starts in the system's root directory, and doesn't seem to fuction as intented in general. As such, the program is currently best used only on Unix 
systems.
