# Note-player

Currently, the only functions of Note-player are playing back .wav files and limited file browsing through a terminal. A GUI is in the works. See specifications for all the planned features.

## Documentation

[Specifications](https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/specifications.md)

[Work hours](https://github.com/Teo44/ot-harjoitusotyo/blob/master/documentation/work_hours.md)

[Architecture](https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/architecture.md)

## Required libraries

The JavaFX mediaplayer used in the application can require additional libraries depending on the operating system and other factors. For Ubuntu, packages libavutil51, libavformat53 and libavcodec53 might be needed (untested). For Arch, ffmpeg-compat-55 is needed (tested).

## Usage

### GUI

After cloning the repository, the GUI can be ran with
```
mvn compile exec:java -Dexec.mainClass=noteplayer.ui.Ui
```

### Command-line

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
