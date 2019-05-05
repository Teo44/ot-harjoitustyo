package noteplayer.player;

import java.io.File;
import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class AudioTest {
    
    static FxPlayer audio;
    static String DS;
    static String regexDS;
    
    @Rule public JavaFxThreadingRule javafxRule = new JavaFxThreadingRule();
    
    @BeforeClass
    public static void createAudio()    {
        audio = new FxPlayer();
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
    
    @Test
    public void repeatWorks1()  {
        audio.play("test_audio"+DS+"ukulele.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        assertTrue(audio.toggleRepeat());
    }
    
    @Test
    public void repeatWorks2()  {
        // no idea why this needs a new player to be created
        // here, testing javafx classes is weird
        audio = new FxPlayer();
        audio.play("test_audio"+DS+"ukulele.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        audio.toggleRepeat();
        audio.toggleRepeat();
        assertFalse(audio.isOnRepeat());
    }
    
    @Test
    public void togglePauseWorks() {
        audio.play("test_audio"+DS+"ukulele.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        audio.togglePause();
        audio.togglePause();
        assertTrue(audio.isPlaying());
    }
    
    @Test
    public void prevWorksWhenUnder5Seconds()    {
        audio.play("test_audio"+DS+"ukulele.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        assertTrue(audio.prev());
    }
    
    @Test
    public void prevWorksWhenNotPlaying()    {
        assertFalse(audio.prev());
    }
    
    @Test
    public void getCurrentlyPlayingString() {
        audio.play("test_audio"+DS+"ukulele.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        assertEquals("test_audio"+DS+"ukulele.wav", audio.getCurrentlyPlayingString());
    }
    
    @Test
    public void getCurrentlyPlayingString2()    {
        assertNull(audio.getCurrentlyPlayingString());
    }
    
    @Test
    public void getCurrentlyPlayingFormattedString()    {
        audio.play("test_audio"+DS+"ukulele.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        assertEquals("ukulele.wav", audio.getCurrentlyPlayingFormattedString());
    }
    
    @Test
    public void getCurrentlyPlayingFormattedStringasd2()    {
        // new audio needs to be created, since the file from the previous 
        // test is still playing here for some reason
        audio = new FxPlayer();
        assertNull(audio.getCurrentlyPlayingFormattedString());
    }
    
    @Test
    public void changingVolume()    {
        audio.play("test_audio"+DS+"ukulele.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        audio.setVolume(0.3);
        assertEquals(0.3, audio.getVolume());
    }
    
    @Test
    public void volumeWhenNotPlaying()  {
        assertEquals(0.0, audio.getVolume());
    }
    
}
