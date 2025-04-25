package goodmorning.parser;

import org.telegram.telegrambots.meta.api.objects.Message;

public class WordleParser {

    private Message message;
    private boolean finishedWordle;
    private int tries;

    public WordleParser(Message message) {
        parse(message);
    }

    private void parseDoneWordle(Message message) {
        finishedWordle = true;
    }

    private void parseNotDoneWordle(Message message) {
        finishedWordle = false;
    }

    private void parse(Message message) {
        String[] args = message.getText().split(" ");

        if (args.length != 3) {
            throw new InvalidCommandException("Wrong number of wordle arguments");
        }

        switch (args[1]) {
        case "done":
            parseDoneWordle(message);
            break;
        case "undone":
            parseNotDoneWordle(message);
            break;
        default:
            throw new InvalidCommandException("Unknown wordle command: " + args[1]);
        }

        try {
            tries = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            tries = 6;
        }
    }

    public Message getMessage() {
        return message;
    }

    public boolean doneWordle() {
        return finishedWordle;
    }

    public int getTries() {
        return tries;
    }
}
