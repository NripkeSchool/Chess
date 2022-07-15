/**
 * This is the Piece class which is the parent class to all other different 
 * pieces on the chessboard. It will contain all methods to be passed down
 * onto all other pieces.
 * 
 * Noah Ripke
 */

abstract public class Piece
{
    protected int xPosition; //Current Row/Rank on the board
    protected int yPosition; //Current Column/File on the board
    
    protected int moves; //How many moves has it made
    
    protected String pieceType; //Type of piece
    protected String pieceColor; //Color of piece
    protected String oppositeColor; //Opposite color of piece (Used for convience sake)
    
    protected String whiteImage; //White piece image
    protected String blackImage; //Black piece image
    
    protected int[][] attackingSquares; //The squares that the piece currently has under control
    
    protected int pieceValue;
    
    /**
     * Constructor method
     */
    public Piece(int newX, int newY, String color, String type, String bI, String wI)
    {
      xPosition = newX;
      yPosition = newY;
      pieceColor = color;
      pieceType = type;
      moves = 0;
      whiteImage = wI;
      blackImage = bI;
 
      if (pieceColor.equals("w")) {oppositeColor = "b";}else {oppositeColor = "w";}
      
      if (pieceType.equals("p"))
      {
        attackingSquares = new int[2][2];  
        pieceValue = 1;
      }else if (pieceType.equals("r"))
      {
        attackingSquares = new int[2][14];
        pieceValue = 5;
      }else if (pieceType.equals("n") || pieceType.equals("k"))
      {
        attackingSquares = new int[2][8];     
        if (pieceType.equals("n"))
        {
          pieceValue = 3;  
        }else {
          pieceValue = 999; //The king is worth the game, so make it super valuable
        }
      }else if (pieceType.equals("b"))
      {
        attackingSquares = new int[2][13]; 
        pieceValue = 3;
      }else if (pieceType.equals("q"))
      {
        attackingSquares = new int[2][27]; //13+14 (Bishop+Rook)    
        pieceValue = 9;
      }
      
      for (int i = 0; i < attackingSquares[0].length; i++)
      {
       attackingSquares[0][i] = 10; //10 is the sign that there are no more attacking squares past this point
       attackingSquares[1][i] = 10;
      }
    }
    
    public void setX(int newX)
    {
      xPosition = newX;  
    }
    
    public void setY(int newY)
    {
     yPosition = newY;   
    }
    
    public int getPieceValue()
    {
        return pieceValue;
    }
    
    public String getColor()
    {
       return pieceColor;
    }
    
    public int[][] getAttackSquares()
    {
     return attackingSquares;   
    }
    
    public String getPieceType()
    {
       return pieceType;
    }
    
    public int getMoves()
    {
       return moves;
    }
    
    public int[] getPosition()
    {
      int[] a = {xPosition, yPosition};  
      return a;
    }
    
    public String getSquareColor()
    {
      String result = "";
      if (xPosition % 2 == 0)
      {
        if (yPosition % 2 == 0)
        {
         result = "w";   
        }else {
         result = "b";   
        }
      }else {
        if (yPosition % 2 == 0)
        {
         result = "b";   
        }else {
         result = "w";   
        }  
      }
      return result;
    }
    
    public String getImage()
    {
       if (pieceColor.equals("w"))
       {
        return whiteImage;   
       }else {
        return blackImage;  
       }
    }
    
    public abstract boolean isLegalMove(int newX, int newY, ChessBoard board);
    
    public abstract int[][] allLegalMoves(ChessBoard board);
    
    public abstract void updateAttackingSquares(Piece[][] board);

    public void move(int newX, int newY)
    {
        moves++;
        xPosition = newX;
        yPosition = newY;
    }
    
    //Shorthand method for checking if there is a piece at a certain square or not
   public boolean noPiece(ChessBoard board, int x1, int y1)
   {
    if ((x1 < 8 && x1 > -1) && (y1 < 8 && y1 > -1))
    {
     return board.getBoard()[x1][y1] == null;
    }else {
     return false;   
    }
   }
   
   //Shorthand method for checking what color piece is at a square, true for opposite, false for the same
   public boolean isPiece(ChessBoard board, int x1, int y1)
   {
    if ((x1 < 8 && x1 > -1) && (y1 < 8 && y1 > -1))
    {
     if (board.getBoard()[x1][y1].getColor().equals(oppositeColor)) 
     { 
        return true;
     }else {
        return false;
     }
    }else {
     return false;   
    }
   }
   
   public boolean isPiece(Piece[][] b, int x1, int y1)
   {
    if ((x1 < 8 && x1 > -1) && (y1 < 8 && y1 > -1))
    {
     if (b[x1][y1].getColor().equals(oppositeColor)) 
     {
        return true;
     }else { 
        return false;
     }
    }else {
     return false;   
    }
   }
   
   public boolean noPiece(Piece[][] b, int x1, int y1)
   {
    if ((x1 < 8 && x1 > -1) && (y1 < 8 && y1 > -1))
    {
     return b[x1][y1] == null;
    }else {
     return false;   
    }
   }
}