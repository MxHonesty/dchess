package bot.chess.discord.gamelogic;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.github.bhlangonijr.chesslib.move.MoveList;

public class Game {
	
	
	public static Move generaremutare(String caractere) {	//transforma un string de forma "e2e4" in obiectul Move respectiv
		
		String inceput = caractere.substring(0, 1).toUpperCase() + caractere.substring(1, 2);	//Capitalizeza primul caracter
		String destinatie = caractere.substring(2, 3).toUpperCase() + caractere.substring(3);
		
		//System.out.println(inceput+destinatie);
		
		Move m = new Move(Square.fromValue(inceput), Square.fromValue(destinatie));
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
	    
        Move m = generaremutare("e2e4");
        if(moves.contains(m)) {
        	System.out.println("VALID");
        }
        else System.out.println("INVALID");
        
        System.out.println(board.toString());
       
    }

}
