package goodmorning.commands;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import goodmorning.Bot;

public abstract class Command {
    public abstract void execute(Bot bot) throws TelegramApiException;
}
