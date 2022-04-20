package bot.amogus.listeners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import bot.amogus.Amogus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class YTStats extends ListenerAdapter {

	static String apiKey = "API_KEY_GOES_HERE"/*--insert the api key from console.cloud.google.com also enable youtube api v3 from the api library*/;
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		boolean b = event.getOption("use_username").getAsBoolean();
		String str = event.getOption("channel").getAsString();
		JSONObject obj = getYTStats(b, str);
		JSONObject brand = getYTBranding(b, str);
		
		if(event.getName().equals("ytstats")) {
			event.reply("Statistics of " + brand.getJSONObject("channel").getString("title"))
					.addEmbeds(
							new EmbedBuilder()
								.setDescription(
											"Subscribers: `" + obj.get("subscriberCount") + "`\n" +
											"Views: `" + obj.get("viewCount") + "`\n" +
											"Videos: `" + obj.get("videoCount") + "`\n"
										)
								.setAuthor(
										getYTBranding(b, str)
											.getJSONObject("channel")
											.getString("title"),
										"https://www.youtube.com/channel/" + getId(b, str)
									)
								.setColor(Amogus.randomColor())
								.setThumbnail(getProfilePicUrl(b, str))
								.build()		
						).addActionRow(
								Button.link("https://www.youtube.com/channel/" + getId(b, str) + "?sub_confirmation=1", "Click Here to subscribe!")
							).queue();
		}
	}
	
	/**
	 * get stats about a yt channel
	 * 
	 * @param useUsername
	 * @param channel
	 * @return yt stats json obj
	 */
	public static JSONObject getYTStats(boolean useUsername, String channel) {
		return getStatsPageItems(useUsername, channel).getJSONObject(0).getJSONObject("statistics");	
	}
	
	public static String getProfilePicUrl(boolean b, String channel) {
		HttpsURLConnection conn = null;
		String pfpUrl = null;
		
		try {
			URL url = new URL("https://www.googleapis.com/youtube/v3/channels?part=snippet&id=" + getId(b, channel) +"&fields=items%2Fsnippet%2Fthumbnails&key=" + apiKey);
			
			conn = (HttpsURLConnection)url.openConnection();
			conn.connect();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			
			while((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			br.close();
			
			JSONObject jsonObj = new JSONObject(sb.toString());
			JSONArray items = jsonObj.getJSONArray("items");
			JSONObject snippet = items.getJSONObject(0).getJSONObject("snippet");
			JSONObject thumbnails = snippet.getJSONObject("thumbnails");
			JSONObject high = thumbnails.getJSONObject("high");
			pfpUrl = high.getString("url");
			
		} catch(MalformedURLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.disconnect();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return pfpUrl;
	}
	
	public static JSONObject getYTBranding(boolean useUsername, String channel) {
		HttpsURLConnection conn = null;
		JSONObject ja = null;
		
		try {
			URL url = null;
			if(useUsername) {
				url = new URL("https://www.googleapis.com/youtube/v3/channels?part=brandingSettings&forUsername=" + channel + "&key=" + apiKey);
			} else {
				url = new URL("https://www.googleapis.com/youtube/v3/channels?part=brandingSettings&id=" + channel + "&key=" + apiKey);
			}
			
			conn = (HttpsURLConnection)url.openConnection();
			conn.connect();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			
			while((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			br.close();
			
			JSONObject jsonObj = new JSONObject(sb.toString());
			JSONArray items = jsonObj.getJSONArray("items");
			JSONObject obj = items.getJSONObject(0);
			ja = obj.getJSONObject("brandingSettings");
			
		} catch(MalformedURLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.disconnect();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return ja;
	}

	public static String getId(boolean b, String str) {
		return getStatsPageItems(b, str).getJSONObject(0).getString("id");
	}
	
	public static JSONArray getStatsPageItems(boolean useUsername, String channel) {
		HttpsURLConnection conn = null;
		JSONArray items = null;
		
		try {
			URL url = null;
			if(useUsername) {
				url = new URL("https://www.googleapis.com/youtube/v3/channels?part=statistics&forUsername=" + channel + "&key=" + apiKey);
			} else {
				url = new URL("https://www.googleapis.com/youtube/v3/channels?part=statistics&id=" + channel + "&key=" + apiKey);
			}
			
			conn = (HttpsURLConnection)url.openConnection();
			conn.connect();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			
			while((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			br.close();
			
			JSONObject jsonObj = new JSONObject(sb.toString());
			items = jsonObj.getJSONArray("items");
			
		} catch(MalformedURLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.disconnect();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return items;
	}
	
}
