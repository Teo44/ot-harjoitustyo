package noteplayer.filebrowser;

import java.io.File;
import java.util.*;

public class Browser {
    
    File currentDirectory;
    
    public Browser() {
        currentDirectory = new File(".");
    }
    
    public String getCurrentDirectory() {
        return currentDirectory.toString();
    }
    
    public boolean changeDirectory(String s)   {
        // TODO: moving up a directory when input is ".."
        if (s.equals("."))  {
            currentDirectory = new File(".");
            return true;
        }
        if (directoryExists(currentDirectory.toString() + "/"+s)) {
            currentDirectory = new File("./"+s);
            return true;
        }
        return false;
    }
    
    private boolean directoryExists(String d)   {
        for (File f : listFiles())    {
            if (f.toString().equals(d)) {
                if (f.isDirectory())    {
                    return true;
                }
            }
        }
        return false;
    }

    
    public File[] listFiles()  {
        return currentDirectory.listFiles();
    }
    
    public String[] listFilesString()  { 
        File[] files = currentDirectory.listFiles();
        if (files == null)  {
            return null;
        }
        String[] returnList = new String[files.length];
        
        for (int i = 0; i < files.length; i++)  {
            returnList[i] = files[i].toString();
        }
        
        return returnList;
    }
    
}
