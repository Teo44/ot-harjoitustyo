package noteplayer.dao;

import java.nio.file.Files;
import java.nio.file.Paths;
import static junit.framework.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class SettingsDAOTest {
    
    static SettingsDAO settings;
    
    @BeforeClass
    public static void createAudio()    {
        settings = new SettingsDAO(14, 0, "test_db");
    }
    
    @Test
    public void fontSizeChangingWorks()  {
        settings.setFontSize(18);
        assertTrue(18 == settings.getFontSize(14));
    }
    
    @Test
    public void themeSavingWorks()   {
        settings.setTheme(1);
        assertTrue(1 == settings.getTheme(0));
    }
    
    @Test
    public void creatingNewDatabaseFile()   {
        try {
            Files.deleteIfExists(Paths.get("test_db"));
        } catch (Exception e)   {
        }
        
        settings = new SettingsDAO(14, 0, "test_db");
        assertTrue(14 == settings.getFontSize(12));
    }
    
    
}
