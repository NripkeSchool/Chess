/**
 * Class describing a rook piece on a chess board
 * 
 * w = White
 * b = Black
 * 
 * r = Rook
 */

public class Rook extends Piece
{
   
   /**
   * Constructor method
   **/
    
   public Rook(int newX, int newY, String color)
   {
     super(newX, newY, color, "r", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Chess_rdt45.svg/1024px-Chess_rdt45.svg.png", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/72/Chess_rlt45.svg/1024px-Chess_rlt45.svg.png");
   }
   
   public boolean isLegalMove(int newX, int newY, ChessBoard board)
   {
      boolean result = false;
       
      
       if (noPiece(board, newX, newY) || isPiece(board, newX, newY))
       {
          if (newX > xPosition && newY == yPosition) //Rook went down
          {
            for (int square = 1; square<(newX-xPosition); square++)
            {
              if (!noPiece(board, xPosition+square, yPosition))
              {
                  return false;
              }
            }
            result = true;   
          }else if (newX < xPosition && newY == yPosition) //Rook went up
          {
            for (int square = 1; square<(xPosition-newX); square++)
            {
              if (!noPiece(board, xPosition-square, yPosition))
              {
                  return false;
              }
            }
            result = true;   
          }else if (newX == xPosition && newY > yPosition) //Rook went right
          {
            for (int square = 1; square<(newY-yPosition); square++)
            {
              if (!noPiece(board, xPosition, yPosition+square))
              {
                  return false;
              }
            }
            result = true;   
          }else if (newX == xPosition && newY < yPosition) //Rook went left
          {
            for (int square = 1; square<(yPosition-newY); square++)
            {
              if (!noPiece(board, xPosition, yPosition-square))
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
       int[][] moves = new int[2][14];
       int position = 0;
       //Downwards
       for (int square = 1; square <= (7-xPosition); square++)
       {
          if (isLegalMove(xPosition+square, yPosition, board))
          {
           moves[0][position] = xPosition+square;  
           moves[1][position] = yPosition;
           position++;
          }else {
           square = 8; 
          }
       }
       
       //Upwards
       for (int square = 1; square <= xPosition; square++)
       {
          if (isLegalMove(xPosition-square, yPosition, board))
          {
           moves[0][position] = xPosition-square;  
           moves[1][position] = yPosition;
           position++;
          }else {
           square = 8; 
          }
       }
       
       //Left
       for (int square = 1; square <= yPosition; square++)
       {
          if (isLegalMove(xPosition, yPosition+square, board))
          {
           moves[0][position] = xPosition;  
           moves[1][position] = yPosition+square;
           position++;
          }else {
           square = 8; 
          }
       }
       
       //Right
       for (int square = 1; square <= (7-yPosition); square++)
       {
          if (isLegalMove(xPosition, yPosition-square, board))
          {
           moves[0][position] = xPosition;  
           moves[1][position] = yPosition-square;
           position++;
          }else {
           square = 8; 
          }
       }
       
       for (int i = position; i<moves.length; i++)
       {
         moves[0][i] = 10;
       }
     
       return moves;
   }

   
   public void updateAttackingSquares(Piece[][] board)
   {
       int position = 0;
       //Downwards
       for (int square = 1; square <= (7-xPosition); square++)
       {
          if (noPiece(board, xPosition+square, yPosition))
          {
           attackingSquares[0][position] = xPosition+square;  
           attackingSquares[1][position] = yPosition;
           position++;
          }else if(isPiece(board, xPosition+square, yPosition))
          {
           attackingSquares[0][position] = xPosition+square;  
           attackingSquares[1][position] = yPosition;
           position++;
           square = 8;
          }else {
           square = 8; 
          }
       }
       
       //Upwards
       for (int square = 1; square <= xPosition; square++)
       {
          if (noPiece(board, xPosition-square, yPosition))
          {
           attackingSquares[0][position] = xPosition-square;  
           attackingSquares[1][position] = yPosition; 
           position++;
          }else if(isPiece(board, xPosition-square, yPosition))
          {
           attackingSquares[0][position] = xPosition-square;  
           attackingSquares[1][position] = yPosition;
           position++;
           square = 8;
          }else {
           square = 8; 
          }
       }
       
       //Left
       for (int square = 1; square <= yPosition; square++)
       {
          if (noPiece(board, xPosition, yPosition-square))
          {
           attackingSquares[0][position] = xPosition;  
           attackingSquares[1][position] = yPosition-square; 
           position++;
          }else if(isPiece(board, xPosition, yPosition-square))
          {
           attackingSquares[0][position] = xPosition;  
           attackingSquares[1][position] = yPosition-square;
           position++;
           square = 8;
          }else {
           square = 8; 
          }
       }
       
       //Right
       for (int square = 1; square <= (7-yPosition); square++)
       {
          if (noPiece(board, xPosition, yPosition+square))
          {
           attackingSquares[0][position] = xPosition;  
           attackingSquares[1][position] = yPosition+square; 
           position++;
          }else if(isPiece(board, xPosition, yPosition+square))
          {
           attackingSquares[0][position] = xPosition;  
           attackingSquares[1][position] = yPosition+square;
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