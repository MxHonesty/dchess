package bot.chess.discordbot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
	
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
	
	public void Embedd_Constructor(MessageReceivedEvent event)
	{
		EmbedBuilder table = new EmbedBuilder(); //Builderul de embed
		table.setColor(0x0576ff);
		table.setImage("https://picsum.photos/800/600"); //placeholder pt imaginea cu tabla
		
		event.getChannel().sendTyping().queue();
		event.getChannel().sendMessage(table.build()).queue(); //construim tabla
		
		table.clear(); //stergem tabla pentru conservarea resurselor
	}
	
	
	public void Joc(User player1, User player2, MessageChannel canal)
	{
		System.out.println("FUNCTIA JOC");
		canal.getJDA().addEventListener(new ChessListener(canal, player1, player2));
		System.out.println("DUPA INIT");
		player1.getJDA().addEventListener(new ChessListener(canal, player1, player2));
		System.out.println("DUPA INIT");
		
		
	}
	
}
