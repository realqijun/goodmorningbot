package goodmorning;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    public static void main(String[] args) {
        //try {
        //    Connection connection = DriverManager.getConnection("jdbc:h2:./todo;AUTO_SERVER=TRUE");
        //    String createTable = "create table if not exists TASK (id identity primary key, name varchar)";
        //    connection.createStatement().executeUpdate(createTable);
        //
        //    String insertQuery = "insert into TASK (name) values ('learn java')";
        //    connection.createStatement().executeUpdate(insertQuery);
        //
        //    String insertStatement = "insert into TASK(name) values (?)";
        //    PreparedStatement ps = connection.prepareStatement(insertStatement);
        //    ps.setString(1, "learn javadb");
        //    ps.execute();
        //
        //} catch (SQLException e) {
        //    e.printStackTrace();
        //}
        try {
            String botToken = System.getenv("BOT_TOKEN");
            String ownerId = System.getenv("OWNER_ID");
            String groupId = System.getenv("GROUP_ID");

            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Bot bot = new Bot(botToken, groupId);
            botsApi.registerBot(bot);

            bot.sendText(Long.decode(ownerId), "Hello, I am " + Bot.BOT_NAME);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
