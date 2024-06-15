package ru.telegrambot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Welcome {

    public static void sendWelcomeMessage(TelegramBot bot, String chatId) {
        String welcomeText = "Добро пожаловать! Я ваш погодный бот. Используйте команду /pogoda, чтобы узнать погоду в любом городе. Например: /pogoda Москва";

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(welcomeText);

        try {
            bot.execute(message); // Отправка сообщения
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}