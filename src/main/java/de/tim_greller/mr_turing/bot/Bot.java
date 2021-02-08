package de.tim_greller.mr_turing.bot;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.reactivestreams.Publisher;

import de.tim_greller.mr_turing.bot.commands.BotCommand;
import de.tim_greller.mr_turing.turing_machine.DeterministicTuringMachine;
import de.tim_greller.mr_turing.turing_machine.TuringMachine;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.ReactiveEventAdapter;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This is the bot class that communicates with discord using Discord4J and uses the
 * Turing machine model to execute commands.
 */
public class Bot extends ReactiveEventAdapter {

    /** The check mark emoji used to react to a successfull command-call. */
    private static final ReactionEmoji CHECKMARK = ReactionEmoji.unicode("\u2705");
    
    /** The cross emoji with which the bot will react to messages with wrong syntax. */
    private static final ReactionEmoji CROSS = ReactionEmoji.unicode("\u274c");
    
    /** The prefix before every command that should get interpreted by this bot. */
    private final String prefix = "!tm";
    
    /** The bot's token. */
    private String token;
    
    /** The client used to communicate with Discord. */
    private GatewayDiscordClient client;
    
    /** The commands of the bot, mapped with their corresponding trigger. */
    private Map<String, BotCommand> commands;
    
    /** The individual Turing machine of each channel. */
    private Map<Snowflake, TuringMachine> turingMachines;
    
    /**
     * Creates a new Bot instance with the given token. In order for the bot to start,
     * you have to log it in using {@link Bot#login()}.
     * 
     * @param token The token that will be needed to build the bot and log it in.
     */
    Bot(String token) {
        this.token = token;
        
        turingMachines = new HashMap<>();
        commands = new HashMap<>();
        
        /*
         * The help command is automatically generated for each bot and has access to his
         * scope (in order to list all commands and their description), but is treated
         * like a normal command.
         */
        BotCommand help = generateHelpCommand();
        commands.put(help.getCallName(), help);
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
     *            the same trigger was previously added to this bot.
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
    
    /**
     * Takes a message that should be interpreted as a command, determines the command 
     * trigger and the argument part and executes the specified command.
     * 
     * @param message The message which should be interpreted and executed.
     * @return The publisher that completes when the called command has finished 
     *            execution.
     * @throws InvalidCommandSyntaxException Thrown if the message does not contain a
     *                                       command in a valid syntax.
     */
    private Publisher<?> parseAndExecute(Message message) 
            throws InvalidCommandSyntaxException {
        
        final Snowflake channelId = message.getChannelId();
        final String content = message.getContent();
        final String contentWithoutPrefix = content.substring(prefix.length()).trim();
        
        if (contentWithoutPrefix.length() == 0) {
            throw new InvalidCommandSyntaxException("No command provided.");
        }
        
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
        
        TuringMachine tm = turingMachines.get(channelId);
        if (tm == null) {
            tm = new DeterministicTuringMachine();
            turingMachines.put(channelId, tm);
        }
        
        return command.execute(message, commandParameter, tm);
    }
    
    /**
     * Generates the help command of this bot. The command will provide information about
     * the bot and its usage.
     * 
     * @return The help command.
     */
    private BotCommand generateHelpCommand() {
        return new BotCommand() {
            @Override
            public String getTitle() { return "Help"; }
            
            @Override
            public String getDescription() { return "Shows this help text."; }
            
            @Override
            public String getCallName() { return "help"; }
            
            @Override
            public Publisher<?> execute(Message msg, String arg, TuringMachine tm) {
                return msg.getChannel().flatMap(channel -> {
                    return channel.createMessage(Bot.this.generateHelpText());
                });
            }
        };
    }
    
    /**
     * Generates the help text of this bot, including an overview of all commands and
     * their description.
     * 
     * @return The help text (uses Discords markdown styling).
     */
    private String generateHelpText() {
        final StringBuilder helpText = new StringBuilder();
        final String username = client.getSelf().block().getUsername();
        final String newListItem = "\n  - ";
        
        helpText.append("Hello. I am ").append(username)
                .append(".\nYou can use me with the prefix \"").append(prefix)
                .append("\" and the following commands:");
        
        commands.forEach((name, command) -> {
            helpText.append(newListItem).append("**").append(command.getTitle())
                    .append("** (`").append(name).append("`) ")
                    .append(command.getDescription());
        });
        
        return helpText.toString();
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
        final boolean isBot = message.getAuthor().map(user -> user.isBot()).orElse(false);
        final boolean usesPrefix = message.getContent().toLowerCase().startsWith(prefix);
        
        // Ignore messages from bots and messages without the correct prefix.
        if (isBot || !usesPrefix) {
            return Mono.empty();
        }
        
        try {
            
            /*
             * Parse and execute the message and - if successful - react with the
             * checkmark. Then return the concatenation of both publishers.
             */
            return Flux.concat(parseAndExecute(message), message.addReaction(CHECKMARK));
            
        } catch (InvalidCommandSyntaxException | IllegalArgumentException 
                | IllegalStateException e) {
            
            /*
             * Signalize the wrong syntax / command usage by reacting with a red cross and
             * showing the reason for the exception.
             */
            return Flux.concat(
                    message.addReaction(CROSS), 
                    message.getChannel().flatMap(c -> {
                        return c.createMessage("**Error.** " + e.getMessage());
                    })
            );
        }
    }
}
