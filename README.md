# Note-player

Currently, Note-player has a graphical file browser and audio player. Users can save notes related to certain songs, which will auto-load from a database when the song is played again. There is also adjustable auto-scrolling of notes, for chord-diagrams for an example.

## Releases

[v0.1-beta](https://github.com/Teo44/ot-harjoitustyo/releases/tag/v0.1.-beta)

## Documentation

[Specifications](https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/specifications.md)

[Work hours](https://github.com/Teo44/ot-harjoitusotyo/blob/master/documentation/work_hours.md)

[Architecture](https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/architecture.md)

## Required libraries

The JavaFX mediaplayer used in the application might require additional libraries depending on the operating system. 

### Windows

Untested.

### Ubuntu

Packages libavutil51, libavformat53 and libavcodec53 might be needed (untested). 

### OS X

Seems to work out of the box.

### Arch

Aur-package ffmpeg-compat-55 is required.



## Usage

### Generating jar-binary

Run in the Note-player directory
```
mvn package
```

### Running from source

#### GUI

After cloning the repository, the GUI can be ran with
```
mvn compile exec:java -Dexec.mainClass=noteplayer.ui.Ui
```

#### Command-line

There is also a command-line interface for testing purposes
```
mvn compile exec:java -Dexec.mainClass=noteplayer.player.Main
```

Entering a filepath to a .wav file will play the file.

Two royalty-free tracks have been included in the repository for testing:

```
test_audio/ukulele.wav

test_audio/guitar.wav
```

List files in current directory:
```
list
```
Move to a directory:
```
cd directory
```

Move back to the root directory:
```
cd .
```

Exiting the application (currently has a long delay when running in the terminal):

```
quit
```

## Tests

Sometimes the tests can require multiple runs to pass, due to the mysterious nature of the JavaFX mediaplayer.

Note: 

Running tests

```
mvn test
```

Generating a test coverage report

```
mvn test jacoco:report
```

Checkstyle
```
mvn jxr:jxr checkstyle:checkstyle
```

Generating javadoc
```
mvn javadoc:javadoc
```
