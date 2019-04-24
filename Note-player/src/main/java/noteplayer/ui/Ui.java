package noteplayer.ui;

import java.io.File;
import java.util.HashMap;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import noteplayer.dao.NoteDAO;
import noteplayer.dao.SettingsDAO;
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
    TextField autoScrollSpeedField;
    
    Animation autoScroll;
    boolean autoScrolling;
    
    Button styleButton;
    Button smaller;
    Button bigger;
    Button autoScrollToggle;
    
    Integer fontSize;
    Integer theme;
    Integer scrollSpeed;
    
    String currentlyPlayingFileName;
    
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
        noteDAO = new NoteDAO("notes");
        settingsDAO = new SettingsDAO(fontSize, theme, "notes");
        pane = new BorderPane();
        
        // get possible saved settings from database
        fontSize = settingsDAO.getFontSize(fontSize);
        theme = settingsDAO.getTheme(theme);
        
        currentlyPlaying = new Label("---");
        currentlyPlaying.setPadding(new Insets(5, 5, 0, 0));
        notes = new HashMap<>();
        
        //TODO: a better layout, as in specifications
        //VBox leftBox = fileVBox();
        VBox leftBox = fileListView();
        pane.setLeft(leftBox);
        noteArea = noteTextArea();
        noteArea.setFont(Font.font("", fontSize));
        pane.setCenter(noteArea);
        
        // creating default autoscroll animation
        autoScrollSetSpeed(60);
        
        // creating the top bar
        HBox player = new HBox();
        player.getChildren().add(noteSaveButton());
        player.getChildren().add(playerButtons());
        cp = new Label("Currently playing: ");
        cp.setPadding(new Insets(5, 0, 0, 0));
        HBox cpBox = new HBox();
        cpBox.getChildren().add(cp);
        cpBox.getChildren().add(currentlyPlaying);
        player.getChildren().add(cpBox);
        player.getChildren().add(fontSizeButtons());
        styleButton = styleButton();
        player.getChildren().add(styleButton);
        player.getChildren().add(autoScrollSpeedControl());
        player.getChildren().add(autoScrollControls());
        
        player.setSpacing(8);
        
        player.setPadding(new Insets(0, 0, 10, 0));
        
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
        String playing = audio.getCurrentlyPlayingFormattedString();
        if (playing.length() > 15)  {
            playing = playing.substring(0, 14) + "...";
        }
        currentlyPlaying.setText(playing);
    }
    
    private void setStyleLight()    {
          scene.getStylesheets().clear();
    }
    
    private void setStyleDark() {
          scene.getStylesheets().add("stylesheet.css");
    }
    
    private void autoScrollSetSpeed(int speed) {
        scrollSpeed = speed;
    autoScroll = new Timeline(
        new KeyFrame(Duration.seconds(speed),
            new KeyValue(noteArea.scrollTopProperty(), 
                    noteArea.getText().split("\n").length * 1.1 * fontSize)));
    autoScroll.setOnFinished(e -> autoScrollStop());
    }
    
    private void autoScrollStop()   {
        autoScroll.stop();
        autoScrolling = false;
        autoScrollToggle.setText("Start autoscroll");
    }
    
    private void autoScrollToggle()  {
        if (autoScrolling)  {
            autoScroll.stop();
            autoScrolling = false;
            autoScrollToggle.setText("Start autoscroll");
        } else  {
            noteArea.setScrollTop(0);
            autoScrollSetSpeed(scrollSpeed);
            autoScroll.play();
            autoScrollToggle.setText("Stop autoscroll");
            autoScrolling = true;
        }
    }
    
    private HBox autoScrollSpeedControl()   {
        HBox box = new HBox();
        Label scrollSpeedLabel = new Label("Autoscroll duration: ");
        scrollSpeedLabel.setPadding(new Insets(5, 0, 0, 0));
        autoScrollSpeedField = new TextField(scrollSpeed.toString());
        autoScrollSpeedField.textProperty().addListener((observable, oldValue, newValue) -> {
            String newSpeedString = newValue;
            try {
                Integer newSpeed = Integer.valueOf(newSpeedString);
                //if ()
                autoScrollSetSpeed(newSpeed);
            } catch (Exception e)   {
            }
        });
        autoScrollSpeedField.setPrefWidth(52);
        box.getChildren().add(scrollSpeedLabel);
        box.getChildren().add(autoScrollSpeedField);
        return box;
    }
    
    private HBox autoScrollControls()   {
        HBox box = new HBox();
        autoScrollToggle = new Button("Start autoscroll");
        autoScrollToggle.setOnAction((event) ->   {
            autoScrollToggle();
        });
        box.getChildren().add(autoScrollToggle);
        return box;
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
    
    private void saveNote(){
        noteDAO.saveNoteAndScrollSpeed(audio.getCurrentlyPlayingString(), 
                noteText, scrollSpeed);
    }
    
    private Button noteSaveButton() {
        Button button = new Button("save note");
        
        button.setOnAction((event) ->   {
            noteText = noteArea.getText();
            saveNote();
        });
        
        return button;
    }
    
    private HBox fontSizeButtons() {
        HBox hbox2 = new HBox();
        fs = new Label("Font size: " + fontSize);
        fs.setPadding(new Insets(5, 5, 0, 0));
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
        return hbox2;
    }
    
    private void fontSizeDown() {
        fontSize--;
        noteArea.setFont(Font.font("", fontSize));
        fs.setText("Font size: " + fontSize);
    }
    
    private void fontSizeUp()   {
        fontSize++;
        noteArea.setFont(Font.font("", fontSize));
        
        fs.setText("Font size: " + fontSize);
    }
    
    private void updateForSong(File file)   {
        String songName = file.toString();
        String note = noteDAO.getSongNote(songName);
        scrollSpeed = noteDAO.getSongScrollSpeed(songName, scrollSpeed);
        noteArea.setText(note);
        if (noteArea.getText() != null) {
            autoScrollSetSpeed(scrollSpeed);
            autoScrollSpeedField.setText(scrollSpeed.toString());
        }
        currentlyPlayingFileName = songName;
    }
    private TextArea noteTextArea() {
        TextArea textArea = new TextArea("");
        return textArea;
    }
    
    private HBox playerButtons()   {
        HBox hbox2 = new HBox();
        Button prevButton = new Button("<<");
        prevButton.setOnAction((event) ->   {
            browser.previousSong(audio);
        });
        hbox2.getChildren().add(prevButton);
        Button playButton = new Button("||");
        playButton.setOnAction((event) ->   {
            audio.togglePause();
        });
        hbox2.getChildren().add(playButton);
        Button nextButton = new Button(">>");
        nextButton.setOnAction((event)  ->  {
            browser.nextSong(audio);
        });
        hbox2.getChildren().add(nextButton);
        Button repeatButton = new Button("repeat on");
        repeatButton.setOnAction((event) -> {
            if (audio.toggleRepeat())   {
                repeatButton.setText("repeat off");
            } else  {
                repeatButton.setText("repeat on");
            }
        });
        hbox2.getChildren().add(repeatButton);
        return hbox2;
    }
    
    private void refreshFiles() {
        pane.setLeft(fileListView());
    }
    
    // creates a button for each file and directory
    // in the current directory, returns them in a 
    // VBox. 
    // TODO: return a list for more flexibility?
    // NOTE: probably undeed now
    private VBox fileVBox()    {
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
             Button button = new Button(names[i]);
             // TODO: change directory if file is directory,
             // otherwise attempt to play file
             button.setOnAction((event) ->  {
                 browser.changeDirectoryOrPlay(file, audio);
                 if (file.isFile()) {
                    updateForSong(file);
                    updateCurrentlyPlaying();
                 }
                 refreshFiles();
             });
             fileBox.getChildren().add(button);
        }
        fileBox.setPadding(new Insets(10, 0, 0, 0));
        return fileBox;
    }
    
    private VBox fileListView()    {
        File[] files = browser.listFiles();
        String[] names = browser.listFilesFormatted();
        VBox box = new VBox();
        ListView list = new ListView();
        Button up = new Button("Upper directory");
        up.setOnAction((event) ->   {
            browser.moveUpOneDirectory();
            refreshFiles();
        });
        box.getChildren().add(up);
        //list.getItems().add(up);
        for(int i = 0; i < files.length; i++)   {
             File file = files[i];
             Button button = new Button(names[i]);
             // TODO: change directory if file is directory,
             // otherwise attempt to play file
             button.setOnAction((event) ->  {
                 browser.changeDirectoryOrPlay(file, audio);
                 if (file.isFile()) {
                    updateForSong(file);
                    updateCurrentlyPlaying();
                 }
                 refreshFiles();
             });
             list.getItems().add(button);
        }
        box.setPadding(new Insets(0, 10, 0, 0));
        box.setSpacing(10);
        //list.setPadding(new Insets(0, 0, 0, 0));
        box.getChildren().add(list);
        return box;
    }
    
}
