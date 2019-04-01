
package noteplayer.player;

import java.util.*;


public class Main {
    
    public static void main(String[] args)  {
        System.out.print("Enter a filename to play it, ");
        System.out.println("\"pause\" or \"unpause\" to control playback:");
        
        
        Scanner scanner = new Scanner(System.in);
        Audio audio = new Audio();
        
        
        while (true)    {
            String input = scanner.nextLine();
            
            if (input.equals("quit")) break;
            else if (input.equals("pause")) audio.pausePlayback();
            else if (input.equals("unpause")) audio.unpausePlayback();
            else if (input.equals("isplaying")) System.out.println(audio.isPlaying());
            else audio.play(input);
            
        }
        
    }
    
}
