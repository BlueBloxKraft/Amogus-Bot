package bot.amogus.listeners;

import bot.amogus.Amogus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DM extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if(event.getName().equals("message")) {
			User user = event.getOption("user").getAsUser();
			String msg = event.getOption("message").getAsString();
			EmbedBuilder embed = new EmbedBuilder();
			
			event.reply("I have DMed " + user.getAsTag()).setEphemeral(true).queue();
			
			embed.setDescription(msg)
				 .setColor(Amogus.randomColor())
				 .setFooter("Sent by " + event.getUser().getAsTag(), event.getUser().getAvatarUrl());
			
			user.openPrivateChannel().flatMap(
					channel -> channel.sendMessage("A user messaged you!")
									  .setEmbeds(embed.build())).queue();
		}
	}
	
}
