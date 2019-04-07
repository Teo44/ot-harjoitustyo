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
        browser.changeDirectory("./test_audio");
        assertEquals("[./test_audio/credits.txt, ./test_audio/guitar.wav, ./test_audio/test_folder, ./test_audio/ukulele.wav]", 
                Arrays.toString(browser.listFilesString()));
        
    }
    
    @Test
    public void goingBackToHomeDirectoryWorks() {
        browser.changeDirectory("./test_audio");
        browser.changeDirectory(".");
        assertEquals(".", browser.getCurrentDirectory());
        
    }
    
    @Test
    public void changingDirectoryToFileDoesntDoAnything()   {
        browser.changeDirectory("./test_audio");
        browser.changeDirectory("ukulele.wav");
        assertEquals("[./test_audio/credits.txt, ./test_audio/guitar.wav, ./test_audio/test_folder, ./test_audio/ukulele.wav]", 
                Arrays.toString(browser.listFilesString()));
        
    }
    
}
