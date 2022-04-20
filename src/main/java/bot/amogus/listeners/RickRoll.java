package bot.amogus.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.Color;

public class RickRoll extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if(event.getName().equals("rickroll")) {
			event.reply("Sent a the Rick Roll to " + event.getOption("user").getAsUser().getAsTag()).setEphemeral(true).queue();
			event.getOption("user").getAsUser()
										.openPrivateChannel()
										.flatMap(
											channel -> channel.sendMessage("Free Nitro!!!")
																.setEmbeds(
																	new EmbedBuilder()
																		.setDescription(event.getUser().getAsTag() + " gifted you FREE Discord Nitro!")
																		.setColor(new Color(88, 101, 242))
																			.build()
															   ).setActionRow(
																	   	 Button.link("https://youtu.be/dQw4w9WgXcQ", "Click Here To Get Nitro!")
																)
												).queue();
		}
	}
}
