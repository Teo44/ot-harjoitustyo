package noteplayer.dao;

import java.sql.*;

/**
 * Saves songs' notes and scrollspeeds to the database and 
 * queries them back.
 * 
 * @author teo
 */
public class NoteDAO {
    
    String currentNote;
    String db;
    
    /**
     * Constructor creates a database file if it doesn't exist.
     * Also creates the Notes table if it doesn't exists.
     * 
     * @param db name of the database file 
     */
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
    
    /**
     * Gets the note for a song from database, if it exists.
     * 
     * TODO: return value should be null when no note is found,
     * but this breaks the application as of now.
     * 
     * @param song any identifier, e.g. song name or file path
     * @return related note, or " " if no note is found.
     */
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
    
    /**
     * Gets the scroll speed for a song from database, if it exists.
     * 
     * @param song any identifier, e.g. song name or file path
     * @param old the current scroll speed
     * @return song's saved scroll speed, or the current one if there
     * isn't one in the database
     */
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

    /**
     * Saves a song's note and scroll speed to the database. Any previous
     * data for the song is overwritten.
     * 
     * @param currentSong any identifier, e.g. song name or file path
     * @param note the song's note
     * @param speed the song's scroll speed
     */
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
        }
    }
}
