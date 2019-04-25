# How to use Note-player

## Installation

Download the latest jar from the [releases.](https://github.com/Teo44/ot-harjoitustyo/releases)
Run the jar from a filemanager, or from a terminal with
```
java -jar Note-player-x.x.jar
```

## Usage

### Audio playback

Use the file browser on the left side to navigate to your music files and simply click a file to start playback. The currently playing file will be displayed in the top bar, next to the player controls. Note that the files must currently be in the same directory as the program. 

Above the file browser are the audio players control, from left to right: volue slider, move playback to beginning/previous file, pause/play, next file and repeat toggle.

### Notes

#### Adding notes

To add a note to a song, type into the text area when a song is playing (or paused), and press the 'save note' button in the top left corner to save the note to database.

#### Loading notes

When a song is chosen, the program will automatically load its existing note, no user intervention is required.

#### Adjusting font size

Note font size can be adjusted with the '-' and '+' buttons in the top bar. It is also automatically saved when the program is exited.

### Autoscroll

Autoscroll is meant for automatically scrolling a song's chord diagram written in the note while the song plays; Note-players original intented use. 

Autoscroll can be started with the 'Start autoscroll' button, and stopped with the same button when it reads 'Stop autoscroll'. Autoscroll also automatically stops upon reaching the bottom of the note.

The duration setting is roughly the time in seconds it takes for the autoscroll to reach the bottom of the note. This setting is saved for a song when the 'save note' button is pressed.

#### Theme

Note-players has a light and a dark theme, which can be changed with the 'Light/Dark theme' button. The chosen theme is automatically saved when the program is exited.
