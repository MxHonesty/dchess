package bot.chess.discordbot;

import java.util.ArrayList;
import java.util.List;

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

	public List<MessageChannel> canale = new ArrayList<MessageChannel>();

    public static void main(String[] args)
            throws LoginException{		//initializare
    	
        JDA jda = new JDABuilder("TOKEN").build();

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
				Joc(event.getMessage().getAuthor(),event.getMessage().getMentionedUsers().get(0),event); // incepe jocul si extrage cei 2 playeri
			}
		}
	}


	public void Joc(User player1, User player2, MessageReceivedEvent event)
	{
		if(canale.contains(event.getChannel())) {

		}
		else {
		System.out.println("FUNCTIA JOC");
		canale.add(event.getChannel());
		event.getJDA().addEventListener(new ChessListener(event.getChannel(), player1, player2, this));
		System.out.println("DUPA INIT");
		}


	}



}
