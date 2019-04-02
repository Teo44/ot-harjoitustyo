package noteplayer.filebrowser;

import java.util.Arrays;
import static junit.framework.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class BrowserTest {
    
    static Browser browser;
    
    @BeforeClass
    public static void createBrowser()  {
        browser = new Browser();
    }
    
    @Test
    public void changingDirectoryWorks()    {
        browser.changeDirectory("test_audio");
        assertEquals(Arrays.toString(browser.listFilesString()), 
                "[./test_audio/credits.txt, ./test_audio/ukulele.wav, ./test_audio/guitar.wav, ./test_audio/test_folder]");
        
    }
    
    @Test
    public void goingBackToHomeDirectoryWorks() {
        browser.changeDirectory("test_audio");
        browser.changeDirectory(".");
        assertEquals(browser.getCurrentDirectory(), ".");
        
    }
    
    @Test
    public void changingDirectoryToFileDoesntDoAnything()   {
        browser.changeDirectory("test_audio");
        browser.changeDirectory("ukulele.wav");
        assertEquals(Arrays.toString(browser.listFilesString()), 
                "[./test_audio/credits.txt, ./test_audio/ukulele.wav, ./test_audio/guitar.wav, ./test_audio/test_folder]");

    }
    
}
