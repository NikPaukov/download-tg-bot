package com.example.demo;

import com.example.demo.compilers.MessageCompiler;
import com.example.demo.config.BotConfig;
import com.example.demo.parsers.AbstractLinkParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;
    private final AbstractLinkParser parser;
    private final CompilersHolder compilersHolder;

    public TelegramBot(BotConfig config, AbstractLinkParser parser, CompilersHolder compilersHolder) {
        this.config = config;
        this.parser = parser;
        this.compilersHolder = compilersHolder;
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            //decompose message
            Message message = update.getMessage();
            String text = message.getText();
            long chatId = message.getChatId();
            String[] words = text.split("\\s+");


            Map<String, MessageCompiler> compilerMap = new HashMap<>();


            for(String word: words){
                MessageCompiler messageCompiler = compilersHolder.getCompiler(word);
                if(messageCompiler != null) compilerMap.put(word, messageCompiler);
            }
            for(String link: compilerMap.keySet()){
                MessageCompiler compiler = compilerMap.get(link);
                SendMessage sendMessage = compiler.compile(link, chatId, message.getMessageId());
                if(sendMessage==null) continue;
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                log.error("TgApiException: Failed to send a message " + text +" in chat " + chatId);
                }
            }

        }
    }
}
