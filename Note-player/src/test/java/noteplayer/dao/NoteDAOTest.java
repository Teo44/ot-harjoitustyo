package noteplayer.dao;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class NoteDAOTest {
    
    static NoteDAO noteDAO;
    
    @BeforeClass
    public static void createAudio()    {
        noteDAO = new NoteDAO("test_db");
    }
    
    @Test
    public void savingNotesWorks1() {
        noteDAO.saveNoteAndScrollSpeed("song name", "note text", 40);
        assertEquals("note text", noteDAO.getSongNote("song name"));
    }
    
    @Test
    public void savingScrollSpeedWorks() {
        noteDAO.saveNoteAndScrollSpeed("song name", "note text", 40);
        assertTrue(40 == noteDAO.getSongScrollSpeed("song name", 60));
    }
    
    @Test
    public void songWithoutNoteReturnsEmptyString()    {
        assertEquals(" ", noteDAO.getSongNote("some song"));
    }
    

}
