package noteplayer.ui;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import noteplayer.player.Audio;
import noteplayer.filebrowser.Browser;

public class Ui extends Application{
    
    Audio audio;
    Browser browser;
    BorderPane pane;
    
    @Override
    public void start(Stage stage)  {
        stage.setTitle("Note-player");
        
        audio = new Audio();
        browser = new Browser();
        pane = new BorderPane();
        
        pane.setLeft(fileVBox());
        pane.setCenter(noteTextArea());
        pane.setBottom(playerButtons());
        
        Scene scene = new Scene(pane);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args)  {
        launch(Ui.class);
    }
    
    // TODO: if a note exists for a song, this method
    // should retrive it from a yet-to-be-written class
    // and display it
    private TextArea noteTextArea() {
        TextArea textArea = new TextArea("");
        return textArea;
    }
    
    private HBox playerButtons()    {
        HBox buttons = new HBox();
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
        VBox fileBox = new VBox();
        Button up = new Button("Upper directory");
        up.setOnAction((event) ->   {
            browser.moveUpOneDirectory();
            refreshFiles();
        });
        fileBox.getChildren().add(up);
        for(int i = 0; i < files.length; i++)   {
             File file = files[i];
             Button button = new Button(file.toString());
             // TODO: change directory if file is directory,
             // otherwise attempt to play file
             button.setOnAction((event) ->  {
                 browser.changeDirectoryOrPlay(file, audio);
                 refreshFiles();
             });
             fileBox.getChildren().add(button);
        }
        return fileBox;
    }
    
}
