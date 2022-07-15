/**
 * Class contains all information and code required to run the chessboard and print out all the pieces
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import java.util.*;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.text.TextAlignment;
import java.io.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import java.text.DecimalFormat;

public class ChessBoardGUI extends Application
{
   private String startColor;
   private int AILevel = ChessHomeGUI.currentAILevel;
   private ChessBoard chessBoard;
   
   private Group board = new Group();
   private Group pieces = new Group();
   private Group otherBoxes = new Group();
   
   private Group root = new Group(board, pieces, otherBoxes);
   
   private Rectangle[][] squares = new Rectangle[8][8];
  
   private Color black = Color.rgb(214,138,89);
   private Color white = Color.rgb(253,213,177);
   private Color green = Color.rgb(153, 200, 51);
   private Color red = Color.rgb(255, 75, 51);
   
   private Image bKingI = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/f/f0/Chess_kdt45.svg/1024px-Chess_kdt45.svg.png");
   private Image wKingI = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Chess_klt45.svg/1024px-Chess_klt45.svg.png");
    
   private Rectangle checkBox = new Rectangle();
   private Text material;
   
   private boolean lifted = false;
   private int pieceClickX;
   private int pieceClickY;
   
   private int screenSize = 1000;
   private int squareSize = screenSize/8;
   
   private Scene scene = new Scene(root, screenSize+500, screenSize, ChessHomeGUI.backgroundColor);
   private Stage pStage;
   
   private boolean gameOver = false;
   public void start(Stage primaryStage)
   {
     pStage = primaryStage;
     Random gen = new Random();
     if (gen.nextInt(2) == 0)
     {
         startColor = "h";
     }else {
         startColor = "c";
     }
     
     if (ChessHomeGUI.playingChess960)
     {
        chessBoard = new ChessBoard();
     }else {
        chessBoard = new ChessBoard(startColor); 
     }
     
     board.setOnMouseClicked(this::move);  
     pieces.setOnMouseClicked(this::move);
     
     for (int i = 0; i<8;i++)
     { 
         for (int j = 0; j<8; j++)
         {
           Rectangle rect;
           if (i % 2 == 0)
           {
             if (j % 2 == 0)
             {
               rect = new Rectangle(j*squareSize, i*squareSize, squareSize, squareSize);
               rect.setFill(white);
               board.getChildren().add(rect);
             }else {
               rect = new Rectangle(j*squareSize, i*squareSize, squareSize, squareSize);
               rect.setFill(black);
               board.getChildren().add(rect);
             }
            }else {
             if (j % 2 == 0)
             {
               rect = new Rectangle(j*squareSize, i*squareSize, squareSize, squareSize);
               rect.setFill(black);
               board.getChildren().add(rect);
             }else {
               rect = new Rectangle(j*squareSize, i*squareSize, squareSize, squareSize);
               rect.setFill(white);
               board.getChildren().add(rect);
             }
            }
           squares[i][j] = rect;
         }
     }
     
     for (int j = 0; j<8; j++)
     {
      if (startColor.equals("h"))
      {
       char l = (char) (97+j);
       
       Text num = new Text(screenSize-(squareSize*.1), (j*squareSize)+(squareSize*.15), ""+(8-j));    
       Text letter = new Text((j*squareSize)+(squareSize*.05), screenSize-(squareSize*.05), ""+l);    
       if (chessBoard.getSquareColor(j, 7).equals("w"))
       {
        num.setFill(black);  
        letter.setFill(black);  
       }else {
        num.setFill(white); 
        letter.setFill(white);  
       }
       num.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, squareSize/8));
       letter.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, squareSize/8));
       
       board.getChildren().add(num);
       board.getChildren().add(letter);
      }else {
       int newJ = 7-j;
       char l = (char) (97+newJ);
       
       Text num = new Text(screenSize-(squareSize*.1), (j*squareSize)+(squareSize*.15), ""+(j+1));    
       Text letter = new Text((j*squareSize)+(squareSize*.05), screenSize-(squareSize*.05), ""+l);    
       if (chessBoard.getSquareColor(j, 7).equals("w"))
       {
        num.setFill(black);  
        letter.setFill(black);  
       }else {
        num.setFill(white); 
        letter.setFill(white);  
       }
       num.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, squareSize/8));
       letter.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, squareSize/8));
       
       board.getChildren().add(num);
       board.getChildren().add(letter); 
      }
     }
     
     Rectangle peoplePlaying = new Rectangle(screenSize+50, 25, 400, screenSize/3);
     peoplePlaying.setFill(white);
     peoplePlaying.setStrokeWidth(10);
     peoplePlaying.setStroke(black);
     
     ImageView wK = new ImageView(wKingI);
     ImageView bK = new ImageView(bKingI);
     
     wK.setFitWidth(squareSize);
     bK.setFitWidth(squareSize);
     wK.setFitHeight(squareSize);
     bK.setFitHeight(squareSize);
     
     wK.setLayoutX(screenSize+75);
     wK.setLayoutY(35);
     
     bK.setLayoutX(screenSize+75);
     bK.setLayoutY(35+(squareSize*1.25));   
     
     Text playingBlack = new Text(bK.getLayoutX()+squareSize, bK.getLayoutY()+(3*squareSize/4), "aaaaaaa");
     playingBlack.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, 30));
     playingBlack.setFill(black);
     
     Text playingWhite = new Text(wK.getLayoutX()+squareSize, wK.getLayoutY()+(3*squareSize/4), "aaaaaaa");
     playingWhite.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, 30));
     playingWhite.setFill(black);
     
     if (startColor.equals("h") && AILevel != -1)
     {
       playingWhite.setText("You"); 
       playingBlack.setText("Computer " + AILevel);  
     }else if (startColor.equals("c") && AILevel != -1) {
       playingWhite.setText("Computer " + AILevel); 
       playingBlack.setText("You"); 
     }else {
       playingWhite.setText("Also You"); 
       playingBlack.setText("You");     
     }
     
     material = new Text(screenSize+50, screenSize/2.5+screenSize/30, "+0");
     material.setFill(black);
     material.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, screenSize/30));
     
     Button goBack = new Button("Go Back Home");
     goBack.setPrefWidth(500);
     goBack.setLayoutX(screenSize);
     goBack.setLayoutY(screenSize-25);
     goBack.setOnMouseClicked(this::goHome);
     
     otherBoxes.getChildren().addAll(peoplePlaying, wK, bK, playingBlack, playingWhite, material, goBack);
     if (startColor.equals("c"))
     {
      playAIMove();  
     }
     setup();
     
     primaryStage.setTitle("Chess");
     primaryStage.setScene(scene);
     primaryStage.show();
   }
   
   public void goHome(MouseEvent event)
   {
     ChessHomeGUI.playingChess960 = false;
     ChessHomeGUI home = new ChessHomeGUI();   
     home.start(pStage);
   }
   
   public void move(MouseEvent event)
   {
     int x = (int) event.getY()/squareSize;
     int y = (int) event.getX()/squareSize;
     if (!gameOver)
     {
      if (lifted)
      {  
       if (chessBoard.move(pieceClickX, pieceClickY, x, y))  
       {
        lifted = false;
        if(!chessBoard.isCheckmated(chessBoard.getMove(), chessBoard.getBoard()) && !chessBoard.isStalemated(chessBoard.getMove(), chessBoard.getBoard())) 
        {
          playAIMove();
        }
        setup();
       }else {
         lifted = false;
       }
       if (chessBoard.getSquareColor(pieceClickX, pieceClickY).equals("w"))
       {
              squares[pieceClickX][pieceClickY].setFill(white);
       }else {
              squares[pieceClickX][pieceClickY].setFill(black);
       }
      }else {
       if (chessBoard.getBoard()[x][y] != null && chessBoard.getBoard()[x][y].getColor().equals(chessBoard.getMove()))
       {
           lifted = true;
           pieceClickX = x;
           pieceClickY = y;
           squares[x][y].setFill(green);
       }
      } 
     }
   }
   
   public void playAIMove()
   {
     if (AILevel == 0)
     {
       chessBoard.findRandomAIMove(chessBoard.getMove());  
     }else if (AILevel == 1)
     {
       chessBoard.level1AI(chessBoard.getMove());
     }else if (AILevel == 2)
     {
       chessBoard.level2AI(chessBoard.getMove());  
     }else if (AILevel == 3)
     {
       chessBoard.level3AI(chessBoard.getMove());  
     }
   }
   
   public void setup()
   {
     pieces.getChildren().clear();
     
     for (int i = 0; i<8;i++)
     { 
         for (int j = 0; j<8; j++)
         {
           if (chessBoard.getBoard()[i][j] != null)
           {
             Image pieceImage = new Image(chessBoard.getBoard()[i][j].getImage()); 
             ImageView piece = new ImageView(pieceImage);
             
             piece.setLayoutX(j*squareSize);
             piece.setLayoutY(i*squareSize);
             
             piece.setFitWidth(squareSize);
             piece.setFitHeight(squareSize);
             
             pieces.getChildren().add(piece);
            }
         }
     }
     
     if(chessBoard.isInCheck(chessBoard.getMove(), chessBoard.getBoard()))
     {
        int x1, y1;
        if (chessBoard.getMove().equals("w"))
        {
          x1 = chessBoard.getKing("w").getPosition()[0];
          y1 = chessBoard.getKing("w").getPosition()[1];
          
          checkBox.setFill(null);
          checkBox.setStrokeWidth(5);
          checkBox.setStroke(red);
          
          checkBox.setX(y1*squareSize);
          checkBox.setY(x1*squareSize);
          
          checkBox.setWidth(squareSize);
          checkBox.setHeight(squareSize);
          
          pieces.getChildren().add(checkBox);
        }else {
          x1 = chessBoard.getKing("b").getPosition()[0];
          y1 = chessBoard.getKing("b").getPosition()[1];  
          
          checkBox.setFill(null);
          checkBox.setStrokeWidth(5);
          checkBox.setStroke(red);
          checkBox.setX(y1*squareSize);
          checkBox.setY(x1*squareSize);
          
          checkBox.setWidth(squareSize);
          checkBox.setHeight(squareSize);
          
          pieces.getChildren().add(checkBox);
        }
     }
     double materialAdv = chessBoard.positionalEvaluation(chessBoard.getBoard());
     DecimalFormat matFmt = new DecimalFormat("0.##");
     
     if (materialAdv > 0)
     {
       material.setText("+"+matFmt.format(materialAdv)+" White is winning");
     }else if (materialAdv == 0)
     {
       material.setText("+-0 Draw");   
     }else {
       material.setText(matFmt.format(materialAdv)+" Black is winning");  
      }
     
     if (chessBoard.isCheckmated(chessBoard.getMove(), chessBoard.getBoard()))
     {
       gameOver = true;
       if (chessBoard.getMove().equals("w"))
       {
         material.setText("White is checkmated");   
       }else {
         material.setText("Black is checkmated");   
       }
     }
      
     if (chessBoard.isStalemated(chessBoard.getMove(), chessBoard.getBoard()))
     {
       gameOver = true;
      
       if (chessBoard.getMove().equals("w"))
       {
         material.setText("White is stalemated");   
       }else {
         material.setText("Black is stalemated");   
       }
     }
    }
}