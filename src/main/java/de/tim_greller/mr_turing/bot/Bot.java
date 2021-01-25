package de.tim_greller.mr_turing.bot;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;

/**
 * This is the bot class that communicates with discord using Discord4J.
 * 
 * @author Tim Greller
 */
public class Bot {

	/**
	 * @param args The arguments required for the bot to log in. The first argument has to
	 *             be the token.
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			throw new IllegalArgumentException("No token recieved.");
		}
		String token = args[0];
		GatewayDiscordClient client = DiscordClientBuilder.create(token)
		        .build()
		        .login()
		        .block();
		
		client.onDisconnect().block();
	}

}
