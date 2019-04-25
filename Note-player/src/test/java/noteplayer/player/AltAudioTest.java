package noteplayer.player;

import java.io.File;
import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class AltAudioTest {
    
    static Audio audio;
    static String DS;
    static String regexDS;
    
    @BeforeClass
    public static void createAudio()    {
        audio = new Audio();
        if (System.getProperty("os.name").contains("Windows"))   {
            DS = "\\";
            regexDS = "\\\\";
        } else  {
            DS = "/";
            regexDS = "/";
        }
    }
    
    @Test
    public void audioIsPlayed() {
        audio.play("test_audio"+DS+"ukulele.wav");
        // a delay is needed, as the file takes time to open
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        assertTrue(audio.isPlaying());
    }
    
    @Test
    public void audioIsPlayed2()    {
        audio.play("ukulele.wav", "test_audio");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        assertTrue(audio.isPlaying());
    }
    
    @Test
    public void audioCanBePaused()  {
        audio.play("test_audio"+DS+"ukulele.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        audio.pausePlayback();
        assertFalse(audio.isPlaying());
    }
    
    @Test
    public void audioCanBeUnpaused()    {
        audio.play("test_audio"+DS+"ukulele.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        audio.pausePlayback();
        audio.unpausePlayback();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        assertTrue(audio.isPlaying());
    }
    
    @Test
    public void openingNewFileStopsCurrentPlayback()    {
        audio.play("test_audio"+DS+"ukulele.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        audio.play("non-existant file");
        assertFalse(audio.isPlaying());
    }
    
    @Test
    public void playingWhenGivenAFileWorks()    {
        File testFile = new File("test_audio"+DS+"ukulele.wav");
        audio.play(testFile);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        assertTrue(audio.isPlaying());
    }
    
    @Test
    public void givingANonValidFile()   {
        File testFile = new File("test_audio"+DS+"test_folder"+DS+"test.file");
        audio.play(testFile);
        assertFalse(audio.isPlaying());
    }
    
    @Test
    public void givingANonExistantFile()  {
        audio.play("some song");
        assertFalse(audio.isPlaying());
    }
    
    @Test
    public void givingANonExistantFile2()  {
        audio.play("some song", "some_directory");
        assertFalse(audio.isPlaying());
    }
}
