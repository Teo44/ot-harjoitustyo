# Note-player



## Documentation

[Specifications](https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/specifications.md)

[Work hours](https://github.com/Teo44/ot-harjoitustyo/blob/master/documentation/work_hours.md)

## Usage

After cloning the repository, the application can currently be ran with 

'''
mvn compile exec:java -Dexec.mainClass=noteplayer.player.Main
'''

Entering a filepath (with the project folder as root) to a .wav file will play the file.

For testing purposes, two royalty-free tracks have been included in the repository:

'''
test_audio/ukulele.wav

test_audio/guitar.wav
'''

Exiting the application (currently has a long delay when running in the terminal):

'''
quit
'''

## Tests

Running tests

'''
mvn test
'''

Generating a test coverage report

'''
mvn test jacoco:report
