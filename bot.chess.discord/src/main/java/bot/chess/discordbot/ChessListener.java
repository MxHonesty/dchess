package bot.chess.discordbot;

import com.github.bhlangonijr.chesslib.Board;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChessListener extends ListenerAdapter{
	
	User adversar1, adversar2;
	MessageChannel channel;
	int turn = 1, sub = 1;
	int terminat = 0;
	String lastmove;
	int acceptat = 0;
	
	Board board = new Board();
	
	public ChessListener(MessageChannel channel, User adversar1, User adversar2) {	//initializarea obiectului cu valorile
        this.adversar1 = adversar1;
        this.adversar2 = adversar2;
        this.channel = channel;
        System.out.println("CHESSLISTENER INIT");
        //System.out.println(board.toString());
        }
	
	 
	 @Override
		public void onMessageReceived(MessageReceivedEvent event) {
		 
		if(acceptat == 0) {
			if(event.getChannel() == channel) {											//daca comanda este trimisa pe acelasi canal
				if(event.getAuthor() == adversar2) {									//daca raspunsul este dat de jucatorul provocat
					if(event.getMessage().getContentDisplay().startsWith("!accept")) {	//daca comanda de acceptare este trimisa		
						event.getChannel().sendMessage("Un meci a inceput intre " + adversar1.getName() + " si " + adversar2.getName()).queue();
						acceptat = 1;
						event.getChannel().sendMessage(board.toString()).queue();
					}
				}
			}
		}
		
		else if(acceptat == 1) {	//ascultare mutari in mod succesiv
			if(sub == 1) {
				if(event.getMessage().getContentDisplay().startsWith("!m ") && event.getAuthor() == adversar1) {
					//valideaza mutarea
					//executa mutarea
					System.out.println("A MERS");
					sub=2;
				}
				else if(event.getMessage().getContentDisplay().startsWith("!stop") && event.getAuthor()==adversar1) {
					//capitulare jucator 1
				}
			}
			
			else if(sub == 2) {
				if(event.getMessage().getContentDisplay().startsWith("!m ") && event.getAuthor() == adversar2) {
					//valideaza mutarea
					//executa mutarea
					sub=1;
					turn++;
			}
				else if(event.getMessage().getContentDisplay().startsWith("!stop") && event.getAuthor()==adversar1) {
				//capitulare jucator 2
				}
			}
		}
	}
	 
	 
	public void onReady(ReadyEvent event){
		System.out.println("\n\n-------------------Bot Debug-------------------\n\n");
	}
	     
	 
	 

}
