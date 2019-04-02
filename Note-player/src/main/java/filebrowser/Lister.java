package filebrowser;

import java.io.File;
import java.util.*;

public class Lister {
    
    File currentDirectory;
    
    public Lister() {
        currentDirectory = new File(".");
    }
    
    //TODO: get current directory, change directory

    
    public String[] listFilesString()  {
        //ArrayList<String> returnList = new ArrayList<>();
        
        File[] files = currentDirectory.listFiles();
        String[] returnList = new String[files.length];
        
        for (int i = 0; i < files.length; i++)  {
            returnList[i] = files[i].toString();
        }
        
        return returnList;
    }
    
}
