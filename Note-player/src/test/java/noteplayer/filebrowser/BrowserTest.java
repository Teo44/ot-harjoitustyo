package noteplayer.filebrowser;

import java.io.File;
import java.util.Arrays;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import org.junit.BeforeClass;
import org.junit.Test;

public class BrowserTest {
    
    static Browser browser;
    static String DS;
    
    @BeforeClass
    public static void createBrowser()  {
        browser = new Browser();
        if (System.getProperty("os.name").contains("Windows"))   {
            DS = "\\";
        } else  {
            DS = "/";
        }
    }
    
    @Test
    public void changingDirectoryWorks()    {
        browser.changeDirectory("." + DS + "test_audio");
        browser.changeDirectory("." + DS + "test_audio" + DS + "test_folder");
        assertEquals("[."+DS+"test_audio"+DS+"test_folder"+DS+"test.file]", 
                Arrays.toString(browser.listFilesString()));
        
    }
    
    @Test
    public void goingBackToHomeDirectoryWorks() {
        browser.changeDirectory("." + DS + "test_audio");
        browser.changeDirectory(".");
        assertEquals(".", browser.getCurrentDirectory());
        
    }
    
    @Test
    public void changingDirectoryToFileDoesntDoAnything()   {
        browser.changeDirectory("." + DS + "test_audio");
        browser.changeDirectory("." + DS + "test_audio" + DS + "test_folder");
        browser.changeDirectory("." + DS + "test_audio" + DS + "test_folder" + DS + "test_file");
        assertEquals("[."+DS+"test_audio"+DS+"test_folder"+DS+"test.file]", 
                Arrays.toString(browser.listFilesString()));
        
    }
    
    @Test
    public void formattedFileListingWorks() {
        browser.changeDirectory("." + DS + "test_audio");
        browser.changeDirectory("." + DS + "test_audio" + DS + "test_folder");
        String[] files = browser.listFilesFormatted();
        assertEquals("test.file", files[0]);
    }
    
    @Test
    public void fileListingWorks()  {
        browser.changeDirectory("." + DS + "test_audio");
        browser.changeDirectory("." + DS + "test_audio" + DS + "test_folder");
        File[] files = browser.listFiles();
        assertEquals("." + DS + "test_audio" + DS + "test_folder" + DS + "test.file", files[0].toString());
    }
    
    @Test
    public void movingUpADirectory()  {
        browser.changeDirectory("." + DS + "test_audio");
        browser.changeDirectory("." + DS + "test_audio" + DS + "test_folder");
        browser.moveUpOneDirectory();
        assertEquals("." + DS + "test_audio", browser.getCurrentDirectory());
    }
    
    @Test
    public void movingUpADirectoryAtRoot()  {
        browser.changeDirectory(".");
        browser.moveUpOneDirectory();
        assertEquals(".", browser.getCurrentDirectory());
    }
    
    @Test
    public void nextSongWhenDoesntExist()   {
        TestPlayer player = new TestPlayer();
        browser.changeDirectory("." + DS + "test_audio" + DS + "test_folder");
        player.setCurrentlyPlaying("test.file");
        browser.nextSong(player);
        assertEquals("test.file", player.getCurrentlyPlayingString());
    }
    
    @Test
    public void previousSongWhenDoesntExist()   {
        TestPlayer player = new TestPlayer();
        browser.changeDirectory("." + DS + "test_audio" + DS + "test_folder");
        player.setCurrentlyPlaying("test.file");
        browser.previousSong(player);
        assertEquals("test.file", player.getCurrentlyPlayingString());
    }
    
    @Test
    public void playOrChangeDirectory() {
        TestPlayer player = new TestPlayer();
        browser.changeDirectory("." + DS + "test_audio");
        File song = new File("." + DS + "test_audio" + DS + "guitar.wav");
        browser.changeDirectoryOrPlay(song, player);
        assertEquals("." + DS + "test_audio" + DS + "guitar.wav", 
                player.getCurrentlyPlayingString());
    }
    
        @Test
        public void playOrChangeDirectory2() {
        TestPlayer player = new TestPlayer();
        browser.changeDirectory("." + DS + "test_audio");
        File folder = new File("." + DS + "test_audio" + DS + "test_folder");
        browser.changeDirectoryOrPlay(folder, player);
        assertNull(player.getCurrentlyPlayingString());
    }
}
