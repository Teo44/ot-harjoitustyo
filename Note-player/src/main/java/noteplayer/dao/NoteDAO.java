package noteplayer.dao;

import java.sql.*;

public class NoteDAO {
    
    String currentNote;
    String db;
    
    public NoteDAO(String db)   {
        this.db = db;
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:./" + db, "sa", "");
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Notes (\n"
                    + "id INTEGER PRIMARY KEY,\n"
                    + "songname varchar(144),\n"
                    + "note varchar(1440)\n"
                    + ");");
            statement.execute();

            statement.close();
            connection.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    public String getSongNote(String song)  {
        String note = "";
        try {
            Connection con = openConnection();
            String getNote = "SELECT Notes.note FROM Notes WHERE"
                    + " Notes.songname = ?";
            PreparedStatement stmt = con.prepareStatement(getNote);
            stmt.setString(1, song);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                note = res.getString("note");
            }
            con.close();
        }
        catch(Exception e)  {}
        return note;
    }
    
    private Connection openConnection() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./"+db, "sa", "");
        return connection;
    }
    
    public boolean noteForSongExists(String song)    {
        return false;
    }
    
    public void saveNote(String currentSong, String noteText){
        if (currentSong == null)    {
            return;
        }
        try {
            Connection con = openConnection();
            // Check if a note for the song already exists
            PreparedStatement check = con.prepareStatement("SELECT * FROM NOTES "
                    + "WHERE Notes.songname = ?;");
            check.setString(1, currentSong);
            ResultSet res = check.executeQuery();
            // Delete possible existing note
            if (res.next()) {
                String deleteStmt = "DELETE FROM Notes WHERE Notes.songname = ?;";
                PreparedStatement delete = con.prepareStatement(deleteStmt);
                delete.setString(1, currentSong);
                delete.execute();
                delete.close();
            }
            check.close();
            res.close();

            // Create new note
            String insertStmt = "INSERT INTO Notes (songname, note) VALUES (?, ?)";
            PreparedStatement insert = con.prepareStatement(insertStmt);
            insert.setString(1, currentSong);
            insert.setString(2, noteText);
            insert.execute();
            insert.close();
            con.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
