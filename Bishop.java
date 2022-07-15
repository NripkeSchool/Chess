/**
 * Class describing a bishop piece on a chess board
 * 
 * w = White
 * b = Black
 * 
 * b = Bishop
 */

public class Bishop extends Piece
{
   
   /**
   * Constructor method
   **/
    
   public Bishop(int newX, int newY, String color)
   {
     super(newX, newY, color, "b", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/Chess_bdt45.svg/1024px-Chess_bdt45.svg.png", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b1/Chess_blt45.svg/1024px-Chess_blt45.svg.png");
   }
   
   public boolean isLegalMove(int newX, int newY, ChessBoard board)
   {
       boolean result = false;
       
       //if newX is less than oldX and newY is greater than oldY and newX+newY = oldX+oldY
       //if newX is greater than oldX and newY is less than oldY and newX+newY = oldX+oldY
       //if newX is greater than oldX and newY is greater than oldY and newX-oldX = newY-oldY
       //if newX is less than oldX and newY is less than oldY and oldX-newX = oldY-newY
       
       if (noPiece(board, newX, newY) || isPiece(board, newX, newY))
       {
        if (newX < xPosition && newY > yPosition && (newX+newY == xPosition+yPosition))
        {
          for (int square = 1; square<(xPosition-newX); square++)
          {
              if (!noPiece(board, xPosition-square, yPosition+square))
              {
                  return false;
              }
          }
          result = true; 
        }else if (newX > xPosition && newY < yPosition && (newX+newY == xPosition+yPosition))
        {
          for (int square = 1; square<(newX-xPosition); square++)
          {
              if (!noPiece(board, xPosition+square, yPosition-square))
              {
                  return false;
              }
          }
          result = true; 
        }else if (newX > xPosition && newY > yPosition && (newX-xPosition == newY-yPosition))
        {
          for (int square = 1; square<(newX-xPosition); square++)
          {
              if (!noPiece(board, xPosition+square, yPosition+square))
              {
                  return false;
              }
          }
          result = true;
        }else if (newX < xPosition && newY < yPosition && (xPosition-newX == yPosition-newY))
        {
          for (int square = 1; square<(xPosition-newX); square++)
          {
              if (!noPiece(board, xPosition-square, yPosition-square))
              {
                  return false;
              }
          }
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
     int[][] moves = new int[2][13];
     int position = 0;
     
     for (int square = 1; square <= (7-yPosition); square++)
     {
       if (isLegalMove(xPosition-square, yPosition+square, board))
       {
         moves[0][position] = xPosition-square;  
         moves[1][position] = yPosition+square; 
         position++;
       }else {
         square = 8; 
       }  
     }
     
     for (int square = 1; square <= (7-yPosition); square++)
     {
       if (isLegalMove(xPosition+square, yPosition+square, board))
       {
         moves[0][position] = xPosition+square;  
         moves[1][position] = yPosition+square; 
         position++;
       }else {
         square = 8; 
       }  
     }
     
     for (int square = 1; square <= yPosition; square++)
     {
       if (isLegalMove(xPosition+square, yPosition-square, board))
       {
         moves[0][position] = xPosition+square;  
         moves[1][position] = yPosition-square; 
         position++;
       }else {
         square = 8; 
       }  
     }
     
     for (int square = 1; square <= yPosition; square++)
     {
       if (isLegalMove(xPosition-square, yPosition-square, board))
       {
         moves[0][position] = xPosition-square;  
         moves[1][position] = yPosition-square; 
         position++;
       }else {
         square = 8; 
       }  
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
     
     //North-East
     for (int square = 1; square <= (7-yPosition); square++)
     {
       if (noPiece(board, xPosition-square, yPosition+square))
       {
         attackingSquares[0][position] = xPosition-square;  
         attackingSquares[1][position] = yPosition+square; 
         position++;
       }else if(isPiece(board, xPosition-square, yPosition+square))
       {
         attackingSquares[0][position] = xPosition-square;  
         attackingSquares[1][position] = yPosition+square;
         position++;
         square = 8;
       }else {
         square = 8; 
       }  
     }
     
     //South-East
     for (int square = 1; square <= (7-yPosition); square++)
     {
       if (noPiece(board, xPosition+square, yPosition+square))
       {
         attackingSquares[0][position] = xPosition+square;  
         attackingSquares[1][position] = yPosition+square; 
         position++;
       }else if(isPiece(board, xPosition+square, yPosition+square))
       {
         attackingSquares[0][position] = xPosition+square;  
         attackingSquares[1][position] = yPosition+square;
         position++;
         square = 8;
       }else {
         square = 8; 
       }  
     }
     
     //South-West
     for (int square = 1; square <= yPosition; square++)
     {
       if (noPiece(board, xPosition+square, yPosition-square))
       {
         attackingSquares[0][position] = xPosition+square;  
         attackingSquares[1][position] = yPosition-square; 
         position++;
       }else if(isPiece(board, xPosition+square, yPosition-square))
       {
         attackingSquares[0][position] = xPosition+square;  
         attackingSquares[1][position] = yPosition-square;
         position++;
         square = 8;
       }else {
         square = 8; 
       }  
     }
     
     //North-West
     for (int square = 1; square <= yPosition; square++)
     {
       if (noPiece(board, xPosition-square, yPosition-square))
       {
         attackingSquares[0][position] = xPosition-square;  
         attackingSquares[1][position] = yPosition-square; 
         position++;
       }else if(isPiece(board, xPosition-square, yPosition-square))
       {
         attackingSquares[0][position] = xPosition-square;  
         attackingSquares[1][position] = yPosition-square;
         position++;
         square = 8;
       }else {
         square = 8; 
       }  
     }
     
     for (int i = position; i<attackingSquares[0].length; i++)
     {
       attackingSquares[0][i] = 10;
     }
   }
}