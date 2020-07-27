package bot.chess.discordbot;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordLogic extends ListenerAdapter{
	
	public static String prefix = "!";
	
    public static void main(String[] args)
            throws LoginException{		//initializare
    	
        JDA jda = new JDABuilder("NzM1ODUzNDE4MDI3MDI0NDE0.XxmS5g.9GJu1rZfgy155zGcDSCP5b6bKgI").build();
        
        jda.addEventListener(new DiscordLogic());
        //jda.addEventListener(new Commands());

        //jda.getPresence().setActivity(Activity.playing("Chess"));
        
    }
    
    
    @Override
    public void onReady(ReadyEvent event){
    	System.out.println("\n\n-------------------Bot Debug-------------------\n\n");
    }
    
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if(args[0].equalsIgnoreCase(DiscordLogic.prefix + "chess")) // daca a scris comanda pe chat
		{
			if(event.getMessage().getMentionedUsers().isEmpty()) //daca userul nu da tag la alt player
			{
				event.getChannel().sendMessage("Please provide a second player").queue();
				
			}
			else
			{
				Joc(event.getMessage().getAuthor(),event.getMessage().getMentionedUsers().get(0),event.getChannel()); // incepe jocul si extrage cei 2 playeri
			}
		}
	}
	
	
	public void Joc(User player1, User player2, MessageChannel canal)
	{
		System.out.println("FUNCTIA JOC");
		canal.getJDA().addEventListener(new ChessListener(canal, player1, player2));
		System.out.println("DUPA INIT");
		
		
		
	}
    
    
  
}
