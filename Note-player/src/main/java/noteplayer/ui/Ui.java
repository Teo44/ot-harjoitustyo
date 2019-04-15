package noteplayer.ui;

import java.io.File;
import java.util.HashMap;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import noteplayer.dao.NoteDAO;
import noteplayer.dao.SettingsDAO;
import noteplayer.player.Audio;
import noteplayer.filebrowser.Browser;
import noteplayer.player.FxPlayer;

public class Ui extends Application{
    
    //Audio audio;
    FxPlayer audio;
    Browser browser;
    NoteDAO noteDAO;
    SettingsDAO settingsDAO;
    BorderPane pane;
    TextArea noteArea;
    String noteText;
    Label currentlyPlaying;
    Label cp;
    Label fs;
    Scene scene;
    
    Button styleButton;
    Button smaller;
    Button bigger;
    
    Integer fontSize;
    Integer theme;
    
    HashMap<String, String> notes;
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Note-player");
        
        // default values, used when no settings are found
        fontSize = 14;
        theme = 1;
        
        //audio = new Audio();
        audio = new FxPlayer();
        browser = new Browser();
        noteDAO = new NoteDAO();
        settingsDAO = new SettingsDAO(fontSize, theme);
        pane = new BorderPane();
        
        // get possible saved settings from database
        fontSize = settingsDAO.getFontSize(fontSize);
        theme = settingsDAO.getTheme(theme);
        
        currentlyPlaying = new Label("---");
        currentlyPlaying.setPadding(new Insets(5, 5, 0, 0));
        notes = new HashMap<>();
        
        //TODO: a better layout, as in specifications
        VBox leftBox = fileVBox();
        pane.setLeft(leftBox);
        noteArea = noteTextArea();
        noteArea.setFont(Font.font("", fontSize));
        pane.setCenter(noteArea);
        
        HBox player = new HBox();
        player = saveButton(player);
        player = playerButtons(player);
        cp = new Label("Currently playing: ");
        cp.setPadding(new Insets(5, 0, 0, 0));
        HBox cpBox = new HBox();
        cpBox.getChildren().add(cp);
        cpBox.getChildren().add(currentlyPlaying);
        player.getChildren().add(cpBox);
        player = fontSizeButtons(player);
        styleButton = styleButton();
        player.getChildren().add(styleButton);
        
        player.setSpacing(30);
        
        pane.setTop(player);
        
        scene = new Scene(pane);
        
        if (theme == 2) {
            setStyleDark();
        }
        
        stage.setScene(scene);
        stage.show();
        
        // saving settings on exit
        stage.setOnCloseRequest((WindowEvent event1) -> {
            settingsDAO.setFontSize(fontSize);
            settingsDAO.setTheme(theme);
        });
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
    
    private void setStyleLight()    {
          scene.getStylesheets().clear();
    }
    
    private void setStyleDark() {
          scene.getStylesheets().add("stylesheet.css");
    }
    
    private Button styleButton()    {
        styleButton = new Button();
        if (theme == 1) {
            styleButton.setText("Dark theme");
        } else if (theme == 2)  {
            styleButton.setText("Light theme");
        }
        styleButton.setOnAction((event) ->   {
            if (theme == 1) {
                theme = 2;
                setStyleDark();
                styleButton.setText("Light theme");
            } else if (theme == 2)  {
                theme = 1;
                setStyleLight();
                styleButton.setText("Dark theme");
            }
        });
        return styleButton;
    }
    
    // TODO: save note to database, current saving is for testing
    private void saveNote(){
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
    
    private HBox fontSizeButtons(HBox hbox) {
        HBox hbox2 = new HBox();
        fs = new Label("Font size: ");
        fs.setPadding(new Insets(5, 0, 0, 0));
        hbox2.getChildren().add(fs);
        smaller = new Button("-");
        bigger = new Button("+");
        bigger.setOnAction((event) ->  {
            fontSizeUp();
        });
        smaller.setOnAction((event) ->  {
           fontSizeDown(); 
        });
        
        hbox2.getChildren().addAll(smaller, bigger);
        hbox.getChildren().add(hbox2);
        return hbox;
    }
    
    private void fontSizeDown() {
        fontSize--;
        noteArea.setFont(Font.font("", fontSize));
        //settingsDAO.setFontSize(fontSize);
    }
    
    private void fontSizeUp()   {
        fontSize++;
        noteArea.setFont(Font.font("", fontSize));
        //settingsDAO.setFontSize(fontSize);
    }
    
    private void getNoteForSong(File file)   {
        String songName = file.toString();
        String note = noteDAO.getSongNote(songName);
        noteArea.setText(note);
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
        Button prevButton = new Button("<<");
        prevButton.setOnAction((event) ->   {
            audio.prev();
        });
        buttons.getChildren().add(prevButton);
        Button playButton = new Button("||");
        playButton.setOnAction((event) ->   {
            audio.togglePause();
        });
        buttons.getChildren().add(playButton);
        Button nextButton = new Button(">>");
        buttons.getChildren().add(nextButton);
        return buttons;
    }
    
    // like above, but adds the buttons to an existing HBox
    private HBox playerButtons(HBox hbox)   {
        HBox hbox2 = new HBox();
        Button prevButton = new Button("<<");
        prevButton.setOnAction((event) ->   {
            audio.prev();
        });
        hbox2.getChildren().add(prevButton);
        Button playButton = new Button("||");
        playButton.setOnAction((event) ->   {
            audio.togglePause();
        });
        hbox2.getChildren().add(playButton);
        hbox2.getChildren().add(new Button(">>"));
        hbox.getChildren().add(hbox2);
        return hbox;
    }
    
    private HBox saveButton(HBox hbox)    {
        hbox.getChildren().add(noteSaveButton());
        return hbox;
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
        fileBox.setPadding(new Insets(10, 0, 0, 0));
        return fileBox;
    }
    
}
