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
        browser.changeDirectory("./test_audio/test_folder");
        assertEquals("[./test_audio/test_folder/test.file]", 
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
        browser.changeDirectory("./test_audio/test_folder");
        browser.changeDirectory("./test_audio/test_folder/test.file");
        assertEquals("[./test_audio/test_folder/test.file]", 
                Arrays.toString(browser.listFilesString()));
        
    }
    
}
