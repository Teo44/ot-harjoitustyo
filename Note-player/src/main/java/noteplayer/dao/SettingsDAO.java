package noteplayer.dao;

import java.sql.*;

/**
 * Saves setting parameters to the database.
 * 
 * @author teo
 */
public class SettingsDAO {
    
    private String db;
    
    /**
     * Creates a database file if it doesn't exists.
     * Also creates the Settings table if it doesn't exists.
     * 
     * @param fontSize The default font size, used only when the table is created. 
     * @param theme The default theme, used only when the table is created.
     * @param db The name of the database file.
     */
    public SettingsDAO(Integer fontSize, Integer theme, String db)   {
        this.db = db;
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:./" + db, "sa", "");
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Settings (\n"
                    + "id INTEGER PRIMARY KEY,\n"
                    + "fontSize INTEGER,\n"
                    + "theme INTEGER\n"
                    + ");");
            statement.execute();
            
            PreparedStatement defaultCheck = connection.prepareStatement("SELECT * FROM Settings "
                    + "WHERE Settings.id = 1;");
            
            ResultSet res = defaultCheck.executeQuery();
            
            if (!(res.next()))   {
                PreparedStatement defaultSettings = connection.prepareStatement("INSERT INTO Settings ("
                        + "id, fontSize, theme) VALUES (?, ?, ?)");
                defaultSettings.setInt(1, 1);
                defaultSettings.setInt(2, fontSize);
                defaultSettings.setInt(3, theme);
                defaultSettings.execute();
                defaultSettings.close();
            }
            
            
            
            defaultCheck.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    /**
     * Returns the saved theme from database, stored as an integer.
     * 
     * @param defaultValue This is returned if the database query fails.
     * @return Theme as an integer.
     */
    public int getTheme(int defaultValue)    {
        int fontSize = defaultValue;
        try {
            Connection con = openConnection();
            PreparedStatement getStmt = con.prepareStatement("SELECT * FROM Settings "
                    + "WHERE Settings.id = 1;");
            ResultSet res = getStmt.executeQuery();
            if (res.next()) {
                fontSize = res.getInt("theme");
            }
            res.close();
            getStmt.close();
            con.close();
            
        } catch (Exception e)   {   
        }
        return fontSize;
    }
    
    /**
     * Attempts to save the theme to the database.
     * 
     * @param value The theme.
     */
    public void setTheme(int value)   {
        try {
            Connection con = openConnection();
            PreparedStatement setStmt = con.prepareStatement("UPDATE Settings "
                    + "SET theme = ? "
                    + "WHERE Settings.id = 1;");
            setStmt.setInt(1, value);
            setStmt.execute();
            setStmt.close();
            con.close();
            
        } catch (Exception e)   {
            System.out.println(e);
        }
    }
    
    /**
     * Attempts to save the font size to the database.
     * 
     * @param fontSize The font size.
     */
    public void setFontSize(int fontSize)   {
        try {
            Connection con = openConnection();
            PreparedStatement setStmt = con.prepareStatement("UPDATE Settings "
                    + "SET fontSize = ? "
                    + "WHERE Settings.id = 1;");
            setStmt.setInt(1, fontSize);
            setStmt.execute();
            setStmt.close();
            con.close();
            
        } catch (Exception e)   {
            System.out.println(e);
        }
    }
    
    /**
     * Attempts to get the font size from the database.
     * 
     * @param defaultValue This is returned if the query fails.
     * @return The font size.
     */
    public int getFontSize(int defaultValue)    {
        int fontSize = defaultValue;
        try {
            Connection con = openConnection();
            PreparedStatement getStmt = con.prepareStatement("SELECT * FROM Settings "
                    + "WHERE Settings.id = 1;");
            ResultSet res = getStmt.executeQuery();
            if (res.next()) {
                fontSize = res.getInt("fontSize");
            }
            res.close();
            getStmt.close();
            con.close();
            
        } catch (Exception e)   {   
        }
        return fontSize;
    }
    
    private Connection openConnection() throws Exception    {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./" + db, "sa", "");
        return connection;
    }
    
}
