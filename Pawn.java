/**
 * Class describing a pawn piece on a chess board
 * 
 * w = White
 * b = Black
 * 
 * p = Pawn
 */

public class Pawn extends Piece
{
   int direction; //Either 1 or -1 (1 Means going down the board, -1 means going up the board)
   /**
   * Constructor method
   **/
    
   public Pawn(int newX, int newY, String color, int direct)
   {
     super(newX, newY, color, "p", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c7/Chess_pdt45.svg/1024px-Chess_pdt45.svg.png", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/Chess_plt45.svg/1024px-Chess_plt45.svg.png");
     direction = direct;
   }
   
    public boolean isLegalMove(int newX, int newY, ChessBoard board)
    {
     boolean result = false;
     
     if (noPiece(board, newX, newY) || isPiece(board, newX, newY))
     {
      if (moves > 0)
      {
       if ((newX == xPosition+direction) && (yPosition == newY)) //Can it go forwards just one square
       {
           if (noPiece(board, newX, newY))
           {
             result = true;   
           }
       }else if ((newX == xPosition+direction) && ((yPosition+1 == newY) || (yPosition-1 == newY)))
       {
           if (!noPiece(board, newX, newY) && isPiece(board, newX, newY)) //If there is a piece of the opposite color
           {
             result = true;   
           }
       }
      }else {
       if ((newX == xPosition+direction) && (yPosition == newY)) //Can it go forwards just one square
       {
           if (noPiece(board, newX, newY))
           {
             result = true;   
           }
       }else if ((newX == xPosition+direction) && ((yPosition+1 == newY) || (yPosition-1 == newY)))
       {
           if (!noPiece(board, newX, newY) && isPiece(board, newX, newY)) //If there is a piece of the opposite color
           {
             result = true;   
           }
       }else if ((newX == xPosition+direction+direction) && (yPosition == newY)) //If it went fowards two squares
       {
           if (noPiece(board, newX, newY) && noPiece(board, newX-direction, newY))
           {
             result = true;   
           }
       }
      }
      Piece temp = board.getBoard()[newX][newY];
     
      board.getBoard()[newX][newY] = board.getBoard()[xPosition][yPosition];
      board.getBoard()[xPosition][yPosition] = null;
      board.updateAttackingMoves(board.getBoard());
    
      if (board.isInCheck(pieceColor, board.getBoard()))
      {
         result = false;
      }
     
      board.getBoard()[xPosition][yPosition] = board.getBoard()[newX][newY];
      board.getBoard()[newX][newY] = temp;
      board.updateAttackingMoves(board.getBoard());
     }
     
     
     return result;
    }
   
   public int[][] allLegalMoves(ChessBoard board)
   {
     int[][] moves = new int[2][4];
     int position = 0;
     
     if (isLegalMove(xPosition+direction, yPosition-1, board))
     {
        moves[0][position] = xPosition+direction;
        moves[1][position] = yPosition-1;
        position++; 
     }
     
     if (isLegalMove(xPosition+direction, yPosition+1, board))
     {
        moves[0][position] = xPosition+direction;
        moves[1][position] = yPosition+1;
        position++; 
     }
     
     if (isLegalMove(xPosition+direction, yPosition, board))
     {
        moves[0][position] = xPosition+direction;
        moves[1][position] = yPosition;
        position++; 
     }
     
     if (isLegalMove(xPosition+direction+direction, yPosition, board))
     {
        moves[0][position] = xPosition+direction+direction;
        moves[1][position] = yPosition;
        position++; 
     }
     
     for (int i = position; i<moves[0].length; i++)
     {
       moves[0][i] = 10;
     }
     return moves;
   }
   
   public void updateAttackingSquares(Piece[][] board)
   {
       int position = 0;
       if(noPiece(board, xPosition+direction, yPosition-1) || isPiece(board, xPosition+direction, yPosition-1))
       {
           attackingSquares[0][position] = xPosition+direction;
           attackingSquares[1][position] = yPosition-1;
           position++;
       }
       if (noPiece(board, xPosition+direction, yPosition+1) || isPiece(board, xPosition+direction, yPosition+1))
       {
           attackingSquares[0][position] = xPosition+direction;
           attackingSquares[1][position] = yPosition+1;
           position++;
       }
       for (int i = position; i<attackingSquares[0].length; i++)
       {
        attackingSquares[0][i] = 10;
       }
   }
}