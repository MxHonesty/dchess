package bot.chess.discordbot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordLogic extends ListenerAdapter{
	
	public static String prefix = "!";
	
    public static void main(String[] args)
            throws LoginException{
    	
        JDA jda = new JDABuilder("NzM1ODUzNDE4MDI3MDI0NDE0.XxmS5g.9GJu1rZfgy155zGcDSCP5b6bKgI").build();
        
        jda.addEventListener(new DiscordLogic());
        jda.addEventListener(new Commands());

        //jda.getPresence().setActivity(Activity.playing("Chess"));
        
    }
    @Override
    public void onReady(ReadyEvent event){
    	System.out.println("\n\n-------------------Bot Debug-------------------\n\n");
    }
    
  
}
