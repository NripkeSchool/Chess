
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import java.util.*;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Text;
import javafx.scene.shape.*;

public class ChessHomeGUI extends Application
{
    private Color black = Color.rgb(214,138,89);
    private Color white = Color.rgb(253,213,177);
    static Color backgroundColor = Color.rgb(239,240,242);
    boolean currentColor = true; //True for white, false for black
    
    private Stage pStage;
    private Scene scene;
    private int screenSize = 600;
    static int currentAILevel = 2;
    
    static boolean playingChess960 = false;
    
    public void start(Stage primaryStage)
    {
       Text title = new Text(screenSize - screenSize/7.5, screenSize/10, "Chess");

       title.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, screenSize/10));
       title.setFill(white);
       title.setWrappingWidth(screenSize/2.5);
       title.setStroke(black);
       
       Rectangle chessAIBox = new Rectangle(screenSize/8, screenSize/5, screenSize/2, screenSize/4);
       chessAIBox.setFill(white);
       chessAIBox.setStrokeWidth(10);
       chessAIBox.setStroke(black);
     
       Rectangle chessFriendBox = new Rectangle(3*screenSize/4, screenSize/5, screenSize/2, screenSize/4);
       chessFriendBox.setFill(white);
       chessFriendBox.setStrokeWidth(10);
       chessFriendBox.setStroke(black);
       
       Rectangle chess960Box = new Rectangle(screenSize+screenSize/4+screenSize/8, screenSize/5, screenSize/2, screenSize/4);
       chess960Box.setFill(white);
       chess960Box.setStrokeWidth(10);
       chess960Box.setStroke(black);
       
       Text chess960Text = new Text(screenSize+screenSize/4+screenSize/8+screenSize/120, screenSize/5+screenSize/8, "Play Chess960");
       chess960Text.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, screenSize/15));
       chess960Text.setFill(black);
       
       Text aiText = new Text(screenSize/8+screenSize/120, screenSize/5+screenSize/8, "Play Against AI");
       aiText.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, screenSize/15));
       aiText.setFill(black);
       
       Text friendText = new Text((3*screenSize/4)+screenSize/160, screenSize/5+screenSize/8, "Play Against Friends");
       friendText.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, screenSize/20));
       friendText.setFill(black);
       
       Rectangle ai1 = new Rectangle(screenSize/8, screenSize/5+screenSize/3, screenSize/5, screenSize/8);
       ai1.setFill(white);
       ai1.setStrokeWidth(10);
       ai1.setStroke(black);
       
       Text ai1Text = new Text(screenSize/8+screenSize/100, screenSize/5+screenSize/3+screenSize/16, "Level 0");
       ai1Text.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, screenSize/20));
       ai1Text.setFill(black);
       
       Rectangle ai2 = new Rectangle(screenSize/8+screenSize/5+screenSize/10, screenSize/5+screenSize/3, screenSize/5, screenSize/8);
       ai2.setFill(white);
       ai2.setStrokeWidth(10);
       ai2.setStroke(black);
       
       Text ai2Text = new Text(screenSize/8+screenSize/5+screenSize/10+screenSize/100, screenSize/5+screenSize/3+screenSize/16, "Level 1");
       ai2Text.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, screenSize/20));
       ai2Text.setFill(black);
       
       Rectangle ai3 = new Rectangle(screenSize/8, screenSize/5+screenSize/3+screenSize/6, screenSize/5, screenSize/8);
       ai3.setFill(white);
       ai3.setStrokeWidth(10);
       ai3.setStroke(black);
       
       Text ai3Text = new Text(screenSize/8+screenSize/100, screenSize/5+screenSize/3+screenSize/6+screenSize/16, "Level 2");
       ai3Text.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, screenSize/20));
       ai3Text.setFill(black);
       
       Rectangle ai4 = new Rectangle(screenSize/8+screenSize/5+screenSize/10, screenSize/5+screenSize/3+screenSize/6, screenSize/5, screenSize/8);
       ai4.setFill(white);
       ai4.setStrokeWidth(10);
       ai4.setStroke(black);
       
       Text ai4Text = new Text(screenSize/8+screenSize/5+screenSize/10+screenSize/100, screenSize/5+screenSize/3+screenSize/6+screenSize/16, "Level 3");
       ai4Text.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, screenSize/20));
       ai4Text.setFill(black);
       
       Text changeBackground = new Text(screenSize + screenSize/2, screenSize/10, "Change Background");
       changeBackground.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, screenSize/20));
       changeBackground.setFill(white);
       changeBackground.setStroke(black);
       
       ai1.setOnMouseClicked(this::ai1Button);
       ai2.setOnMouseClicked(this::ai2Button);
       ai3.setOnMouseClicked(this::ai3Button);
       ai4.setOnMouseClicked(this::ai4Button);
       chess960Box.setOnMouseClicked(this::chess960Button);
       changeBackground.setOnMouseClicked(this::changeBack);
       chessFriendBox.setOnMouseClicked(this::friendButton);
      
       Group categories = new Group(title, chessAIBox, chessFriendBox, chess960Box, chess960Text, aiText, friendText);
       Group AIButtons = new Group(ai1, ai2, ai3, ai4, ai1Text, ai2Text, ai3Text, ai4Text);
       
       Group root = new Group(categories, AIButtons, changeBackground);
       
       scene = new Scene(root, 2*screenSize, screenSize, backgroundColor);
       
       primaryStage.setTitle("Chess Home");
       primaryStage.setScene(scene);
       primaryStage.show();
       
       pStage = primaryStage;
    }
    
    public void changeBack(MouseEvent event)
    {
        //White Background RGB: 239,240,242
        //Black Background RGB: 47, 49, 51
        
        System.out.println("Works");
        
        if (currentColor)
        {
          backgroundColor = Color.rgb(47, 49, 51);   
          currentColor = false;
        }else {
          backgroundColor = Color.rgb(239,240,242);   
          currentColor = true;  
        }
        
        scene.setFill(backgroundColor);
    }
    
    public void ai1Button(MouseEvent event)
    {
        currentAILevel = 0;
        ChessBoardGUI chessBoard = new ChessBoardGUI();
        chessBoard.start(pStage);
    }
    
    public void ai2Button(MouseEvent event)
    {
        currentAILevel = 1;
        ChessBoardGUI chessBoard = new ChessBoardGUI();
        chessBoard.start(pStage);
    }
    
    public void ai3Button(MouseEvent event)
    {
        currentAILevel = 2;
        ChessBoardGUI chessBoard = new ChessBoardGUI();
        chessBoard.start(pStage);
    }
    
    public void ai4Button(MouseEvent event)
    {
        currentAILevel = 3;
        ChessBoardGUI chessBoard = new ChessBoardGUI();
        chessBoard.start(pStage);
    }
    
    public void friendButton(MouseEvent event)
    {
        currentAILevel = -1;
        ChessBoardGUI chessBoard = new ChessBoardGUI();
        chessBoard.start(pStage);
    }
    
    public void chess960Button(MouseEvent event)
    {
        currentAILevel = -1;
        playingChess960 = true;
        ChessBoardGUI chessBoard = new ChessBoardGUI();
        chessBoard.start(pStage);
    }
}
