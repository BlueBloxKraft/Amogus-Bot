package bot.amogus.listeners;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import bot.amogus.Amogus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Math extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if(event.getName().equals("math")) {
			ScriptEngineManager scriptMgr = new ScriptEngineManager();
			ScriptEngine engine = scriptMgr.getEngineByName("js");
			
			try {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setDescription("The answer is: " + engine.eval(event.getOption("equation").getAsString()).toString())
					 .setColor(Amogus.randomColor())
					 .setFooter("By " + event.getUser().getAsTag(), event.getUser().getAvatarUrl());
				event.reply("Here is the answer!")
								  .addEmbeds(embed.build()).queue();
				
			} catch(ScriptException | NullPointerException e) {
				int i = new Random().nextInt(11);
				String err = "The equation you specified isn't valid";
				
				if(event.getChannel().getType() != ChannelType.PRIVATE) {
					if(i != 5) {
						event.reply(err + " :(").setEphemeral(true).queue();
					} else {
						event.reply(err + ", atleast thats what she said XD").setEphemeral(true).queue();
					}
				} else {
					//idk why but it says the app didnt respond while the equation was invalid
					//if the command is executed in DMs, so i changed to send normal messages
					//in DMs instead of ephemeral messages cuz that works. not sure what the problem is
					//i changed it, so that it'll be deleted after 3 secs
					if(i != 5) {
						event.reply(err + " :(").queue(
											message -> message.deleteOriginal().queueAfter(3, TimeUnit.SECONDS));
					} else {
						event.reply(err + ", atleast thats what she said XD").queue(
											message -> message.deleteOriginal().queueAfter(3, TimeUnit.SECONDS));
					}
				}
				
				Amogus.logSlashCommandErrorsToErrorLogChannel(e, "Equation specified: " + event.getOption("equation").getAsString(), event);
				
			}
		}
	}
	
}
