# Architecture

## Structure

The program structure consists of two layers, the UI and the logic. The UI responds to user actions by sending simple method calls to the player, file browser and dao-classes, which handle all of the program logic. When the browser and player need to interact, dependency injection is used to avoid having program logic in the UI.

<img src="https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/pictures/class_diagram.png">

## GUI

The GUI has a single view, which consist of a file list, note area and controls for playback and other settings. These are created using different JavaFX objects, and placed in a BorderPane. 

Pictured below: file list on the left, controls and settings along the top, the rest is for notes.

<img src="https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/screenshots/basic_view_1.png">

The alternative dark theme:

<img src="https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/screenshots/basic_view_2.png">



## Program logic

### File browsing and audio playback

File browsing and playing audio and handled by the browser and player-classes respectively. 

The browser provides list of files to be shown to the user, handles changing directory, provides the player with audio files and the NoteDAO with song names. Below are some of the methods of the browser.

- public String getCurrentDirectory() 
- public String[] listFilesFormatted()
- public boolean changeDirectory(String newDirectory)
- public void nextSong(FxPlayer player)

The player-class plays audio files and controls their playback, currently using the JavaFX Mediaplayer. Below are some of its methods.

- public void play(File file)
- public String getCurrentlyPlayingString()
- public void togglePause()
- public boolean isPlaying()  
- public void setVolume(double vol)  
- public boolean toggleRepeat()

A sequence diagram of how the program might be used:

<img src="https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/pictures/sequence_diagram_1.png">

### Database

The database is used through two Data Access Object - classes, NoteDAO and SettingsDAO. They both store data in a .db file using SQLite.

NoteDAO is used to save per song data, which currently includes a note and and autoscrolling duration. These are saved manually by the user, and loaded automatically when a song is played. Saved data is identified by the filename of the song.

SettingsDAO can save the user's settings, which are the chosen theme and font size. These are only saved when the program is exited, and loaded when it starts up.

Database diagram:

<img src="https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/pictures/database_diagram.png">
