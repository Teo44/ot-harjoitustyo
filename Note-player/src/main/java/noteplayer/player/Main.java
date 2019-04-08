package noteplayer.player;

import java.util.*;
import noteplayer.filebrowser.Browser;


public class Main {
    
    public static void main(String[] args)  {
        System.out.print("Enter a filename to play it, ");
        System.out.println("\"pause\" or \"unpause\" to control playback:");
        
        
        Scanner scanner = new Scanner(System.in);
        Browser lister = new Browser();
        Audio audio = new Audio();
        
        // Temporary command line UI, wont be needed later
        while (true)    {
            String input = scanner.nextLine();
            
            if (input.equals("quit"))   {
                audio.pausePlayback();
                break;
            } else if (input.equals("pause")) {
                audio.pausePlayback();
            } else if (input.equals("list") || input.equals("ls"))  {
                printFiles(lister);
            } else if (input.equals("unpause"))   {
                audio.unpausePlayback();
            } else if (input.equals("isplaying")) {
                System.out.println(audio.isPlaying());
            } else if (input.equals("cd ..")) {
                System.out.println(lister.moveUpOneDirectory());
            } else if (input.matches("cd .*")) {
                // testing
                System.out.println("changing directory");
                String [] s = input.split(" ");
                if (s.length == 2)  {
                    //lister.changeDirectory(s[1]
                    
                    // print is for testing
                    System.out.println(lister.changeDirectory(s[1]));
                }
            } else if (input.equals("os")) {
                System.out.println(System.getProperty("os.name"));
            } else  {
                audio.play(input, lister.getCurrentDirectory());
            }
            
        }
        
    }
    
    
    
    private static void printFiles(Browser lister)    {
        //System.out.println(Arrays.toString(lister.listFilesString()));
        if (lister.listFilesString() == null)   {
            return;
        }
        for (String s : lister.listFilesString()) {
            System.out.println(s);
        }
        
    }
    
}
