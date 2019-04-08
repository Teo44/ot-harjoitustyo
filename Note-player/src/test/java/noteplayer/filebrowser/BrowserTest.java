package noteplayer.filebrowser;

import java.util.Arrays;
import static junit.framework.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class BrowserTest {
    
    static Browser browser;
    static String DS;
    
    @BeforeClass
    public static void createBrowser()  {
        browser = new Browser();
        if (System.getProperty("os.name").equals("Windows"))   {
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
    
}
