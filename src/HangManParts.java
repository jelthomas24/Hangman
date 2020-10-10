import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

/**
 *
 * @author Jerry Thomas
 */
public class HangManParts{
    Group parts = new Group();
    
    public void resetParts(){
        parts = new Group();
    }
    
    public void addPart1(){   
        Line top = new Line(125, 125, 300, 125);
        top.setStroke(Color.BROWN);
        top.setStrokeWidth(3);
        parts.getChildren().add(top);
    }
    
    public void addPart2(){
        Line vert = new Line(125, 125, 125, 400);
        vert.setStroke(Color.BROWN);
        vert.setStrokeWidth(3);
        parts.getChildren().add(vert);
    }
    
    public void addPart3(){     
        Line base = new Line(400, 400, 125, 400);
        base.setStroke(Color.BROWN);
        base.setStrokeWidth(3);
        parts.getChildren().add(base);
    }
    
    public void addPart4(){
        Line rope = new Line(300, 125, 300, 175);
        rope.setStroke(Color.BROWN);
        rope.setStrokeWidth(3);
        parts.getChildren().add(rope);
    }
    
    public void addPart5(){
        Ellipse head = new Ellipse(300, 212, 35, 35);
        head.setStroke(Color.BLACK);
        head.setFill(Color.WHITE);
        head.setStrokeWidth(3);
        parts.getChildren().add(head);
    }
    
    public void addPart6(){
        Line tor = new Line(300, 300, 300, 250);
        tor.setStroke(Color.BLACK);
        tor.setStrokeWidth(3);
        parts.getChildren().add(tor);
    }
    
    public void addPart7(){
        Line lftArm = new Line(250, 325, 300, 275);
        lftArm.setStroke(Color.BLACK);
        lftArm.setStrokeWidth(3);
        parts.getChildren().add(lftArm);
    }
    
    public void addPart8(){
        Line rtArm = new Line(350, 325, 300, 275);
        rtArm.setStroke(Color.BLACK);
        rtArm.setStrokeWidth(3);
        parts.getChildren().add(rtArm);
    }
    
    public void addPart9(){
        Line lftLeg = new Line(300, 300, 275, 375);
        lftLeg.setStroke(Color.BLACK);
        lftLeg.setStrokeWidth(3);
        parts.getChildren().add(lftLeg);
    }
    
    public void addPart10(){
        Line rtLeg = new Line(300, 300, 325, 375);
        rtLeg.setStroke(Color.BLACK);
        rtLeg.setStrokeWidth(3);
        parts.getChildren().add(rtLeg);
    }
    
    public void addParts(int guesses){
        parts.setTranslateX(300);
        parts.setTranslateY(200);
        switch(guesses){
            case 10:
                addPart1();
                break;
            case 9:
                addPart2();
                break;
            case 8:
                addPart3();
                break;
            case 7:
                addPart4();
                break;    
            case 6:
                addPart5();
                break;    
            case 5:
                addPart6();
                break;   
            case 4:
                addPart7();
                break;    
            case 3:
                addPart8();
                break;  
            case 2:
                addPart9();
                break;
            case 1:
                addPart10();
                break;
        }
    }
}
