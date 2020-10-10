import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Jerry Thomas
 */
public class Helper{

    public static String getRandomWord(){
        String wordToGuess;
        ArrayList<String> words = new ArrayList<>();
        File myFile = new File("words.txt");
        try{
            Scanner input = new Scanner(myFile);
            while(input.hasNext()){
                String word = input.nextLine().trim();
                words.add(word);
            }
            int randIndex = (int)(Math.random()*(words.size()));
            return words.get(randIndex).toUpperCase();
        }
        catch(Exception e){
            System.out.println("words.txt file is not in its proper location!");
            return "";
        }
    }
    
    public static VBox addHeader(Hangman h, ImageView startView, ImageView loadView, ImageView saveView, ImageView exitView){
        VBox top = new VBox();
        top.setSpacing(10);
        top.getChildren().add(getTopLine(h, startView, loadView, saveView, exitView));
        HBox nextLine = new HBox();
        h.hangman.setFill(Color.RED);
        h.hangman.setFont(Font.font("Arial", 50));
        h.hangman.setVisible(false);
        nextLine.getChildren().add(h.hangman);
        nextLine.setAlignment(Pos.TOP_CENTER);
        top.getChildren().add(nextLine);
        return top;
    }
    
    public static HBox getTopLine(Hangman h, ImageView startView, ImageView loadView, ImageView saveView, ImageView exitView){
        h.start.setGraphic(startView);
        h.buttons.add(h.start);
        h.load.setGraphic(loadView);
        h.buttons.add(h.load);
        h.save.setGraphic(saveView);
        h.buttons.add(h.save);
        h.save.setDisable(true);
        h.exit.setGraphic(exitView);
        h.buttons.add(h.exit);
        HBox topLine = new HBox();
        topLine.setPadding(new Insets(20, 1000, 20, 12));
        topLine.setSpacing(20);
        topLine.getChildren().addAll(h.start, h.load, h.save, h.exit);
        topLine.setStyle("-fx-background-color: dimgrey;");
        return topLine;
    }
    
    public static HBox addFooter(HangManParts p, Hangman h){
        HBox botLine = new HBox();
        botLine.getChildren().add(h.play);
        h.play.setPadding(new Insets(10));
        h.play.setOnMouseClicked(e -> {
                if(!h.play.disabledProperty().getValue()){
                        h.playing = true;
                        h.play.disableProperty().set(true);
                        h.rightSide.getChildren().get(1).setVisible(true);
                        h.welcome.setVisible(false);
                        h.addPath(p);
                }
            });
        botLine.setPadding(new Insets(10));
        botLine.setStyle("-fx-background-color: honeydew;");
        botLine.setAlignment(Pos.BASELINE_CENTER);
        return botLine;
    }
    
    public static VBox addRightSide(Stage primaryStage, HangManParts p, Hangman h){
        HBox text = new HBox();
        h.guess.setFont(Font.font("Arial", 20));
        h.constructAlph(p);
        h.constructWordBox();
        h.rightSide.setSpacing(100);
        text.getChildren().add(h.guess);
        text.setPadding(new Insets(50, 0, 0, 0));
        h.rightSide.getChildren().add(text);
        h.rightSide.getChildren().add(h.wordBox);
        h.rightSide.getChildren().add(h.alph);
        h.alph.setPadding(new Insets(0,50,0,0));
        h.wordBox.setPadding(new Insets(0,50,0,0));
        return h.rightSide;
    }
    
    public static void lostGame(HangManParts p, Hangman h){
        p.addParts(h.numGuesses);
        h.numGuesses--;
        if(h.numGuesses == 0){
            h.save.setDisable(true);
            h.load.setDisable(true);
            h.playing = false;
            h.revealLetters();
            h.alph.setDisable(true);
            Stage endGame = new Stage();
            VBox end = new VBox();
            end.setSpacing(30);
            Button l = new Button("CLOSE");
            Text loser1 = new Text("You lost!");
            Text loser2 = new Text("\n The correct word was '" + h.wordToGuess + "'");
            end.getChildren().addAll(loser1, loser2, l);
            end.setAlignment(Pos.CENTER);
            Scene endScreen = new Scene(end, 600, 300);
            endGame.setTitle("Game Over");
            endGame.setScene(endScreen);
            endGame.show();
            l.setOnMouseClicked(e -> endGame.close());
        }
        h.guess.setText("Remaining Guesses: " + h.numGuesses);
    }
    
    public static void wonGame(Hangman h){
        if(h.correctLettersGuessed == h.wordToGuess.length()){
            h.playing = false;
            h.load.setDisable(true);
            h.alph.setDisable(true);
            Stage endGame = new Stage();
            VBox end = new VBox();
            end.setSpacing(30);
            Button w = new Button("CLOSE");
            Text winner = new Text("You Won!!!");
            end.getChildren().addAll(winner, w);
            end.setAlignment(Pos.CENTER);
            Scene endScreen = new Scene(end, 600, 300);
            endGame.setTitle("Game Over");
            endGame.setScene(endScreen);
            endGame.show();

            w.setOnMouseClicked(e -> endGame.close());
        }
        h.activateSave();
    }
}
