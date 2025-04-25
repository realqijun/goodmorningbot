package goodmorning.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import goodmorning.Bot;

public class SendImageCommand extends Command {

    private SendPhoto sendPhoto;
    private File tempFile;

    public SendImageCommand(Message message, Bot bot) {
        createSendPhoto(message, bot);
    }

    private void createSendPhoto(Message message, Bot bot) {
        int randomNum = bot.photoRandomizer.nextInt(1, 15);
        String image = "images/image" + randomNum + ".jpg";

        try {
            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream(image);

            if (inputStream == null) {
                System.err.println("Error: Image " + image + " not found in resources!");
                return;
            }

            tempFile = File.createTempFile("temp_image", ".jpg");
            Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            sendPhoto = SendPhoto.builder()
                    .chatId(message.getChatId().toString())
                    .photo(new InputFile(tempFile))
                    .caption("Skibidi good morning!")
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(Bot bot) throws TelegramApiException {
        bot.execute(sendPhoto);
    }
}
