package de.tim_greller.mr_turing.bot;


import org.reactivestreams.Publisher;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.ReactiveEventAdapter;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

/**
 * This is the bot class that communicates with discord using Discord4J and uses the
 * Turing machine model to execute commands.
 */
public class Bot extends ReactiveEventAdapter {
	
	/** The prefix before every command that should get interpreted by this bot. */
	private final String prefix = "!tm ";
	
	/** The bot's token */
	private String token;
	
	/** The client used to communicate with Discord. */
	private GatewayDiscordClient client;
	
	/**
	 * Creates a new Bot instance with the given token. In order for the bot to start,
	 * you have to log it in using {@link Bot#login()}.
	 * 
	 * @param token The token that will be needed to build the bot and log it in.
	 */
	Bot(String token) {
		this.token = token;
	}
	
	/**
	 * Creates the client with the provided token, logs it in and starts listening to
	 * events from it.
	 */
	void login() {
		client = DiscordClientBuilder.create(token).build().login().block();		
		client.on(this).subscribe();
		client.onDisconnect().block();
	}
	
	@Override
	public Publisher<?> onReady(ReadyEvent event) {
        return Mono.fromRunnable(() ->
        	System.out.println("Connected as " + event.getSelf().getTag())
        );
    }

	@Override
    public Publisher<?> onMessageCreate(MessageCreateEvent event) {
    	final Message message = event.getMessage();
    	final String content = message.getContent();
    	final boolean isBot = message.getAuthor().map(user -> user.isBot()).orElse(false);
    	
    	if (isBot) {
    		return Mono.empty();
    	}

    	if (content.toLowerCase().startsWith(prefix)) {
        	System.out.println("[ MSG:] " + content);
    		return event.getMessage().getChannel()
                    .flatMap(channel -> channel.createMessage("You tried to execute a command, but I don't have any commands yet. What a pitty."));
    	}
        return Mono.empty();
    }
}
