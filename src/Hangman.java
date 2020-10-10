import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Jerry Thomas
 */
public class Hangman extends Application {
    BorderPane border = new BorderPane();
    FlowPane alph = new FlowPane();
    VBox rightSide = new VBox();
    ArrayList<Button> buttons = new ArrayList<>();
    boolean startPressed = false;
    int numGuesses = 10;
    Text hangman = new Text("Hangman");
    boolean playing = false;
    String wordToGuess = "";
    Button play = new Button("Start Playing");
    ArrayList<TextField> keyLetters = new ArrayList<>();
    FlowPane wordBox = new FlowPane();
    int correctLettersGuessed = 0;
    Button save = new Button("Save");
    Button load = new Button("Load");
    Button start = new Button("Start");
    Button exit = new Button("Exit");
    VBox welcome = new VBox();
    Text welcome1 = new Text("Welcome to Hangman!");
    Text welcome2 = new Text("Click below to start playing");
    boolean madeMove = false;
    Text guess = new Text("Remaining Guesses: " + numGuesses);
    ImageView startView = new ImageView(new Image(getClass().getResourceAsStream("New.png")));
    ImageView loadView = new ImageView(new Image(getClass().getResourceAsStream("Load.png")));
    ImageView saveView = new ImageView(new Image(getClass().getResourceAsStream("Save.png")));
    ImageView exitView = new ImageView(new Image(getClass().getResourceAsStream("Exit.png")));
    
    @Override
    public void start(Stage primaryStage) {
        HangManParts p = new HangManParts();
        wordToGuess = Helper.getRandomWord();
        border.setTop(Helper.addHeader(this, startView, loadView, saveView, exitView));
        border.setLeft(p.parts);
        Scene scene = new Scene(border, 1400, 850);
        scene.setFill(Color.IVORY);
        primaryStage.setTitle("Hangman");
        primaryStage.setScene(scene);
        primaryStage.show();
        start.setOnMouseClicked(e -> Buttons.startButton(primaryStage, p, this));
        exit.setOnMouseClicked(e -> Buttons.exitButton(primaryStage, this));
        save.setOnMouseClicked(e -> Buttons.saveButton(this));
        load.setOnMouseClicked(e -> Buttons.loadButton(primaryStage, p, this));
        scene.setOnKeyPressed(e -> {
            if(e.getCode().isLetterKey() && playing){
                int ascii = (int)e.getText().toUpperCase().charAt(0);
                if(!alph.getChildren().get(ascii - 65).disableProperty().getValue()){
                    alph.getChildren().get(ascii - 65).disableProperty().set(true);
                    madeMove = true;
                    checkIfValid(ascii, p);
                }
            }
        });
    }
    
    public void resetComponents(HangManParts p){
        border.setRight(null);
        border.setBottom(null);
        border.setLeft(null);
        wordBox = new FlowPane();
        alph = new FlowPane();
        keyLetters = new ArrayList<>();
        rightSide = new VBox();
        p.resetParts();
        border.setLeft(p.parts);
    }
    
    public void activateSave(){
        if(!playing){
            save.setDisable(true);
            return;
        }
        save.setDisable(false);  
    }
    
    public void constructAlph(HangManParts p){
        int ascii = 65;
        for(int i = 0; i < 26; i++){
            Button b = new Button((char)ascii + "");
            b.setMaxSize(50, 50);
            b.setMinSize(50, 50);
            b.setOnAction(e ->{
                if(!b.disabledProperty().getValue() && playing){
                    b.disableProperty().set(true);
                    int ascii2 = (int)b.getText().toUpperCase().charAt(0);
                    checkIfValid(ascii2, p);
                }
            });
            b.setStyle("-fx-background-color: lime;");
            alph.getChildren().add(b);
            ascii++;
        }
        alph.setVgap(4);
        alph.setHgap(4);
    }
    
    public void constructWordBox(){
        for(int i = 0; i < wordToGuess.length(); i++){
            TextField b = new TextField();
            b.setEditable(false);
            b.setMaxSize(35, 35);
            b.setMinSize(35, 35);
            b.setStyle("-fx-background-color: black;");
            keyLetters.add(b);
            wordBox.getChildren().add(b);
        }
        wordBox.setVisible(false);
        wordBox.setHgap(4);
        wordBox.setVgap(4);
    }
    
    public void checkIfValid(int ascii, HangManParts p){
        boolean correctGuess = false;
        madeMove = true;
        activateSave();
        for(int i = 0; i < wordToGuess.length(); i++){
            TextField b = keyLetters.get(i);
            if((int)wordToGuess.charAt(i) == ascii){
                b.setText(wordToGuess.toUpperCase().charAt(i) + "");
                b.setFont(Font.font("Castellar", 16));
                b.setAlignment(Pos.CENTER);
                b.setStyle("-fx-color: white;");
                wordBox.getChildren().set(i, b);
                correctGuess = true;
                correctLettersGuessed++;
            }
        }
        if(!correctGuess){
            Helper.lostGame(p, this);
        }
        else{
            Helper.wonGame(this);
        }
    }
    
    public void revealLetters(){
        for(int i = 0; i < wordBox.getChildren().size(); i++){
            TextField b = keyLetters.get(i);
            if(!wordBox.getChildren().get(i).getStyle().equalsIgnoreCase("-fx-color: white;")){
                b.setText(wordToGuess.toUpperCase().charAt(i) + "");
                b.setFont(Font.font("Castellar", 16));
                b.setAlignment(Pos.CENTER);
                b.setStyle("-fx-background-color: red;");
                wordBox.getChildren().set(i, b);
            }
        }
    }
    
    public void resetGameState(String[] correctLetters, String[] lettersGuessed, Stage primaryStage, HangManParts p){
        if(!startPressed){
                startPressed = true;
                hangman.setVisible(true);
                border.setRight(Helper.addRightSide(primaryStage, p, this));
                border.setBottom(Helper.addFooter(p, this));
                setAlphButtons(lettersGuessed);
                setWordBox(correctLetters);
                resetHangMan(p);
            }
        else{
            resetComponents(p);
            border.setRight(Helper.addRightSide(primaryStage, p, this));
            border.setBottom(Helper.addFooter(p, this));
            play.setDisable(false);
            setAlphButtons(lettersGuessed);
            setWordBox(correctLetters);
            resetHangMan(p);
        }
    }
    
    public void resetHangMan(HangManParts p){
        int temp = 10;
        while(temp != numGuesses){
            p.addParts(temp);
            temp--;
        }
    }
    
    public void setWordBox(String[] correctLetters){
        for(int i = 0; i < correctLetters.length; i++){
            for(int j = 0; j < wordToGuess.length(); j++){
                TextField b = keyLetters.get(j);
                if((wordToGuess.charAt(j) + " ").trim().equals(correctLetters[i])){
                    b.setText(wordToGuess.toUpperCase().charAt(j) + "");
                    b.setFont(Font.font("Castellar", 16));
                    b.setAlignment(Pos.CENTER);
                    b.setStyle("-fx-color: white;");
                    wordBox.getChildren().set(j, b);
                }
            }
        }
    }
   
    public void setAlphButtons(String[] lettersGuessed){
        for(int i = 0; i < lettersGuessed.length; i++){
            for(int j = 0; j < alph.getChildren().size(); j++){
                Button b = (Button)alph.getChildren().get(j);
                if(b.getText().trim().equals(lettersGuessed[i])){
                    b.setDisable(true);
                }
            }
        }
    }
    
    public void newGame(Stage primaryStage){
        if(!playing || !madeMove){
         Hangman h = new Hangman();
         h.start(primaryStage);
        }
        else{
            endGame("Would you like to save the game before starting a new one?", "New Game", primaryStage);
        }
    }
    
    public void endGame(String text, String title, Stage primaryStage){
        Stage game = new Stage();
        VBox n = new VBox();
        Text save = new Text(text);
        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);
        Button yes = new Button("Yes");
        Button no = new Button("No");
        Button cancel = new Button("Cancel");
        buttons.getChildren().addAll(yes, no, cancel);
        n.getChildren().addAll(save, buttons);
        n.setAlignment(Pos.CENTER);
        n.setSpacing(30);
        Scene newScreen = new Scene(n, 600, 300);
        game.setTitle(title);
        game.setScene(newScreen);
        game.show();
        yes.setOnMouseClicked(e -> Buttons.saveButton(this));
        no.setOnMouseClicked(e -> {
            if(title.equalsIgnoreCase("Exit Game")){
                game.close();
                primaryStage.close();
            }
            else{
                Hangman h = new Hangman();
                h.start(primaryStage);
                game.close();
            }
        });
        cancel.setOnMouseClicked(e -> game.close());
    }
   
    public void addPath(HangManParts p){
        p.parts.prefHeight(1000);
        p.parts.prefWidth(1000);
        border.setLeft(p.parts);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}