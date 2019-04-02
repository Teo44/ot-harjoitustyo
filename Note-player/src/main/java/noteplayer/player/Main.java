package noteplayer.player;

import java.util.*;
import filebrowser.Lister;


public class Main {
    
    public static void main(String[] args)  {
        System.out.print("Enter a filename to play it, ");
        System.out.println("\"pause\" or \"unpause\" to control playback:");
        
        
        Scanner scanner = new Scanner(System.in);
        Audio audio = new Audio();
        Lister lister = new Lister();
        
        // Temporary command line UI, wont be needed later
        while (true)    {
            String input = scanner.nextLine();
            
            if (input.equals("quit"))   {
                audio.pausePlayback();
                break;
            }
            else if (input.equals("pause")) {
                audio.pausePlayback();
            }
            else if (input.equals("list") || input.equals("ls"))  {
                printFiles(lister);
            }
            else if (input.equals("unpause"))   {
                audio.unpausePlayback();
            }
            else if (input.equals("isplaying")) {
                System.out.println(audio.isPlaying());
            }
            else audio.play(input);
            
        }
        
    }
    
    
    
    private static void printFiles(Lister lister)    {
        
        for (String s : lister.listFilesString()) {
            System.out.println(s);
        }
        
    }
    
}
