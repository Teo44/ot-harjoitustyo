package noteplayer.dao;

import java.sql.*;

public class SettingsDAO {
    
    public SettingsDAO(Integer fontSize)   {
        
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:./notes", "sa", "");
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Settings (\n"
                    + "id INTEGER PRIMARY KEY,\n"
                    + "fontSize INTEGER\n"
                    + ");");
            statement.execute();
            
            PreparedStatement defaultCheck = connection.prepareStatement("SELECT * FROM Settings "
                    + "WHERE Settings.id = 1;");
            
            ResultSet res = defaultCheck.executeQuery();
            
            if (!(res.next()))   {
                PreparedStatement defaultSettings = connection.prepareStatement("INSERT INTO Settings ("
                        + "id, fontSize) VALUES (?, ?)");
                defaultSettings.setInt(1, 1);
                defaultSettings.setInt(2, fontSize);
                defaultSettings.execute();
                defaultSettings.close();
            }
            
            
            
            defaultCheck.close();
            statement.close();
            connection.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
    
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
    
    public int getFontSize()    {
        int fontSize = 14;
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
            
        } catch (Exception e)   {}
        return fontSize;
    }
    
    private Connection openConnection() throws Exception    {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./notes", "sa", "");
        return connection;
    }
    
}
