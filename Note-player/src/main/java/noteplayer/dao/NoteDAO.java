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
                    + "note varchar(1440),\n"
                    + "scrollspeed INTEGER\n"
                    + ");");
            statement.execute();

            statement.close();
            connection.close();
        } catch (Exception e)  {
        }
        
    }
    
    public String getSongNote(String song)  {
        String note = " ";
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
            res.close();
            con.close();
        } catch (Exception e)  {   
        }
        return note;
    }
    
    public Integer getSongScrollSpeed(String song, Integer old)  {
        
        try {
            Connection con = openConnection();
            String getSpeed = "SELECT Notes.scrollspeed FROM Notes WHERE"
                    + " Notes.songname = ?";
            PreparedStatement stmt = con.prepareStatement(getSpeed);
            stmt.setString(1, song);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                old = res.getInt("scrollspeed");
            }
            res.close();
            con.close();
        } catch (Exception e)  {
        }
        return old;
    }
    
    private Connection openConnection() throws Exception    {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./" + db, "sa", "");
        return connection;
    }
    
    public boolean noteForSongExists(String song)    {
        return false;
    }
    
    public void saveNoteAndScrollSpeed(String currentSong, String note, Integer speed)  {
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
            // Update an existing row
            if (res.next()) {
                String deleteStmt = "UPDATE Notes SET note = ?, scrollspeed = ? WHERE Notes.songname = ?;";
                PreparedStatement delete = con.prepareStatement(deleteStmt);
                delete.setString(1, note);
                delete.setInt(2, speed);
                delete.setString(3, currentSong);
                delete.execute();
                delete.close();
            } else  {
                // Create new row
                String insertStmt = "INSERT INTO Notes (songname, note, scrollspeed) VALUES (?, ?, ?)";
                PreparedStatement insert = con.prepareStatement(insertStmt);
                insert.setString(1, currentSong);
                insert.setString(2, note);
                insert.setInt(3, speed);
                insert.execute();
                insert.close();
            }
            check.close();
            res.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void saveNote(String currentSong, String noteText)   {
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
            // Update an existing row
            if (res.next()) {
                String deleteStmt = "UPDATE Notes SET Notes.note = ? WHERE Notes.songname = ?;";
                PreparedStatement delete = con.prepareStatement(deleteStmt);
                delete.setString(1, noteText);
                delete.setString(2, currentSong);
                delete.execute();
                delete.close();
            } else  {
                // Create new row
                String insertStmt = "INSERT INTO Notes (songname, note) VALUES (?, ?)";
                PreparedStatement insert = con.prepareStatement(insertStmt);
                insert.setString(1, currentSong);
                insert.setString(2, noteText);
                insert.execute();
                insert.close();
                con.close();
            }
            check.close();
            res.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
