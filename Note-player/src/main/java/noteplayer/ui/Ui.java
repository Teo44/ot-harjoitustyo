package noteplayer.ui;

import java.io.File;
import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import noteplayer.dao.NoteDAO;
import noteplayer.player.Audio;
import noteplayer.filebrowser.Browser;

public class Ui extends Application{
    
    Audio audio;
    Browser browser;
    NoteDAO noteDAO;
    BorderPane pane;
    TextArea noteArea;
    String noteText;
    TextField currentlyPlaying;
    
    HashMap<String, String> notes;
    
    @Override
    public void start(Stage stage)  {
        stage.setTitle("Note-player");
        
        audio = new Audio();
        browser = new Browser();
        noteDAO = new NoteDAO();
        pane = new BorderPane();
        
        currentlyPlaying = new TextField("");
        notes = new HashMap<>();
        
        //TODO: a better layout, as in specifications
        VBox leftBox = fileVBox();
        pane.setLeft(leftBox);
        noteArea = noteTextArea();
        pane.setCenter(noteArea);
        HBox bottomBox = playerButtons();
        bottomBox.getChildren().add(currentlyPlaying);
        pane.setBottom(bottomBox);
        
        Scene scene = new Scene(pane);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args)  {
        launch(Ui.class);
    }
    
    private void updateCurrentlyPlaying()    {
        if (audio.getCurrentlyPlayingString() == null)  {
            return;
        }
        currentlyPlaying.setText(audio.getCurrentlyPlayingFormattedString());
    }
    
    // TODO: save note to database, current saving is for testing
    private void saveNote() {
        noteDAO.saveNote(audio.getCurrentlyPlayingString(), noteText);
    }
    
    private Button noteSaveButton() {
        Button button = new Button("save note");
        
        button.setOnAction((event) ->   {
            noteText = noteArea.getText();
            saveNote();
        });
        
        return button;
    }
    
    
    private void getNoteForSong(File file)   {
        String songName = file.toString();
        if (noteDAO.noteForSongExists(songName))   {
            noteArea.setText(noteDAO.getSongNote(songName));
        } else  {
            noteArea.setText("");
        }
    }
    
    // TODO: if a note exists for a song, this method
    // should retrive it from a yet-to-be-written class
    // and display it
    private TextArea noteTextArea() {
        TextArea textArea = new TextArea("");
        return textArea;
    }
    
    // creates the buttons to control playback
    // TODO: volume, functionality for next and previous track,
    // repeat
    private HBox playerButtons()    {
        HBox buttons = new HBox();
        
        buttons.getChildren().add(noteSaveButton());
        
        buttons.getChildren().add(new Button("<<"));
        Button playButton = new Button("||");
        playButton.setOnAction((event) ->   {
            audio.togglePause();
        });
        buttons.getChildren().add(playButton);
        buttons.getChildren().add(new Button(">>"));
        return buttons;
    }
    
    private void refreshFiles() {
        pane.setLeft(fileVBox());
    }
    
    // creates a button for each file and directory
    // in the current directory, returns them in a 
    // VBox. 
    // TODO: return a list for more flexibility?
    private VBox fileVBox()    {
        //String[] files = browser.listFilesString();
        File[] files = browser.listFiles();
        String[] names = browser.listFilesFormatted();
        VBox fileBox = new VBox();
        Button up = new Button("Upper directory");
        up.setOnAction((event) ->   {
            browser.moveUpOneDirectory();
            refreshFiles();
        });
        fileBox.getChildren().add(up);
        for(int i = 0; i < files.length; i++)   {
             File file = files[i];
             //Button button = new Button(file.toString());
             Button button = new Button(names[i]);
             // TODO: change directory if file is directory,
             // otherwise attempt to play file
             button.setOnAction((event) ->  {
                 browser.changeDirectoryOrPlay(file, audio);
                 if (file.isFile()) {
                    getNoteForSong(file);
                    updateCurrentlyPlaying();
                 }
                 refreshFiles();
             });
             fileBox.getChildren().add(button);
        }
        return fileBox;
    }
    
}
