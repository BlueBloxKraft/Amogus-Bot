package bot.amogus.listeners;

import bot.amogus.Amogus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Sus extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(Amogus.containsIgnoreCase(event.getMessage().getContentRaw(), "amogus")) {
			event.getMessage().addReaction(Amogus.amogus.getEmoteById(964846284982874112L)).queue();
			EmbedBuilder emb = new EmbedBuilder();
			emb.setDescription("<:red:964865438519554090>")
			   .setThumbnail("https://cdn.discordapp.com/attachments/953531386273366049/964850752008912906/amogus.gif")
			   .setColor(Amogus.randomColor());
			event.getMessage().reply("sussy baka ඞඞඞඞ")
						 	  .setEmbeds(emb.build())
						 	  .queue();
		}
	}
	
}