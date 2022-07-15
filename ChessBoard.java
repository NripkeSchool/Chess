/**
 * Contains all information on the current Chess Board
 * Where all the pieces are, whose move it, and methods to do an AI
 */
import java.util.*;

public class ChessBoard
{
    private Piece[][] board; //2D array of pieces this is the main datastructure of chess
    
    private String whoseMove = "w";
    private String whoseStarting; //h = human, c = computer
    
    
    //King variables for convience
    private Piece wKing;
    private Piece bKing;
    
    private int moves = 0;

    //Opening Repitroire (Defenses)
    
    private int[][] scillian = {{1, 4, 3, 4}, {0, 5, 3, 2}, {1, 3, 2, 3}, {0, 1, 2, 2}, {1, 5, 3, 5}};
    private int[][] indian = {{0, 6, 2, 5}, {1, 6, 2, 6}, {0, 5, 1, 6}, {0, 4, 0, 7}, {1, 3, 3, 3}};
    
    //Opening Repitroire (Attacks)
    
    private int[][] london = 
    {{1, 4, 3, 4}, 
    {0, 5, 3, 2}, 
    {1, 3, 2, 3}, 
    {0, 1, 2, 2}, 
    {1, 5, 3, 5}};
    
    /**
     * Constructor for objects of class ChessBoard
     */
    
    public ChessBoard(String wStarting)
    {
       board = new Piece[8][8];
       whoseStarting = wStarting;
       
       if (whoseStarting.equals("h"))
       {
         //Black Pieces on other side of the board
         board[0][0] = new Rook(0, 0, "b");
         board[0][1] = new Knight(0, 1, "b");
         board[0][2] = new Bishop(0, 2, "b");
         board[0][3] = new Queen(0, 3, "b");
         board[0][4] = new King(0, 4, "b");
         board[0][5] = new Bishop(0, 5, "b");
         board[0][6] = new Knight(0, 6, "b");
         board[0][7] = new Rook(0, 7, "b");
          
         //White pieces on your side of the board
         board[7][0] = new Rook(7, 0, "w");
         board[7][1] = new Knight(7, 1, "w");
         board[7][2] = new Bishop(7, 2, "w");
         board[7][3] = new Queen(7, 3, "w");
         board[7][4] = new King(7, 4, "w");
         board[7][5] = new Bishop(7, 5, "w");
         board[7][6] = new Knight(7, 6, "w");
         board[7][7] = new Rook(7, 7, "w");
         
         for (int i = 0; i<8; i++)
         {
          board[1][i] = new Pawn(1, i, "b", 1);   
          board[6][i] = new Pawn(6, i, "w", -1); 
         }
         
         wKing = board[7][4];
         bKing = board[0][4];
       }else {
         //Black Pieces on your side of the board
         board[7][0] = new Rook(7, 0, "b");
         board[7][1] = new Knight(7, 1, "b");
         board[7][2] = new Bishop(7, 2, "b");
         board[7][4] = new Queen(7, 4, "b");
         board[7][3] = new King(7, 3, "b");
         board[7][5] = new Bishop(7, 5, "b");
         board[7][6] = new Knight(7, 6, "b");
         board[7][7] = new Rook(7, 7, "b");
         
         //White pieces on other side of the board
         board[0][0] = new Rook(0, 0, "w");
         board[0][1] = new Knight(0, 1, "w");
         board[0][2] = new Bishop(0, 2, "w");
         board[0][4] = new Queen(0, 4, "w");
         board[0][3] = new King(0, 3, "w");
         board[0][5] = new Bishop(0, 5, "w");
         board[0][6] = new Knight(0, 6, "w");
         board[0][7] = new Rook(0, 7, "w");
         
         for (int i = 0; i<8; i++)
         {
          board[1][i] = new Pawn(1, i, "w", 1);   
          board[6][i] = new Pawn(6, i, "b", -1); 
         }  
         
         wKing = board[0][3];
         bKing = board[7][3];
       } 
       
       updateAttackingMoves(board);
    }
    
    public ChessBoard()
    {
       board = new Piece[8][8];
       Random gen = new Random();
       //This is the constructor for Chess960
       
       //Pawns
       for (int i = 0; i<8; i++)
       {
          board[1][i] = new Pawn(1, i, "b", 1);   
          board[6][i] = new Pawn(6, i, "w", -1); 
       } 
       
       int kingPos = gen.nextInt(8);
       
       board[0][kingPos] = new King(0, kingPos, "b");
       board[7][kingPos] = new King(7, kingPos, "w");
       
       wKing = board[7][kingPos];
       bKing = board[0][kingPos];

       int queenPos = gen.nextInt(8);
       
       while (board[0][queenPos] != null)
       {
          queenPos = gen.nextInt(8);  
       }
       
       board[0][queenPos] = new Queen(0, queenPos, "b");
       board[7][queenPos] = new Queen(7, queenPos, "w");
       
       int rook1Pos = gen.nextInt(8);
       int rook2Pos = gen.nextInt(8);
       
       while (board[0][rook1Pos] != null || board[0][rook2Pos] != null || rook1Pos == rook2Pos)
       {
         rook1Pos = gen.nextInt(8);
         rook2Pos = gen.nextInt(8);
       }
       
       board[0][rook1Pos] = new Rook(0, rook1Pos, "b");
       board[7][rook1Pos] = new Rook(7, rook1Pos, "w");
       
       board[0][rook2Pos] = new Rook(0, rook2Pos, "b");
       board[7][rook2Pos] = new Rook(7, rook2Pos, "w");
       
       int bishop1Pos = gen.nextInt(8);
       int bishop2Pos = gen.nextInt(8);
       
       while (board[0][bishop1Pos] != null || board[0][bishop2Pos] != null || bishop1Pos == bishop2Pos)
       {
         bishop1Pos = gen.nextInt(8);
         bishop2Pos = gen.nextInt(8);
       }
       
       board[0][bishop1Pos] = new Bishop(0, bishop1Pos, "b");
       board[7][bishop1Pos] = new Bishop(7, bishop1Pos, "w");
       
       board[0][bishop2Pos] = new Bishop(0, bishop2Pos, "b");
       board[7][bishop2Pos] = new Bishop(7, bishop2Pos, "w");
       
       int knight1Pos = gen.nextInt(8);
       int knight2Pos = gen.nextInt(8);
       
       while (board[0][knight1Pos] != null || board[0][knight2Pos] != null || knight1Pos == knight2Pos)
       {
         knight1Pos = gen.nextInt(8);
         knight2Pos = gen.nextInt(8);
       }
       
       board[0][knight1Pos] = new Knight(0, knight1Pos, "b");
       board[7][knight1Pos] = new Knight(7, knight1Pos, "w");
       
       board[0][knight2Pos] = new Knight(0, knight2Pos, "b");
       board[7][knight2Pos] = new Knight(7, knight2Pos, "w");
       
       updateAttackingMoves(board);
    }
    
    public boolean isCheckmated(String color, Piece[][] board)
    {
      boolean result = false;
      
      if (isInCheck(color, board))
      {
       for (int row = 0; row<8; row++)
       {
         for (int col = 0; col<8; col++)
         {
           if (board[row][col] != null)
           {
                if (board[row][col].getColor().equals(color))
                {
                    int[][] moves = board[row][col].allLegalMoves(this);
                    if (moves[0][0] != 10)
                    {
                        return false;
                    }
                }
           }
         }  
       }
       result = true;
      } 
      
      return result;
    }
    
    public boolean isStalemated(String color, Piece[][] board)
    {
       boolean result = false;
       
       if (!isInCheck(color, board))
       {
        for (int row = 0; row<8; row++)
        {
         for (int col = 0; col<8; col++)
         {
           if (board[row][col] != null)
           {
                if (board[row][col].getColor().equals(color))
                {
                    int[][] moves = board[row][col].allLegalMoves(this);
                    if (moves[0][0] != 10)
                    {
                        return false;
                    }
                }
           }
         }  
        } 
        result = true;
       }
       
       return result;
    }
    
    public void findRandomAIMove(String move)
    {
        int finalX1 = 0;
        int finalX2 = 0;
        int finalY1 = 0;
        int finalY2 = 0;
        
        int[][] pieceLoc = new int[2][32];
        int locLength = 0;
        
        for (int row = 0; row<8; row++)
        {
           for (int col = 0; col<8; col++)
           {
             if (board[row][col] != null && board[row][col].getColor().equals(move))
             {
                if (board[row][col].allLegalMoves(this)[0][0] != 10)
                {
                 pieceLoc[0][locLength] = board[row][col].getPosition()[0];
                 pieceLoc[1][locLength] = board[row][col].getPosition()[1];
                 locLength++;
                }
             }
           } 
        }
        
        Random gen = new Random();
        
        int ranPiece = gen.nextInt(locLength);
        Piece rP = board[pieceLoc[0][ranPiece]][pieceLoc[1][ranPiece]];
        
        finalX1 = pieceLoc[0][ranPiece];
        finalY1 = pieceLoc[1][ranPiece];
        
        int[][] moves = rP.allLegalMoves(this);
        int moveLength = 0;
        
        for (int col = 0; col<moves[0].length; col++)
        {
           if (moves[0][col] != 10)
           {
               moveLength++;
           }else {
               col = moves[0].length;
           }
        }
        
        int ranMove = gen.nextInt(moveLength);
        finalX2 = moves[0][ranMove];
        finalY2 = moves[1][ranMove]; 
        
        if(!move(finalX1, finalY1, finalX2, finalY2))
        {
            System.out.println("Didn't work");
            System.out.println("Piece: " + rP.getColor() + rP.getPieceType());
            System.out.println("("+finalX1 + ", " + finalY1+")");
            System.out.println("("+finalX2 + ", " + finalY2+")");  
        }
        
    }
    
    public void level1AI(String move)
    {
       int finalX1 = 0;
       int finalX2 = 0;
       int finalY1 = 0; 
       int finalY2 = 0;
       int change = 0;
      
       double bestEval;
       if (move.equals("w"))
       {
         bestEval = -999;  //This is to prevent the AI from not making a move
       }else {
         bestEval = 999;  //This is to prevent the AI from not making a move
       }
        
       for (int row = 0; row<8; row++)
       {
         for (int col = 0; col<8; col++)
         {
           if (board[row][col] != null && board[row][col].getColor().equals(move))
           { 
             int[][] moves = board[row][col].allLegalMoves(this);
             int currentX1 = row; //Purely for self-documenting reasoning
             int currentY1 = col; //Purely for self-documenting reasoning
             
             int currentX2 = 0;
             int currentY2 = 0;
             for (int i = 0; i<moves[0].length; i++)
             {
                if (moves[0][i] != 10)
                {
                 currentX2 = moves[0][i];
                 currentY2 = moves[1][i];
                 
                 Piece temp = board[currentX2][currentY2];
                 
                 board[currentX1][currentY1].setX(currentX2);
                 board[currentX1][currentY1].setY(currentY2);
                 
                 board[currentX2][currentY2] = board[currentX1][currentY1];
                 board[currentX1][currentY1] = null;
           
                 updateAttackingMoves(board);
                 
                 double eval = positionalEvaluation(board);
                 
                 board[currentX1][currentY1] = board[currentX2][currentY2];
                 board[currentX2][currentY2] = temp;
                 
                 board[currentX1][currentY1].setX(currentX1);
                 board[currentX1][currentY1].setY(currentY1);
                 
                 //System.out.println(board[currentX1][currentY1].getPosition()[0] + ", " + board[currentX1][currentY1].getPosition()[1]);
                 //System.out.println(currentX1 + ", " + currentY1 + ", " + currentX2 + ", " + currentY2);
                 if (move.equals("w") && eval >= bestEval)
                 {
                  bestEval = eval;  
                  finalX1 = currentX1;
                  finalX2 = currentX2;
                  finalY1 = currentY1;
                  finalY2 = currentY2;
                  change++;
                 }else if (move.equals("b") && eval <= bestEval)
                 {
                  bestEval = eval;   
                  finalX1 = currentX1;
                  finalX2 = currentX2;
                  finalY1 = currentY1;
                  finalY2 = currentY2;
                  change++;
                 }
                 
                }else {
                 i = moves[0].length;   
                }
             }
           }  
         }  
       }
     
       if (change != 0)
       {
         move(finalX1, finalY1, finalX2, finalY2);
       }else {
         findRandomAIMove(move);   
        }
    }
    
    public double findLevel1AI(String move)
    {
       double bestEval;
       if (move.equals("w"))
       {
         bestEval = -999;  //This is to prevent the AI from not making a move
       }else {
         bestEval = 999;  //This is to prevent the AI from not making a move
       }
        
       for (int row = 0; row<8; row++)
       {
         for (int col = 0; col<8; col++)
         {
           if (board[row][col] != null && board[row][col].getColor().equals(move))
           { 
             int[][] moves = board[row][col].allLegalMoves(this);
             int currentX1 = row; //Purely for self-documenting reasoning
             int currentY1 = col; //Purely for self-documenting reasoning
             
             int currentX2 = 0;
             int currentY2 = 0;
             for (int i = 0; i<moves[0].length; i++)
             {
                if (moves[0][i] != 10)
                {
                 currentX2 = moves[0][i];
                 currentY2 = moves[1][i];
                 
                 Piece temp = board[currentX2][currentY2];
                 
                 board[currentX1][currentY1].setX(currentX2);
                 board[currentX1][currentY1].setY(currentY2);
                 
                 board[currentX2][currentY2] = board[currentX1][currentY1];
                 board[currentX1][currentY1] = null;
                        
                 updateAttackingMoves(board);
                 
                 double eval = positionalEvaluation(board);
                 
                 board[currentX1][currentY1] = board[currentX2][currentY2];
                 board[currentX2][currentY2] = temp;
                 
                 board[currentX1][currentY1].setX(currentX1);
                 board[currentX1][currentY1].setY(currentY1);
                 
                 
                 if (move.equals("w") && eval >= bestEval)
                 {
                  bestEval = eval;  
                 }else if (move.equals("b") && eval <= bestEval)
                 {
                  bestEval = eval;   
                 }
                 
                }else {
                 i = moves[0].length;   
                }
             }
           }  
         }  
       }

       return bestEval;
    }
    
    public void level2AI(String move)
    {
       int finalX1 = 0;
       int finalX2 = 0;
       int finalY1 = 0;
       int finalY2 = 0;
       int changes = 0;
       
       double bestEval;
       double originalEval = positionalEvaluation(board);
       String oppositeColor;
       
       
       if (move.equals("w"))
       {
         bestEval = -999;  //This is to prevent the AI from not making a move
         oppositeColor = "b";
       }else {
         bestEval = 999;  //This is to prevent the AI from not making a move
         oppositeColor = "w";
       }
        
       for (int row = 0; row<8; row++)
       {
        for (int col = 0; col<8; col++)
        {
           if (board[row][col] != null && board[row][col].getColor().equals(move))
           { 
             int[][] moves = board[row][col].allLegalMoves(this);
             int currentX1 = row; //Purely for self-documenting reasoning
             int currentY1 = col; //Purely for self-documenting reasoning
             
             int currentX2 = 0;
             int currentY2 = 0;
             for (int i = 0; i<moves[0].length; i++)
             {
                if (moves[0][i] != 10)
                {
                 currentX2 = moves[0][i];
                 currentY2 = moves[1][i];
                 
                 Piece temp = board[currentX2][currentY2];
                 
                 
                 board[currentX1][currentY1].setX(currentX2);
                 board[currentX1][currentY1].setY(currentY2);
                 
                 board[currentX2][currentY2] = board[currentX1][currentY1];
                 board[currentX1][currentY1] = null;
                 
                 updateAttackingMoves(board);
                 
                 double eval = positionalEvaluation(board);
                 
                 double newEval = findLevel1AI(oppositeColor);
                 
                 
                 board[currentX1][currentY1] = board[currentX2][currentY2];
                 board[currentX2][currentY2] = temp;
                 
                 board[currentX1][currentY1].setX(currentX1);
                 board[currentX1][currentY1].setY(currentY1);
                 
                 if (move.equals("w") && eval >= bestEval && (newEval > (originalEval-1))) //1 is the accuracy or the box it must be in between
                 {
                  bestEval = eval;  
                  finalX1 = currentX1;
                  finalX2 = currentX2;
                  finalY1 = currentY1;
                  finalY2 = currentY2;
                  changes++;
                  //System.out.println("Best Eval: " + bestEval);
                  //System.out.println("New Eval: " + newEval + " Old Eval: " + originalEval);
                  //System.out.println("Old Location: " + finalX1 + ", " + finalY1 + " New Location: " + finalX2 + ", " + finalY2);
                  //System.out.println();
                 }else if (move.equals("b") && eval <= bestEval && (newEval < (originalEval+1))) //1 is the accuracy or the box it must be in between
                 {
                  bestEval = eval;   
                  finalX1 = currentX1;
                  finalX2 = currentX2;
                  finalY1 = currentY1;
                  finalY2 = currentY2;
                  changes++;
                  //System.out.println("Best Eval: " + bestEval);
                  //System.out.println("New Eval: " + newEval + " Old Eval: " + originalEval);
                  //System.out.println("Old Location: " + finalX1 + ", " + finalY1 + " New Location: " + finalX2 + ", " + finalY2);
                  //System.out.println();
                 }
                 
                }else {
                 i = moves[0].length;   
                }
             }
           }    
        }
       }
       
       if (changes != 0)
       {
         move(finalX1, finalY1, finalX2, finalY2);
       }else {
         level1AI(move);
         //System.out.println("Worse Move");
       }
       //System.out.println("\n\n");
    }  
 
    public double findLevel2AI(String move)
    {
       int changes = 0;
       
       double bestEval;
       double originalEval = positionalEvaluation(board);
       String oppositeColor;
      
           
       if (move.equals("w"))
       {
         bestEval = -999;  //This is to prevent the AI from not making a move
         oppositeColor = "b";
       }else {
         bestEval = 999;  //This is to prevent the AI from not making a move
         oppositeColor = "w";
       }
        
       for (int row = 0; row<8; row++)
       {
        for (int col = 0; col<8; col++)
        {
          if (board[row][col] != null && board[row][col].getColor().equals(move))
           { 
             int[][] moves = board[row][col].allLegalMoves(this);
             int currentX1 = row; //Purely for self-documenting reasoning
             int currentY1 = col; //Purely for self-documenting reasoning
             
             int currentX2 = 0;
             int currentY2 = 0;
             for (int i = 0; i<moves[0].length; i++)
             {
                if (moves[0][i] != 10)
                {
                 currentX2 = moves[0][i];
                 currentY2 = moves[1][i];
                 
                 Piece temp = board[currentX2][currentY2];
                 
                 
                 board[currentX1][currentY1].setX(currentX2);
                 board[currentX1][currentY1].setY(currentY2);
                 
                 board[currentX2][currentY2] = board[currentX1][currentY1];
                 board[currentX1][currentY1] = null;
                 
                 updateAttackingMoves(board);
                 
                 double eval = positionalEvaluation(board);
                 
                 double newEval = findLevel1AI(oppositeColor);
                 
                 
                 board[currentX1][currentY1] = board[currentX2][currentY2];
                 board[currentX2][currentY2] = temp;
                 
                 board[currentX1][currentY1].setX(currentX1);
                 board[currentX1][currentY1].setY(currentY1);
                 if (move.equals("w") && eval >= bestEval && (newEval > (originalEval-0.5))) //0.5 is the accuracy or the box it must be in between
                 {
                  bestEval = eval;  
                  changes++;
                 }else if (move.equals("b") && eval <= bestEval && (newEval < (originalEval+0.5))) //0.5 is the accuracy or the box it must be in between
                 {
                  bestEval = eval;
                  changes++;
                 }
                 
                }else {
                 i = moves[0].length;   
                }
             }
           }    
        }
       }
       
       if (changes != 0)
       {
          return bestEval; 
       }else {
          return findLevel1AI(move); 
       }
    }  
    
    
    public void level3AI(String move)
    {
       //Level 3 AI will start using openings to play aginast its opponents
       
       if (whoseStarting.equals("h"))
       {
         //Human started, so playing defenses   
         int currentOpenMove = moves/2;
         if (currentOpenMove < 5)
         {
          if(!move(indian[currentOpenMove][0], indian[currentOpenMove][1], indian[currentOpenMove][2], indian[currentOpenMove][3]))
          {
            level2AI(move);
          }
         }else {
          level2AI(move);  
         }
       }else {
        //Computer started, so playing attacks
        int currentOpenMove = moves/2;
        if (currentOpenMove < 5)
        {
         if(!move(london[currentOpenMove][0], london[currentOpenMove][1], london[currentOpenMove][2], london[currentOpenMove][3]))
         {
           level2AI(move);
         }
        }else {
          level2AI(move);  
        }
       }
       
    }
    //The simpleEvaluation method will do an evaluation on the current board using only materialistic values of the pieces
    public int simpleEvaluation(Piece[][] b)
    {
       int whiteEval = 0;
       int blackEval = 0;
       
       for (int row = 0; row<8; row++)
       {
         for (int col = 0; col<8; col++)
         {
           if (b[row][col] != null)
           {
              if (b[row][col].getColor().equals("w"))
              {
                whiteEval += b[row][col].getPieceValue();  
              }else {
                blackEval += b[row][col].getPieceValue();    
              }
           }
         }
       }
       
       return whiteEval-blackEval; //Will return negative if black is winning, and positive if white is winning
    }
    
    public double positionalEvaluation(Piece[][] b)
    {
       double eval = simpleEvaluation(b);
        
       for (int row = 0; row<8; row++)
       {
         for (int col = 0; col<8; col++)
         {
           if (b[row][col] != null)
           {
              if (b[row][col].getColor().equals("w"))
              {
                for (int i = 0; i<b[row][col].getAttackSquares()[0].length; i++)
                {
                 if (b[row][col].getAttackSquares()[0][i] != 10)
                 {
                   eval += 0.01; 
                 }else {
                   i = 27; //Speed up the process
                 }
                }
              }else {
                for (int i = 0; i<b[row][col].getAttackSquares()[0].length; i++)
                {
                 if (b[row][col].getAttackSquares()[0][i] != 10)
                 {
                   eval -= 0.01; 
                 }else {
                   i = 27; //Speed up the process
                 }
                }
              }
           }
         }
       }
       
       if (isCheckmated("w", b))
       {
         eval = -1000;   
       }else if (isCheckmated("b", b))
       {
         eval = 1000;  
       }

       
       return eval;
    }
    
    public String getMove()
    {
     return whoseMove;   
    }
    
    public String getSquareColor(int x1, int y1)
    {
      String result = "";
      if (x1 % 2 == 0)
      {
        if (y1 % 2 == 0)
        {
         result = "w";   
        }else {
         result = "b";   
        }
      }else {
        if (y1 % 2 == 0)
        {
         result = "b";   
        }else {
         result = "w";   
        }  
      }
      return result;
    }
    
    public Piece[][] getBoard()
    {
        return board;
    }
    
    public boolean move(int x1, int y1, int x2, int y2)
    {
        boolean result = false;
        
        if (board[x1][y1].getPieceType().equals("k"))
        {
           if (board[x2][y2] != null && board[x2][y2].getPieceType().equals("r") && board[x2][y2].getColor().equals(board[x1][y1].getColor()))
           {
             if (board[x1][y1].isLegalMove(x2, y2, this))
             {
              if (y2 == 7)
              {
                board[x1][6]  = board[x1][y1];
                board[x1][y1] = null;
                
                board[x1][5]  = board[x2][y2];
                board[x2][y2] = null;
                
                board[x1][6].move(x1, 6);
                board[x1][5].move(x1, 5);
              }else {
                board[x1][2]  = board[x1][y1];
                board[x1][y1] = null;
                
                board[x1][3]  = board[x2][y2];
                board[x2][y2] = null;
                
                board[x1][2].move(x1, 2);
                board[x1][3].move(x1, 3);  
              }
              
              result = true;
              moves++;
              updateAttackingMoves(board);
              if (whoseMove.equals("w"))
              {
                whoseMove = "b";
              }else {
                whoseMove = "w"; 
              }
             }
           }
        }
        
        if (!result && board[x1][y1].isLegalMove(x2, y2, this))
        {            
            board[x2][y2] = board[x1][y1];
            board[x1][y1] = null;
            
            board[x2][y2].move(x2, y2);
            result = true;
            
            if (whoseMove.equals("w"))
            {
                whoseMove = "b";
            }else {
                whoseMove = "w"; 
            }
            
            if (board[x2][y2].getPieceType().equals("p"))
            {
              if (x2 == 0 || x2 == 7)
              {
                board[x2][y2] = new Queen(x2, y2, board[x2][y2].getColor());
              }  
            }
            updateAttackingMoves(board);
            moves++;
        }
        return result;
    }
    
    
    public boolean isInCheck(String color, Piece[][] b)
    {
     boolean result = false;
      
     if (color.equals("w"))
     {
      int wKingX = 0;
      int wKingY = 0;
      for (int row = 0; row<8; row++)
      {
         for (int col = 0; col<8; col++)
         {
             if (b[row][col] != null && b[row][col].getPieceType().equals("k"))
             {
               if(b[row][col].getColor().equals("w"))   
               {
                 wKingX = b[row][col].getPosition()[0];
                 wKingY = b[row][col].getPosition()[1];
               }
             }
         }
      }
       
       for (int row = 0; row < 8; row++)
       {
        for (int col = 0; col < 8; col++)
        {
         if (b[row][col] != null)
         {
          if (b[row][col].getColor().equals("b"))
          {
              int[][] moves = b[row][col].getAttackSquares();
              
              for (int i = 0; i < moves[0].length; i++)
              {
               if (moves[0][i] != 10)
               {
                 if (moves[0][i] == wKingX && moves[1][i] == wKingY)
                 {
                   return true;
                 }
               }else {
                  i = moves[0].length;   
               }
              }
          }
         }
        }
       }
     }else {
       int bKingX = 0;
       int bKingY = 0;  
       for (int row = 0; row<8; row++)
       {
         for (int col = 0; col<8; col++)
         {
             if (b[row][col] != null && b[row][col].getPieceType().equals("k"))
             {
               if(b[row][col].getColor().equals("b"))   
               {
                 bKingX = b[row][col].getPosition()[0];
                 bKingY = b[row][col].getPosition()[1];
               }
             }
         }
       }
       
       for (int row = 0; row < 8; row++)
       {
        for (int col = 0; col < 8; col++)
        {
         if (b[row][col] != null)
         {
          if (b[row][col].getColor().equals("w"))
          {
              int[][] moves = board[row][col].getAttackSquares();
              
              for (int i = 0; i < moves[0].length; i++)
              {
               if (moves[0][i] != 10)
               {
                 if (moves[0][i] == bKingX && moves[1][i] == bKingY)
                 {
                   return true; 
                 }
               }else {
                 i = moves[0].length; 
               }
              }
          }
         }
        }
       }  
     }
     
     return result;
    }
    
    public boolean squareInCheck(int x1, int y1, String oppositeColor, Piece[][] b)
    {
     boolean result = false;
       
       for (int row = 0; row < 8; row++)
       {
        for (int col = 0; col < 8; col++)
        {
         if (b[row][col] != null)
         {
          if (b[row][col].getColor().equals(oppositeColor))
          {
              int[][] moves = b[row][col].getAttackSquares();
              
              for (int i = 0; i < moves[0].length; i++)
              {
               if (moves[0][i] != 10)
               {
                 if (moves[0][i] == x1 && moves[1][i] == y1)
                 {
                   return true;
                 }
               }else {
                  i = moves[0].length;   
               }
              }
          }
         }
        }
       }
     return result;
    }
    
    public void updateAttackingMoves(Piece[][] b)
    {
       for (int row = 0; row < 8; row++)
       {
        for (int col = 0; col < 8; col++)
        {
          if (b[row][col] != null)
          {
           b[row][col].updateAttackingSquares(b); 
          }
        }
       }
    }
    
    
    public Piece getKing(String color)
    {
        if (color.equals("w"))
        {
         return wKing;   
        }else {
         return bKing;   
        }
    }
    
  
 
}