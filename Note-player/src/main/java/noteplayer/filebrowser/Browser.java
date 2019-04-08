package noteplayer.filebrowser;

import java.io.File;
import noteplayer.player.Audio;

public class Browser {
    
    File currentDirectory;
    // "directory separator", "/" on unix and "\" on windows
    final private String DS;
    
    public Browser() {
        currentDirectory = new File(".");
        if (System.getProperty("os.name").contains("Windows"))   {
            DS = "\\";
        } else  {
            DS = "/";
        }
    }
    
    public String getCurrentDirectory() {
        return currentDirectory.toString();
    }
    
    public boolean moveUpOneDirectory() {
        String curDir = getCurrentDirectory();
        // do nothing if already at root directory
        if (curDir.equals("."))   {
            return false;
        }
        String[] levels = curDir.split(DS);
        //System.out.println(levels[levels.length - 1]);
        String newDir = curDir.replace(DS + levels[levels.length - 1], "");
        //System.out.println(newDir);
        currentDirectory = new File(newDir);
        return true;
    }
    
    public boolean changeDirectory(String s)   {
        // TODO: moving up a directory when input is ".."
        if (s.equals("."))  {
            currentDirectory = new File(".");
            return true;
        }
        if (directoryExists(s)) {
            currentDirectory = new File(s);
            return true;
        }
        return false;
    }
    
    public boolean changeDirectoryOrPlay(File f, Audio audio)   {
        if (f.isDirectory())    {
            String s = f.toString();
            if (s.equals("."))  {
                currentDirectory = new File(".");
                return true;
            }
            if (directoryExists(s)) {
                currentDirectory = new File(s);
                return true;
            }
            return false;
        } else  {
            audio.play(f.toString());
            return true;
        }
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
    
    public String[] listFilesFormatted()    {
        File[] files = currentDirectory.listFiles();
        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++)  {
            String name = files[i].toString();
            String[] split = name.split(DS);
            name = split[split.length - 1];
            if (name.length() > 20) {
                name = name.substring(0, 19) + "...";
            }
            names[i] = name;
        }
        return names;
    }
    
    public String[] listFilesString()  { 
        File[] files = currentDirectory.listFiles();
        if (files == null)  {
            String[] list = {""};
            return list;
        }
        String[] returnList = new String[files.length];
        
        for (int i = 0; i < files.length; i++)  {
            returnList[i] = files[i].toString();
        }
        
        return returnList;
    }
    
}
