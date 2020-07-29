package bot.chess.discord.gamelogic;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.github.bhlangonijr.chesslib.move.MoveList;

public class Game {
	
	
	public static Move generaremutare(String caractere, Board board) {	//transforma un string de forma "e2e4" in obiectul Move respectiv
		
		Move m = null;
		
		//System.out.println(inceput+destinatie);
		try {
		String inceput = caractere.substring(0, 1).toUpperCase() + caractere.substring(1, 2);	//Capitalizeza primul caracter
		String destinatie = caractere.substring(2, 3).toUpperCase() + caractere.substring(3);
		m = new Move(Square.fromValue(inceput), Square.fromValue(destinatie));
		
		Square[] s = new Square[1];
		s[0] = Square.fromValue(inceput);
		
		if(Board.isPromoRank(Side.WHITE, m) && board.hasPiece(Piece.WHITE_PAWN, s)) {
			m = new Move(Square.fromValue(inceput), Square.fromValue(destinatie), Piece.WHITE_QUEEN);
		} else if(Board.isPromoRank(Side.BLACK, m) && board.hasPiece(Piece.BLACK_PAWN, s)) {
			m = new Move(Square.fromValue(inceput), Square.fromValue(destinatie), Piece.BLACK_QUEEN);
		}
		
		} catch(Exception e) {
			
		}
		
		return m;
		
	}
	
	
    public static void main(String[] args) throws MoveGeneratorException {
    	
    	// Creates a new chessboard in the standard initial position
        Board board = new Board();
        
        //Make a move from E2 to E4 squares
        //board.doMove(new Move(Square.fromValue("E2"), Square.E4));

	    //print the chessboard in a human-readable form
	    //System.out.println(board.toString());
	    
        MoveList moves = MoveGenerator.generateLegalMoves(board);
        
	    //board.doMove(new Move(Square.fromValue("E2"), Square.E4)); Executa mutarea determinata de inceput
	    // si destinatie indiferent daca este legala sau nu
	    
        Move m = generaremutare("g2g8", board);
        board.doMove(m);
        m = generaremutare("e7e6", board);
        board.doMove(m);
		MoveList moves1 = null;
		moves1 = MoveGenerator.generateLegalMoves(board);
		//m = generaremutare("g7g8");
        //board.doMove(m);
        //System.out.print(moves1);
        System.out.println(board.toString());
        System.out.println(moves1);
        
    }
    
    
    public static char[][] matrice(String string){
    	char[] a = string.toCharArray();
    	char[][] b = new char[8][8];
    	int k = 0;
    	for(int i = 0; i<=7; i++) {
    		for(int j = 0; j<=7; j++) {
    			b[i][j] = a[k];
    			k++;
    		}
    		k+=1;
    	}
    	return b;
    }
    

}
