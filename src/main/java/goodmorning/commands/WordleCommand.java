package goodmorning.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import goodmorning.Bot;
import goodmorning.parser.WordleParser;

public class WordleCommand extends Command {

    private final static String WORDLE_DONE_MESSAGE = "Daily wordle complete! +100 Aura points! LFGGGG\n";
    private final static String WORDLE_UNDONE_MESSAGE = "Oops! Wordle marked undone\n";
    private final static String USER_DONE_ACK = " has finished the daily wordle, and in %s tries! Congrats!";
    private final static String USER_UNDONE_ACK = " has not finished the daily wordle, keep it up!";

    private SendMessage sendMessage;

    public WordleCommand(Message message, Bot bot, WordleParser wordleParser) {
        createWordleAck(message, bot, wordleParser);
    }

    private void createWordleAck(Message message, Bot bot, WordleParser wordleParser) {
        sendMessage = SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text("")
                .build();

        StringBuilder wordleAcknowledged = new StringBuilder();

        if (wordleParser.doneWordle()) {
            wordleAcknowledged.append(WORDLE_DONE_MESSAGE)
                    .append(message.getFrom().getFirstName())
                    .append(String.format(USER_DONE_ACK, wordleParser.getTries()));
            sendMessage.setText(wordleAcknowledged.toString());
        } else {
            wordleAcknowledged.append(WORDLE_UNDONE_MESSAGE)
                    .append(message.getFrom().getFirstName())
                    .append(USER_UNDONE_ACK);
            sendMessage.setText(wordleAcknowledged.toString());
        }
    }

    @Override
    public void execute(Bot bot) throws TelegramApiException {
        bot.execute(sendMessage);
    }
}
