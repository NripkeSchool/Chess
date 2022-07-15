/**
 * Class describing a king piece on a chess board
 * 
 * w = White
 * b = Black
 * 
 * k = King
 */

public class King extends Piece
{
   
   /**
   * Constructor method
   **/
   
   public King(int newX, int newY, String color)
   {
     super(newX, newY, color, "k", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f0/Chess_kdt45.svg/1024px-Chess_kdt45.svg.png", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Chess_klt45.svg/1024px-Chess_klt45.svg.png");
   }
   
   public boolean isLegalMove(int newX, int newY, ChessBoard board)
   {
      boolean result = false;
      
      if (noPiece(board, newX, newY) || isPiece(board, newX, newY)) 
      {
       if ((newX == xPosition+1) && (newY == yPosition))
       {
           result = true; 
       }else if ((newX == xPosition-1) && (newY == yPosition))
       {
           result = true; 
       }else if ((newX == xPosition) && (newY == yPosition+1))
       {
           result = true; 
       }else if((newX == xPosition) && (newY == yPosition-1))
       {
           result = true; 
       }else if((newX == xPosition+1) && (newY == yPosition+1))
       {
           result = true; 
       }else if((newX == xPosition+1) && (newY == yPosition-1))
       {
           result = true; 
       }else if ((newX == xPosition-1) && (newY == yPosition-1))
       {
           result = true; 
       }else if ((newX == xPosition-1) && (newY == yPosition+1))
       {
           result = true; 
       }
       Piece temp = board.getBoard()[newX][newY];
     
       board.getBoard()[newX][newY] = board.getBoard()[xPosition][yPosition];
       board.getBoard()[xPosition][yPosition] = null;
       board.updateAttackingMoves(board.getBoard());
       
       int oldX = xPosition;
       int oldY = yPosition;
       
       xPosition = newX;
       yPosition = newY;
       
       if (board.isInCheck(pieceColor, board.getBoard()))
       {
          result = false;
       }
       
       xPosition = oldX;
       yPosition = oldY;
       
       board.getBoard()[xPosition][yPosition] = board.getBoard()[newX][newY];
       board.getBoard()[newX][newY] = temp;
       board.updateAttackingMoves(board.getBoard());
      }else {
        if (newX < 8 && newX > -1 && newY < 8 && newY > -1 && moves == 0 && board.getBoard()[newX][newY].getMoves() == 0)
        {
            if (newX == xPosition && newY == 0)
            {
             if (board.getBoard()[newX][1] == null && board.getBoard()[newX][2] == null && board.getBoard()[newX][3] == null)
             {
               for (int i = newY+1; i <= yPosition; i++)
               {
                 if (board.squareInCheck(newX, i, oppositeColor, board.getBoard()))  
                 {
                  return false;   
                 }
               }
               result = true;
             }
            }else if (newX == xPosition && newY == 7)
              {
               if (board.getBoard()[newX][5] == null && board.getBoard()[newX][6] == null)
               {
                 for (int i = newY-1; i >= yPosition; i--)
                 {
                  if (board.squareInCheck(newX, i, oppositeColor, board.getBoard()))  
                  {
                   return false;   
                  }   
                 }
                 result = true;
               }  
            }

        }
      }
      return result;
   }
   
   public int[][] allLegalMoves(ChessBoard board)
   {
     int[][] moves = new int[2][10];
     int position = 0;
  
     //Three squares above
     for (int i = -1; i<=1; i++)
     {
      if (isLegalMove(xPosition-1, yPosition+i, board))
      {
        moves[0][position] = xPosition-1;
        moves[1][position] = yPosition+i;
        position++;
      }
     }
  
     //Three squares below
     for (int i = -1; i<=1; i++)
     {
      if (isLegalMove(xPosition+1, yPosition+i, board))
      {
        moves[0][position] = xPosition+1;
        moves[1][position] = yPosition+i;
        position++;
      }
     }
     
     //Two squares on each side
     for (int i = -1; i<=1; i += 2)
     {
      if (isLegalMove(xPosition, yPosition+i, board))
      {
        moves[0][position] = xPosition;
        moves[1][position] = yPosition+i;
        position++;
      }
     }
  
     if (isLegalMove(xPosition, 7, board))
     {
       moves[0][position] = xPosition;
       moves[1][position] = 7;
       position++;  
     }
     
     if (isLegalMove(xPosition, 0, board))
     {
       moves[0][position] = xPosition;
       moves[1][position] = 0;
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
      
      //3 Moves above it
      for (int square = -1; square<=1; square++)
      {
        if (noPiece(board, xPosition-1, yPosition+square) || isPiece(board, xPosition-1, yPosition+square))
        {
           attackingSquares[0][position] = xPosition-1;  
           attackingSquares[1][position] = yPosition+square; 
           position++;
        }  
      }
      
     //3 Moves below it
     for (int square = -1; square<=1; square++)
      {
        if (noPiece(board, xPosition+1, yPosition+square) || isPiece(board, xPosition+1, yPosition+square))
        {
           attackingSquares[0][position] = xPosition+1;  
           attackingSquares[1][position] = yPosition+square; 
           position++;
        }  
     }
     
     //2 Moves on each side
     for (int square = -1; square<=1; square += 2)
      {
        if (noPiece(board, xPosition, yPosition+square) || isPiece(board, xPosition, yPosition+square))
        {
           attackingSquares[0][position] = xPosition;  
           attackingSquares[1][position] = yPosition+square; 
           position++;
        }  
     }
     
     for (int i = position; i<attackingSquares[0].length; i++)
     {
       attackingSquares[0][i] = 10;
     }
   }
}