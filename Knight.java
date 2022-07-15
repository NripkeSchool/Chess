
/**
 * Class describing a knight piece on a chess board
 * 
 * w = White
 * b = Black
 * 
 * n = Knight
 */

public class Knight extends Piece
{
   
   /**
   * Constructor method
   **/
    
   public Knight(int newX, int newY, String color)
   {
     super(newX, newY, color, "n", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/Chess_ndt45.svg/1024px-Chess_ndt45.svg.png", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Chess_nlt45.svg/1024px-Chess_nlt45.svg.png");
   }
   
   public boolean isLegalMove(int newX, int newY, ChessBoard board)
   {
       boolean result = false;
       
      if (noPiece(board, newX, newY) || isPiece(board, newX, newY))
      {
       if ((newX == xPosition+2) && (newY == yPosition+1))
       {
         result = true;  
       }else if ((newX == xPosition+2) && (newY == yPosition-1))
       {
         result = true; 
       }else if ((newX == xPosition-2) && (newY == yPosition+1))
       {
         result = true; 
       }else if((newX == xPosition-2) && (newY == yPosition-1))
       {
         result = true;   
       }else if((newX == xPosition+1) && (newY == yPosition-2))
       {
         result = true; 
       }else if((newX == xPosition-1) && (newY == yPosition-2))
       {
         result = true;  
       }else if ((newX == xPosition+1) && (newY == yPosition+2))
       {
         result = true; 
       }else if ((newX == xPosition-1) && (newY == yPosition+2))
       {
         result = true; 
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
     int[][] moves = new int[2][8];  //8 Legal Knight Moves
     int position = 0;
    
     if (isLegalMove(xPosition+2, yPosition+1, board))
     {
        moves[0][position] = xPosition+2;
        moves[1][position] = yPosition+1;
        position++;
     }
     if (isLegalMove(xPosition+2, yPosition-1, board))
     {
       moves[0][position] = xPosition+2;
       moves[1][position] = yPosition-1; 
       position++;
     }
     if (isLegalMove(xPosition-2, yPosition+1, board))
     {
       moves[0][position] = xPosition-2;
       moves[1][position] = yPosition+1;  
       position++;
     }
     if (isLegalMove(xPosition-2, yPosition-1, board))
     {
        moves[0][position] = xPosition-2;
        moves[1][position] = yPosition-1;
        position++;
     }
     if (isLegalMove(xPosition+1, yPosition+2, board))
     {
        moves[0][position] = xPosition+1;
        moves[1][position] = yPosition+2;
        position++;
     }
     if (isLegalMove(xPosition-1, yPosition+2, board))
     {
        moves[0][position] = xPosition-1;
        moves[1][position] = yPosition+2;
        position++;
     }
     if (isLegalMove(xPosition+1, yPosition-2, board))
     {
        moves[0][position] = xPosition+1;
        moves[1][position] = yPosition-2;
        position++;
     }
     if (isLegalMove(xPosition-1, yPosition-2, board))
     {
        moves[0][position] = xPosition-1;
        moves[1][position] = yPosition-2;
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
     if(noPiece(board, xPosition+2, yPosition+1) || isPiece(board, xPosition+2, yPosition+1))
     {
        attackingSquares[0][position] = xPosition+2;
        attackingSquares[1][position] = yPosition+1;
        position++;
     }
     if (noPiece(board, xPosition+2, yPosition-1) || isPiece(board, xPosition+2, yPosition-1))
     {
        attackingSquares[0][position] = xPosition+2;
        attackingSquares[1][position] = yPosition-1;
        position++;
     }
     if (noPiece(board, xPosition-2, yPosition+1) || isPiece(board, xPosition-2, yPosition+1))
     {
        attackingSquares[0][position] = xPosition-2;
        attackingSquares[1][position] = yPosition+1;
        position++;
     }
     if (noPiece(board, xPosition-2, yPosition-1) || isPiece(board, xPosition-2, yPosition-1))
     {
        attackingSquares[0][position] = xPosition-2;
        attackingSquares[1][position] = yPosition-1;
        position++;
     }
     if (noPiece(board, xPosition+1, yPosition+2) || isPiece(board, xPosition+1, yPosition+2))
     {
        attackingSquares[0][position] = xPosition+1;
        attackingSquares[1][position] = yPosition+2;
        position++;
     }
     if (noPiece(board, xPosition-1, yPosition+2) || isPiece(board, xPosition-1, yPosition+2))
     {
        attackingSquares[0][position] = xPosition-1;
        attackingSquares[1][position] = yPosition+2;
        position++;
     }
     if (noPiece(board, xPosition+1, yPosition-2) || isPiece(board, xPosition+1, yPosition-2))
     {
        attackingSquares[0][position] = xPosition+1;
        attackingSquares[1][position] = yPosition-2;
        position++;
     }
     if (noPiece(board, xPosition-1, yPosition-2) || isPiece(board, xPosition-1, yPosition-2))
     {
        attackingSquares[0][position] = xPosition-1;
        attackingSquares[1][position] = yPosition-2;
        position++;
     }  
     for (int i = position; i<attackingSquares[0].length; i++)
     {
       attackingSquares[0][i] = 10;
     }
   }
}