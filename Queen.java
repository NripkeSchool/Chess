/**
 * Class describing a queen piece on a chess board
 * 
 * w = White
 * b = Black
 * 
 * q = Queen
 * 
 * Something to keep in mind is the fact that the queen is a combination of the rook and the bishop
 * This makes it significantly easier to code as we can simply combine the rook code with the bishop code
 */

public class Queen extends Piece
{
   
   /**
   * Constructor method
   **/
    
   public Queen(int newX, int newY, String color)
   {
     super(newX, newY, color, "q", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Chess_qdt45.svg/1024px-Chess_qdt45.svg.png", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/Chess_qlt45.svg/1024px-Chess_qlt45.svg.png");
   }
   
   public boolean isLegalMove(int newX, int newY, ChessBoard board)
   {
       boolean result = false;
       
       Rook rookTest = new Rook(xPosition, yPosition, pieceColor); //Rook part of the queen
       Bishop bishopTest = new Bishop(xPosition, yPosition, pieceColor); //Bishop part of the queen
       
       if(noPiece(board, newX, newY) || isPiece(board, newX, newY))
       {
        if (rookTest.isLegalMove(newX, newY, board))
        {
            result = true;
        }else if (bishopTest.isLegalMove(newX, newY, board))
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
     int[][] moves = new int[2][27];
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
       for (int square = 1; square <= (7-xPosition); square++)
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
       for (int square = 1; square <= (7-xPosition); square++)
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
       for (int square = 1; square <= (7-xPosition); square++)
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
     
     //Rook moves
     
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
       
     //Bishop Moves  
     
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