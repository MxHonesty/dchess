package bot.chess.discordbot;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

public class DiscordLogic extends ListenerAdapter{

	public static String prefix = "%";

	public List<MessageChannel> canale = new ArrayList<MessageChannel>();

    public static void main(String[] args)
            throws LoginException{		//initializare
    	
        //JDA jda = new JDABuilder("NzU0MDAyNTExNDI1NDM3ODA3.X1uZjw.eaAISHOqUtG6pXuuBiY_KjJh_0Y").build();
       
        // Playing status
        //jda.getPresence().setActivity(Activity.playing("chess | "+ DiscordLogic.prefix + "help"));

        //jda.addEventListener(new DiscordLogic());
        
        new DefaultShardManagerBuilder()
        	.setToken("NzM5OTYwNjI4NTU4NTYxMzM0.XyiECQ.NPeZD1N26N52gOmW4G-jdspAHcc")
	        .setShardsTotal(2)
	        .setActivity(Activity.playing("chess | "+ DiscordLogic.prefix + "help"))
	        .addEventListeners(new DiscordLogic())
	        .build();

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
		else if(args[0].equalsIgnoreCase(DiscordLogic.prefix + "help")){
			Help_Embed(event);
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
	
	public void Help_Embed(MessageReceivedEvent event)
	{
		EmbedBuilder table = new EmbedBuilder(); //Builderul de embed
		table.setColor(0x0576ff);
		
		table.setTitle("DChess Help", null);
		table.setAuthor("DChess Bot", null, "https://images-platform.99static.com//CkCMRlE5Gvmkr65u_pZ1hXLFzyM=/100x100:900x900/fit-in/500x500/99designs-contests-attachments/116/116527/attachment_116527796");
		
		table.addField("Sending a game request", DiscordLogic.prefix + "chess <Mention Other Player>", false);
		
		table.addField("Accepting a game request", DiscordLogic.prefix + "accept", false);
		
		table.addField("Executing a move", DiscordLogic.prefix + "m <starting square><destination square> (" + DiscordLogic.prefix + 
				"m e2e4)", false);
		
		table.addField("Surrender", DiscordLogic.prefix + "stop", false);
		
		
		event.getChannel().sendMessage(table.build()).queue(); //construim tabla
		
	}



}
