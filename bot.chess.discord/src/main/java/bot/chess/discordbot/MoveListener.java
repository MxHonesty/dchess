package bot.chess.discordbot;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MoveListener extends ListenerAdapter{

	ChessListener parent;
	User dela;
	
	public MoveListener(ChessListener parent, User dela) {
		this.parent = parent;
		this.dela = dela;	// de la cine asteapta mesajul
	}
	
	@Override
    public void onMessageReceived(MessageReceivedEvent event){
		
		if (event.getAuthor().isBot()) return; // nu raspunde altor boti
        if (event.getChannel().getIdLong() != parent.channel.getIdLong()) return; // nu raspunde pe alte canale
 
        if(event.getAuthor() == dela) {		// mesajul de la jucatorul cautat
        	
        	if(event.getMessage().getContentDisplay().startsWith("!m")) {	// mesajul care incepe cu prefixul pentru mutare
        		parent.lastmove = event.getMessage().getContentDisplay().substring(3);
				parent.lastmove.notify();
        		event.getJDA().removeEventListener(this);
        	}
        	
        	else if(event.getMessage().getContentDisplay() == "!stop") {
        		parent.lastmove = "stop";
        		parent.lastmove.notify();
        		event.getJDA().removeEventListener(this);
        	}
        }
	}
	
}
