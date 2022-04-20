package bot.amogus.listeners;

import bot.amogus.Amogus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class Invite extends ListenerAdapter {

	String invite = "https://discord.com/api/oauth2/authorize?client_id=" + Amogus.amogus.getSelfUser().getId() +"&permissions=535331077185&scope=bot%20applications.commands";
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if(event.getName().equals("invite")) {
						
			EmbedBuilder emb = new EmbedBuilder();
			emb.setDescription("This is the [invite link](" + invite + ") of this bot!")
			   .setAuthor("Amogus Bot")
			   .setColor(Amogus.randomColor());
			
			if(event.getChannelType() != ChannelType.PRIVATE) {
				event.reply("I have Direct-Messaged you the invite link!").setEphemeral(true).queue();
				event.getUser().openPrivateChannel()
							   .flatMap(channel -> channel.sendMessage("Here is this invite!")
							   .setEmbeds(emb.build())
							   .setActionRow(
								  	Button.link(invite, "Invite Me!"))
						 ).queue();
			}
			
		}
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		Message msg = event.getMessage();
		
		if(msg.getContentRaw().equalsIgnoreCase("sus invite")) {
			msg.addReaction("✅").queue();
			
			EmbedBuilder emb = new EmbedBuilder();
			emb.setDescription("This is the [invite link](" + invite + ") of this bot!")
			   .setAuthor("Amogus Bot")
			   .setFooter("By " + event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl())
			   .setColor(Amogus.randomColor());
			
			msg.reply("Here is the invite!")
					  .setEmbeds(emb.build())
					  .setActionRow(
							  	Button.link(invite, "Invite Me!")
							 )
					  .queue();
		}
	}
	
}
