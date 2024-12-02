package com.igladkikh.tutortelebot.controller;

import com.igladkikh.tutortelebot.telegram.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class MainController {
    private final Bot bot;

    @Autowired
    public MainController(Bot bot) {
        this.bot = bot;
    }

    @PostMapping
    public BotApiMethod<?> listener(@RequestBody Update update) {
        if (update.hasMessage()) {
            return autoResponse(update.getMessage());
        }

        return bot.onWebhookUpdateReceived(update);
    }

    private BotApiMethod<Message> autoResponse(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(message.getText())
                .build();
    }
}
