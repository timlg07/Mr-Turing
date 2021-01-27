package de.tim_greller.mr_turing.bot;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.commands.BotCommand;
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
	private final String prefix = "!tm";
	
	/** Whether the prefix has to be followed by a space or not. */
	private final boolean isPrefixFollowedBySpace = true;
	
	/** The bot's token. */
	private String token;
	
	/** The client used to communicate with Discord. */
	private GatewayDiscordClient client;
	
	/** The commands of the bot, mapped with their corresponding trigger. */
	private Map<String, BotCommand> commands;
	
	/**
	 * Creates a new Bot instance with the given token. In order for the bot to start,
	 * you have to log it in using {@link Bot#login()}.
	 * 
	 * @param token The token that will be needed to build the bot and log it in.
	 */
	Bot(String token) {
		this.token = token;
		this.commands = new HashMap<>();
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
	
	/**
	 * Adds a command to this bot, which will be executed when triggered by a message.
	 * Each command has to have an unique call name. If a command with the same call name
	 * already exists, the given command will not be added.
	 * 
	 * @param command The command that should be added.
	 * @return {@code true} if the command could be added, {@code false} if a command with
	 * 		   the same trigger was previously added to this bot.
	 */
	boolean addCommand(BotCommand command) {
		final String key = command.getCallName().toLowerCase();
		final boolean keyDoesNotExist = !commands.containsKey(key);
		
		if (keyDoesNotExist) {
			commands.put(key, command);
		}
		
		return keyDoesNotExist;
	}
	
	/**
	 * Adds multiple commands to this bot. This is equivalent to multiple calls of 
	 * {@link Bot#addCommand(BotCommand)}.
	 * 
	 * @param commands The commands that should be added
	 * @return {@code true} if all commands could be successfully added, {@code false} if
	 *         at least one of the commands was a duplicate and therefore was skipped.
	 */
	boolean addCommands(BotCommand... commands) {
		return Stream.of(commands).map(this::addCommand).allMatch(b -> b);
	}
	
	private void parseAndExecute(Message message) throws InvalidCommandSyntaxException {
		final String content = message.getContent();
		final int prefixLength = prefix.length() + (isPrefixFollowedBySpace ? 1 : 0);
		
		if (prefixLength >= content.length()) {
			throw new InvalidCommandSyntaxException("No command provided.");
		}

		final String contentWithoutPrefix = content.substring(prefixLength);
		
		/*
		 * Each command has to be separated from its parameter(s) with a space. By
		 * splitting the content on the first space, we separate it into those two parts.
		 * If the message contains no parameter, the array will only contain one element.
		 */
		final String[] commandParts = contentWithoutPrefix.trim().split(" ", 2);		
		final String commandName = commandParts[0].toLowerCase();
		String commandParameter = ""; 
		
		if (commandParts.length == 2) {
			commandParameter = commandParts[1];
		}
		
		BotCommand command = commands.get(commandName);
		
		if (command == null) {
			throw new InvalidCommandSyntaxException(
					"Unknown command: \"" + commandName + "\"");
		}
		
		command.execute(message, commandParameter, null);
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
    	
    	// Ignore messages from bots.
    	if (isBot) {
    		return Mono.empty();
    	}

    	if (content.toLowerCase().startsWith(prefix)) {
        	System.out.println("[ MSG:] " + content);
        	try {
				parseAndExecute(message);
			} catch (InvalidCommandSyntaxException e) {
				return message.getChannel().flatMap(c -> {
					return c.createMessage("**Error.** " + e.getMessage());
				});
			}
    	}
    	
        return Mono.empty();
    }
}
