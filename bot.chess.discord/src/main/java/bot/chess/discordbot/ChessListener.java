package bot.chess.discordbot;

import java.io.File;
import java.io.IOException;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.github.bhlangonijr.chesslib.move.MoveList;

import bot.chess.discord.imageprocessing.Image;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChessListener extends ListenerAdapter{
	
	User adversar1, adversar2;
	MessageChannel channel;
	int turn = 1, sub = 1;
	int terminat = 0;
	String lastmove;
	int acceptat = 0;
	DiscordLogic d = new DiscordLogic();
	Image img = new Image();
	
	Board board = new Board();
	
	public ChessListener(MessageChannel channel, User adversar1, User adversar2, DiscordLogic d) {	//initializarea obiectului cu valorile
        this.adversar1 = adversar1;
        this.adversar2 = adversar2;
        this.channel = channel;
        this.d = d;
        System.out.println("CHESSLISTENER INIT");
        //System.out.println(board.toString());
        }
	
	 
	 @Override
		public void onMessageReceived(MessageReceivedEvent event) {
		 
		if(event.getChannel() == channel) {
			if(acceptat == 0) {
				if(event.getAuthor() == adversar2) {									//daca raspunsul este dat de jucatorul provocat
					if(event.getMessage().getContentDisplay().startsWith("!accept")) {	//daca comanda de acceptare este trimisa		
						event.getChannel().sendMessage("Un meci a inceput intre " + adversar1.getName() + " si " + adversar2.getName()).queue();
						acceptat = 1;
						trimitereimagine();
					}
				}
			}
			else if (acceptat == 1) {
				
				if(sub == 1) {
					
					if(event.getMessage().getContentDisplay().startsWith("!m ") && event.getAuthor() == adversar1) {
						if(validaremutare(generaremutare(event.getMessage().getContentDisplay().substring(3), board), board) == true) {	//daca mutarea data este valida
							board.doMove(generaremutare(event.getMessage().getContentDisplay().substring(3), board));						//executa mutarea
							System.out.println("Mutare executata " + event.getMessage().getContentDisplay().substring(3));			//print mutare debug
							trimitereimagine();																				//trimite imaginea
							verificari(event, adversar1);
							sub=2;
						} else {
							channel.sendMessage("Mutare Invalida").queue();
						}
						
					}
					else if(event.getMessage().getContentDisplay().startsWith("!stop") && event.getAuthor()==adversar1) {
						channel.sendMessage(adversar1.getName() + " a renuntat!").queue();
						stopjoc(event.getJDA()); // stop listening
					}
				}
				
				else if(sub == 2) {
					if(event.getMessage().getContentDisplay().startsWith("!m ") && event.getAuthor() == adversar2) {

						if(validaremutare(generaremutare(event.getMessage().getContentDisplay().substring(3), board), board) == true) {	//daca mutarea data este valida
							board.doMove(generaremutare(event.getMessage().getContentDisplay().substring(3), board));						//executa mutarea
							System.out.println("Mutare executata " + event.getMessage().getContentDisplay().substring(3));			//print mutare debug
							trimitereimagine();	
							verificari(event, adversar2);
							sub=1;
							turn++;
						} else {
							channel.sendMessage("Mutare Invalida").queue();
						}

				}
					else if(event.getMessage().getContentDisplay().startsWith("!stop") && event.getAuthor()==adversar2) {
						channel.sendMessage(adversar2.getName() + " a renuntat!").queue();
						stopjoc(event.getJDA()); // stop listening
					}
				}
				
				
				
			}
		}
		 
	}
	 
	
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
	
	public boolean validaremutare(Move m, Board b){
		 MoveList moves = null;
		 try {
			moves = MoveGenerator.generateLegalMoves(b);
		} catch (MoveGeneratorException e) {
			// TODO Auto-generated catch block
			System.out.println("VALIDAREMUTARE FAIL");
		}
	        if(moves.contains(m)) {
	        	return true;
	        }
	        else return false;
	}
	
	public void Embedd_Constructor(MessageReceivedEvent event)
	{
		EmbedBuilder table = new EmbedBuilder(); //Builderul de embed
		table.setColor(0x0576ff);
		table.setImage("src/main/resources/img" + event.getChannel().getId()); //placeholder pt imaginea cu tabla
		
		event.getChannel().sendMessage(table.build()).queue(); //construim tabla
		
		table.clear(); //stergem tabla pentru conservarea resurselor
	}
	
	     
    public static char[][] matrice(String string){	//conversia din string[] in char[][]
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
    
    public void trimitereimagine() {
    	
    	
		img.updateTable(matrice(board.toString()));					//update matrice
		
		
		//img.salvare(img.renderTabel(), channel.getId());			//salvare imagine
		//channel.sendFile(new File("src/main/resources/img" + channel.getId() + ".png")).queue();	//trimitere fisier pe discord
		
		
		try {
			channel.sendFile(img.BufferedtoArray(img.renderTabel()), "image.png").queue();	//trimite byte[] generat din BufferedImage
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void verificari(MessageReceivedEvent event, User jucator) {
		if(board.isDraw()) {						//conditia remiza
			channel.sendMessage("REMIZA").queue();
			stopjoc(event.getJDA());
			
		} else if(board.isMated()) {				//conditia mat

			//img.salvare(img.presentWinner( jucator.getName() ), channel.getId());
			//salvare imagine
			//channel.sendFile(new File("src/main/resources/img" + channel.getId() + ".png")).queue();	//trimitere fisier pe discord
			
			channel.sendMessage("SAH MAT " + jucator.getAsMention() + " este castigator!").queue();
			stopjoc(event.getJDA());
		} else if(board.isKingAttacked()) {			//conditia sah
			channel.sendMessage("Sah").queue();
		}
    }
	 
    public void stopjoc(JDA jda) {
    	d.canale.remove(channel);		//elimina canalul din lista
    	jda.removeEventListener(this);	//elimina acest listener
    	File f = new File("src/main/resources/img" + channel.getId() + ".png");
    	f.delete();						//sterge fisierul
    }

}
