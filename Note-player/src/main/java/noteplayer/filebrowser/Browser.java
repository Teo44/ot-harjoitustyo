package noteplayer.filebrowser;

import java.io.File;
import noteplayer.player.FxPlayer;

/**
 * Used for listing and accessing files.
 * 
 * @author teo
 */
public class Browser {
    
    File currentDirectory;
    // "directory separator", "/" on unix and "\" on windows
    final private String directorySeparator;
    final private String regexDS;
    
    /**
     * Sets the applications directory as the current directory.
     * TODO: on windows this gets set to the root directory, 
     * which is quite unideal
     * 
     * Sets the DS (directory separator) final variables depending
     * on the OS, as Windows uses "\" and Unix "/"
     */
    public Browser() {
        currentDirectory = new File(".");
        if (System.getProperty("os.name").contains("Windows"))   {
            directorySeparator = "\\";
            regexDS = "\\\\";
        } else  {
            directorySeparator = "/";
            regexDS = "/";
        }
    }
    
    public String getCurrentDirectory() {
        return currentDirectory.toString();
    }
    
    /**
     * Calls the player's prev() method. If return value is true, 
     * attempts to find the previous song in the current directory.
     * If found, it is passed to the audio player.
     * 
     * @see noteplayer.player.FxPlayer#prev() 
     * 
     * @param player Dependency injection.
     */
    public void previousSong(FxPlayer player)   {
        if (!(player.prev()))   {
            return;
        }
        File next = previousFile(player.getCurrentlyPlayingString());
        if (next == null)   {
            return;
        }
        player.play(next);
    }
    
    /**
     * Attempts to find the next song in the current directory.
     * If found, it is passed to the audio player.
     * 
     * @param player Dependency injection for playing back the file.
     */
    public void nextSong(FxPlayer player)   {
        File next = nextFile(player.getCurrentlyPlayingString());
        if (next == null)   {
            return;
        }
        player.play(next);
    }
    
    private File previousFile(String current)    {
        File[] files = listFiles();
        for (int i = files.length - 1; i >= 0; i--)  {
            if (files[i].toString().equals(current))   {
                for (int j = i - 1; j >= 0; j--)  {
                    if (files[j].isFile())  {
                        return files[j];
                    }
                }
            }
        }
        return null;
    }
    
    private File nextFile(String current)    {
        File[] files = listFiles();
        for (int i = 0; i < files.length; i++)  {
            if (files[i].toString().equals(current))   {
                for (int j = i + 1; j < files.length; j++)  {
                    if (files[j].isFile())  {
                        return files[j];
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Attempts to move up one directory in the file system.
     * Does nothing if already at root.
     * 
     * @return True if successful, false if already at root.
     */
    public boolean moveUpOneDirectory() {
        String curDir = getCurrentDirectory();
        // do nothing if already at root directory
        if (curDir.equals("."))   {
            return false;
        }
        String[] levels = curDir.split(regexDS);
        String newDir = curDir.replace(directorySeparator + levels[levels.length - 1], "");
        currentDirectory = new File(newDir);
        System.out.println(currentDirectory.toString());
        return true;
    }
    
    /**
     * Moves to the given directory.
     * 
     * @param s The directory as a string.
     * 
     * @return True if successful. 
     */
    public boolean changeDirectory(String s)   {
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
    
    /**
     * Either moves to a directory or attempts to play back a file, 
     * depending if the given file is a directory or not.
     * 
     * @param f The file to move to or play.
     * @param audio Dependency injection for playing back the file.
     * @return True if successfully moved directory or sent file to player.
     */
    public boolean changeDirectoryOrPlay(File f, FxPlayer audio)   {
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

    /**
     * Lists all files in the current directory.
     * 
     * @return An array containing all files in current directory.
     */
    public File[] listFiles()  {
        return currentDirectory.listFiles();
    }
    
    /**
     * Lists all the filenames in the current directory, with 
     * the preceding folders removed using the regexDS variable,
     * 
     * @return An array containing the formatted file names.
     */
    public String[] listFilesFormatted()    {
        File[] files = currentDirectory.listFiles();
        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++)  {
            String name = files[i].toString();
            String[] split = name.split(regexDS);
            name = split[split.length - 1];
            names[i] = name;
        }
        return names;
    }
    
    /**
     * Lists all the filenames in the current directory as is, 
     * containing all the preceding folders.
     * 
     * @return An array containing the full file names.
     */
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
