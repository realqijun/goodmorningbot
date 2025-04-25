package goodmorning.parser;

import org.telegram.telegrambots.meta.api.objects.Message;

import goodmorning.Bot;
import goodmorning.commands.Command;
import goodmorning.commands.HelpCommand;
import goodmorning.commands.MenuCommand;
import goodmorning.commands.SendImageCommand;
import goodmorning.commands.StartCommand;
import goodmorning.commands.WordleCommand;

public class CommandParser {

    private final static String START_COMMAND = "/start";
    private final static String PHOTO = "/photo";
    private final static String HELP = "/help";
    private final static String MENU = "/menu";
    private final static String WORDLE = "/wordle";

    public static Command parseCommand(Message message, Bot bot) {
        String[] commandArgs = message.getText().split(" ");
        switch (commandArgs[0]) {
        case START_COMMAND:
            return new StartCommand();
        case HELP:
            return new HelpCommand(message);
        case PHOTO:
            return new SendImageCommand(message, bot);
        case MENU:
            return new MenuCommand(message);
        case WORDLE:
            return new WordleCommand(message, bot, new WordleParser(message));
        default:
            throw new InvalidCommandException("Unknown command: " + commandArgs[0]);
        }
    }
}
