package noteplayer.player;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;



public class FxPlayer {
    
    final private String DS;
    final private String regexDS;
    Media media;
    MediaPlayer mediaPlayer;
    String currentlyPlaying;
    
    
    public FxPlayer()  {
        if (System.getProperty("os.name").contains("Windows"))   {
            DS = "\\";
            regexDS = "\\\\";
        } else  {
            DS = "/";
            regexDS = "/";
        }
    }
    
     public void play(String file, String curDir)  {
        
        // Stop any ongoing playback before starting the new one
        if (mediaPlayer != null)   {
            mediaPlayer.stop();
        }
        
        try {
            media = new Media(new File(curDir + DS + file).toURI().toString());
            currentlyPlaying = curDir + DS + file;
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
        } catch (Exception e)   {
            System.out.print("Could not play file: ");
            System.out.println(e);
        }
        
    }
     
    public void play(File file)  {
        
        // Stop any ongoing playback before starting the new one
        if (mediaPlayer != null)   {
            mediaPlayer.stop();
        }
        
        try {
            media = new Media(file.toURI().toString());
            currentlyPlaying = file.toURI().toString();
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
        } catch (Exception e)   {
            System.out.print("Could not play file: ");
            System.out.println(e);
        }
        
    }
    
    public void play(String file)  {
        
        // Stop any ongoing playback before starting the new one
        if (mediaPlayer != null)   {
            mediaPlayer.stop();
        }
        
        try {
            media = new Media(new File(file).toURI().toString());
            currentlyPlaying = file;
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
        } catch (Exception e)   {
            System.out.print("Could not play file: ");
            System.out.println(e);
        }
        
    }
    
    public String getCurrentlyPlayingFormattedString()  {
        if (media == null)  {
            return null;
        }
        //String song = mediaPlayer.toString();
        String[] split = currentlyPlaying.split(regexDS);
        return split[split.length - 1];
    }
    
    public String getCurrentlyPlayingString()   {
//        if (media == null)  {
//            return null;
//        }
//        return media.toString();
          return currentlyPlaying;
    }
    
    public void prev()  {
        if (mediaPlayer == null)    {
            return;
        }
        // starts the song from the beginning
        if (isPlaying())    {
            mediaPlayer.stop();
            mediaPlayer.play();
        } else  {
            mediaPlayer.stop();
        }
        // TODO: if the song is in the first 5 (?) seconds, 
        // attempt to move to the previous song (the next song above
        // in the directory?
    }
    
    public boolean isPlaying()  {
        return mediaPlayer.getStatus().equals(Status.PLAYING); 
    }
    
    public void togglePause()   {
        if (mediaPlayer == null)   {
            return;
        }
        if (mediaPlayer.getStatus().equals(Status.PLAYING))    {
            mediaPlayer.pause();
        } else  {
            mediaPlayer.play();
        }
    }
    
    public void pausePlayback()  {
        if (mediaPlayer == null)   {
            return;
        }
        mediaPlayer.pause();
    }
    
    public void unpausePlayback()   {
        if (mediaPlayer == null)   {
            return;
        }
        mediaPlayer.play();
    }
    
    
    
}
