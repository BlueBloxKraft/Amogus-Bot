package bot.amogus;

import java.awt.Color;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import javax.security.auth.login.LoginException;

import bot.amogus.listeners.DM;
import bot.amogus.listeners.Invite;
import bot.amogus.listeners.Ping;
import bot.amogus.listeners.RickRoll;
import bot.amogus.listeners.Shutdown;
import bot.amogus.listeners.Sus;
import bot.amogus.listeners.YTStats;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Amogus extends ListenerAdapter {

	public static String TOKEN = "bot token goes here";
	public static JDA amogus;
	
	public static void main(String[] args) throws LoginException {
		amogus = JDABuilder.createDefault(TOKEN)
						   .addEventListeners(
								   	new Amogus(),
								   	new Sus(),
								   	new Invite(),
								   	new DM(),
								   	new bot.amogus.listeners.Math(),
						  			new Ping(),
						  			new RickRoll(),
						  			new Shutdown(),
						  			new YTStats()
								)
						   .build();
		
		amogus.getPresence().setActivity(Activity.playing("Among Us"));
		amogus.getPresence().setStatus(OnlineStatus.IDLE);
		
		amogus.updateCommands().addCommands(
							Commands.slash("invite", "The invite link for Amogus Bot"),
							
							Commands.slash("message", "Direct Messages a user")
									.addOption(OptionType.USER, "user", "The user to DM", true)
									.addOption(OptionType.STRING, "message", "The message to send", true),
									
							Commands.slash("math", "Does Math stuff =D WARNING: Dont use this to cheat during exam")
									.addOption(OptionType.STRING, "equation", "Insert an equation", true),
									
							Commands.slash("ping", "Says the ping of the bot"),
							
							Commands.slash("rickroll", "Rick rolls someone with a fake Nitro link LMAOOOO")
									.addOption(OptionType.USER, "user", "The user to Rick Roll", true),
									
							Commands.slash("shutdown", "Shuts down the bot if you are the bot's owner"),
							
							Commands.slash("ytstats", "Get the stats of a YouTube channel")
									.addOption(OptionType.BOOLEAN, "use_username", "If true this will use the username if the channel has a custom link", true)
									.addOption(OptionType.STRING, "channel", "Enter channel ID if 'use_username' is false, if it isn't, use the custom link", true)
				).queue();
		
	}
	
	@Override
	public void onReady(ReadyEvent event) {
		System.out.println("the bot is ready");
	}
	
	@Override
	public void onShutdown(ShutdownEvent event) {
		amogus.getPresence().setStatus(OnlineStatus.INVISIBLE);
	}
	
	/**
	 * this generates a random color using java's 'Random' class.
	 * 
	 * it subtracts 1 from the random number cuz it the random class chooses
	 * a number between 0 and the given number, color values can range from 0
	 * to 255 so if the random number is 256 it subtracts 1 from it so it'll change to 255
	 * and if it is 1 it subtracts 1 from it so it'll become 0
	 * 
	 * @return random color
	 */
	public static Color randomColor() {
		return new Color(new Random().nextInt(256) - 1, new Random().nextInt(256) - 1, new Random().nextInt(256) - 1);
	}
	
	/**
	 * a scuffed way of checking if a string contains a word while ignoring case
	 *
	 * @param firstString
	 * @param secondString
	 */
	public static boolean containsIgnoreCase(String firstString, String secondString) {
		return firstString.toLowerCase().contains(secondString.toLowerCase());
	}
	
	/**
	 * a boolean which returns true if the given user's id 
	 * is the same as the owner's id
	 * 
	 * @param user
	 * @return true if user is owner
	 */
	public static boolean isOwner(User user) {
		//change this to ur id
		return user.getId().equals("949918075359227924");
	}
	
	/**
	 * returns a exception stack trace as string
	 * 
	 * @param e
	 * @return exception as string
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	
	/**
	 * logs the slash commands errors to a channel
	 * 
	 * @param e
	 * @param authorText
	 * @param event
	 */
	public static void logSlashCommandErrorsToErrorLogChannel(Exception e, String authorText, SlashCommandInteractionEvent event) {
		EmbedBuilder errEmbed = new EmbedBuilder();
		errEmbed.setAuthor(authorText)
				.setDescription(Amogus.getStackTraceAsString(e))
				.setColor(Color.RED)
				.setFooter("Executed by " + event.getUser().getAsTag(), event.getUser().getAvatarUrl());
		
		amogus.getTextChannelById(965132804537090048L).sendMessage("An error happened!")
														.setEmbeds(errEmbed.build()).queue();
	}
	
}
