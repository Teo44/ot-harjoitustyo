package noteplayer.player;

import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class AudioTest {
    
    static Audio audio;
    
    @BeforeClass
    public static void createAudio()    {
        audio = new Audio();
    }
    
    @Test
    public void audioIsPlayed() {
        audio.play("test_audio/ukulele.wav");
        // a delay is needed, as the file takes time to open
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        assertTrue(audio.isPlaying());
    }
    
    @Test
    public void audioCanBePaused()  {
        audio.play("test_audio/ukulele.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        audio.pausePlayback();
        assertFalse(audio.isPlaying());
    }
    
    @Test
    public void audioCanBeUnpaused()    {
        audio.play("test_audio/ukulele.wav");
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
        audio.play("test_audio/ukulele.wav");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {}
        audio.play("non-existant file");
        assertFalse(audio.isPlaying());
    }
}
