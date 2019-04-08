# Note-player

Currently, the only functions of Note-player are playing back .wav files and limited file browsing through a terminal. A GUI is in the works. See specifications for all the planned features.

## Documentation

[Specifications](https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/specifications.md)

[Work hours](https://github.com/Teo44/ot-harjoitusotyo/blob/master/documentation/work_hours.md)

[Architecture](https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/architecture.md)

## Usage

After cloning the repository, the GUI can be ran with
```
mvn compile exec:java -Dexec.mainClass=noteplayer.ui.Ui
```

There is also a command-line interface for testing
```
mvn compile exec:java -Dexec.mainClass=noteplayer.player.Main
```

Entering a filepath to a .wav file will play the file.

For testing purposes, two royalty-free tracks have been included in the repository:

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
