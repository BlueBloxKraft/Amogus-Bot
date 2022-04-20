package bot.amogus.listeners;

import bot.amogus.Amogus;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Shutdown extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if(event.getName().equals("shutdown")) {
			if(Amogus.isOwner(event.getUser())) {
				event.reply("Shutting down the bot...").setEphemeral(true).queue();
				Amogus.amogus.shutdown();
			} else {
				event.reply("You are not the bot's owner >:(").setEphemeral(true).queue();
			}
		}
	}
	
}
