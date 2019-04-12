package noteplayer.dao;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import org.junit.BeforeClass;
import org.junit.Test;

public class NoteDAOTest {
    
    static TestNoteDAO noteDAO;
    
    @BeforeClass
    public static void createAudio()    {
        noteDAO = new TestNoteDAO();
    }
    
    @Test
    public void savingNotesWorks1() {
        noteDAO.saveNote("song name", "note text");
        assertEquals("note text", noteDAO.getSongNote("song name"));
    }
    
    @Test
    public void songWithoutNoteReturnsEmptyString()    {
        assertEquals("", noteDAO.getSongNote("some song"));
    }
    

}
