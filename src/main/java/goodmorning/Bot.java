package goodmorning;
import java.util.Random;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import goodmorning.commands.Command;
import goodmorning.parser.CommandParser;
import goodmorning.parser.InvalidCommandException;

public class Bot extends TelegramLongPollingBot {

    public final static String BOT_NAME = "GoodMorningMessagesBot";
    private final static String NOT_GROUP_ERROR_MESSAGE = "You got too little aura points, please don't ever talk to me again";

    private final String botToken;
    private final String groupId;

    public final Random photoRandomizer = new Random();

    Bot(String botToken, String groupId) {
        this.botToken = botToken;
        this.groupId = groupId;
        photoRandomizer.setSeed(System.currentTimeMillis());
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        // chatId is a Long type
        var chatId = message.getChatId();

        if (!chatId.equals(Long.decode(groupId))) {
            sendMessageToChat(NOT_GROUP_ERROR_MESSAGE, chatId.toString());
            return;
        }

        if (update.hasMessage()) {
            handleMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            var callback = update.getCallbackQuery();
        } else {
            System.out.println("unhandled message: " + update);
        }
    }

    private void executeCommand(Command command) {
        try {
            command.execute(this);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void handleMessage(Message message) {
        if (message.isCommand()) {
            Command command;
            try {
                command = CommandParser.parseCommand(message, this);
            } catch (InvalidCommandException e) {
                sendMessageToChat(e.getMessage(), message.getChatId().toString());
                return;
            }
            executeCommand(command);
        } else {
            copyMessage(message.getChatId(), message.getMessageId());
        }
    }

    private void sendMessageToChat(String msg, String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, msg);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendText(Long who, String what) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(who.toString())
                .text(what)
                .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void copyMessage(Long chatId, Integer msgId){
        CopyMessage cm = CopyMessage.builder()
                .fromChatId(chatId.toString())
                .chatId(chatId.toString())
                .messageId(msgId)
                .build();
        try {
            execute(cm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}