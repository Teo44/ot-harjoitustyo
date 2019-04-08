package noteplayer.dao;

import static junit.framework.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class NoteDAOTest {
    
    static NoteDAO noteDAO;
    
    @BeforeClass
    public static void createAudio()    {
        noteDAO = new NoteDAO();
    }
    
    @Test
    public void savingNotesWorks1() {
        noteDAO.saveNote("song name", "note text");
        assertEquals("note text", noteDAO.getSongNote("song name"));
    }
    

}
