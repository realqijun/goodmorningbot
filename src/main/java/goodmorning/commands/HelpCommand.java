package goodmorning.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import goodmorning.Bot;

public class HelpCommand extends Command {
    private final static String HELP_MESSAGE = "Help yourself";

    private SendMessage sendMessage;

    public HelpCommand(Message message) {
        createSendMessage(message);
    }

    private void createSendMessage(Message message) {
        sendMessage = SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(HELP_MESSAGE)
                .build();
    }

    @Override
    public void execute(Bot bot) throws TelegramApiException {
        bot.execute(sendMessage);
    }
}
