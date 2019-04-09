package noteplayer.dao;

import java.util.HashMap;

public class TestNoteDAO {
    
    HashMap<String, String> notes;
    
    public TestNoteDAO()    {
        
        notes = new HashMap<>();
        
    }
    
    public String getSongNote(String song)  {
        return notes.getOrDefault(song, "");
    }
    
    public boolean noteForSongExists(String song)    {
        return notes.containsKey(song);
    }
    
    public void saveNote(String currentSong, String noteText) {
        if (currentSong == null)    {
            return;
        }
        notes.put(currentSong, noteText);
    }
    
}
