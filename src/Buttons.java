import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Jerry Thomas
 */
public class Buttons{
    
    public static void startButton(Stage primaryStage, HangManParts p, Hangman h){
        if(!h.startPressed){
            h.startPressed = true;
            h.hangman.setVisible(true);
            h.border.setRight(Helper.addRightSide(primaryStage, p, h));
            h.border.setBottom(Helper.addFooter(p, h));
            h.welcome1.setFont(Font.font("Castellar", 36));
            h.welcome2.setFont(Font.font("Castellar", 36));
            h.welcome.getChildren().addAll(h.welcome1, h.welcome2);
            h.welcome.setAlignment(Pos.CENTER);
            h.border.setCenter(h.welcome);
        }
        else{
            h.newGame(primaryStage);
        }
    }
    
    public static void loadButton(Stage primaryStage, HangManParts p, Hangman h){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HNG Files", "*.hng"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if(selectedFile == null){
            
        }
        else{
            readFromFile(selectedFile, primaryStage, p, h);
            h.madeMove = false;
        }
    }
    
    public static void readFromFile(File file, Stage primaryStage, HangManParts p, Hangman h){
        try{
            Scanner sc = new Scanner(file);
            h.wordToGuess = sc.nextLine();
            h.numGuesses = sc.nextInt();
            Text guess = new Text("Remaining Guesses: " + h.numGuesses);
            h.guess = guess;
            String clear = sc.nextLine();
            String[] correctLetters = sc.nextLine().split(" ");
            int numCorrect = 0;
            for(int i = 0; i < correctLetters.length; i++){
                if(correctLetters[i].trim().length() != 0){
                    numCorrect++;
                }
            }
            h.correctLettersGuessed = numCorrect;
            String[] lettersGuessed = sc.nextLine().split(" ");
            sc.close();
            h.resetGameState(correctLetters, lettersGuessed, primaryStage, p);
        }
        catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
    public static void exitButton(Stage primaryStage, Hangman h){
        if(!h.playing || !h.madeMove){
            primaryStage.close();
        }
        else{
            h.endGame("Would you like to save the game before exiting?", "Exit Game", primaryStage);
        }
    }
    
    public static void saveButton(Hangman h){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("HNG Files (*.hng)", "*.hng");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showSaveDialog(new Stage());
        if (file == null) {
            //Do Nothing
        }
        else{
            writeToFile(file, h);
        }
    }

    public static void writeToFile(File file, Hangman h){
        try{
            FileWriter fw = new FileWriter(file);
            fw.write(h.wordToGuess + "\n");
            fw.write(h.numGuesses + "\n");
            for(int i = 0; i < h.wordBox.getChildren().size(); i++){
                TextField b = h.keyLetters.get(i);
                if(h.wordBox.getChildren().get(i).getStyle().equalsIgnoreCase("-fx-color: white;")){
                    fw.write(b.getText() + " ");
                }
            }
            fw.write("\n");
            for(int i = 0; i < h.alph.getChildren().size(); i++){
                Button b = (Button)h.alph.getChildren().get(i);
                if(b.disableProperty().get()){
                    fw.write(b.getText() + " ");
                }
            }
            fw.write("\n");
            h.madeMove = false;
            h.save.setDisable(true);
            fw.close();
        }
        catch(IOException e){
            System.out.println("IOException!");
        }
    }
}
