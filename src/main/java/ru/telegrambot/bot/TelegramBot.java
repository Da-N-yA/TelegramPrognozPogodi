package ru.telegrambot.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public String getBotToken() {
        return System.getenv("TELEGRAM_BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            if (messageText.equals("/start")) {
                Welcome.sendWelcomeMessage(this, chatId);
            }

            if (messageText.startsWith("/pogoda")) {
                String city = messageText.replace("/pogoda", "").trim();
                if (!city.isEmpty()) {
                    String weatherReport = Weather.getWeatherReport(city);
                    SendMessage message = new SendMessage();
                    message.setChatId(chatId);
                    message.setText(weatherReport);

                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    SendMessage message = new SendMessage();
                    message.setChatId(chatId);
                    message.setText("Пожалуйста, укажите город. Пример использования: /pogoda Москва");

                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "gangprognoz_bot";
    }
}
