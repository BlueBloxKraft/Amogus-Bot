package bot.amogus.listeners;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import bot.amogus.Amogus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ping extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if(event.getName().equals("ping")) {
			event.reply("Ping: pinging...").queue(
						message -> message.editOriginal("**Pong!**").setEmbeds(
							new EmbedBuilder()
									.setDescription(	
											"Ping: `" + (TimeUnit.MILLISECONDS.convert(
																OffsetDateTime.now().minusNanos(event.getTimeCreated().getNano()
														).getNano(), TimeUnit.NANOSECONDS))  + "ms`\nWebsocket: `" + event.getJDA().getGatewayPing() + "ms`"	
										)
									.setColor(Amogus.randomColor())
									.setFooter("By " + event.getUser().getAsTag(), event.getUser().getAvatarUrl())
										.build()
							).queue());
		}
	}
	
}
