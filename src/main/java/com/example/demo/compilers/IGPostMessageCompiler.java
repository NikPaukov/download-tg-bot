package com.example.demo.compilers;

import com.example.demo.parsers.AbstractLinkParser;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;

@Component
@Qualifier("ig-posts")
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class IGPostMessageCompiler extends MessageCompiler {

    private final AbstractLinkParser parser;

    public IGPostMessageCompiler(@Qualifier("ig") AbstractLinkParser parser) {
        this.parser = parser;
        this.keyword="post";
    }

    @Override
    public SendMessage compile(String link, long chatId, int messageId) {
        SendMessage message = new SendMessage();
        message.setReplyToMessageId(messageId);
        message.setChatId(chatId);
        message.setParseMode(ParseMode.MARKDOWN);
        JSONObject media = null;
             try {
                media = parser.parse(link);
             } catch (IOException | InterruptedException | ParseException e) {
                 log.error("Failed to access ig download api");
                return null;
           }

        if (media.get("error") != null && ((String) media.get("error")).contains("private")) {
            log.error("Account is private or the error has occurred");
            message.setText("This post is private or an error has occurred");
            return message;
        }

        if (media.get("media") != null) {

            if (media.get("title") != null && ((String) media.get("title")).length() < 150) {
                message.setText("["+keyword+"](" + media.get("media") + ")\n" + media.get("title"));
            } else {
                message.setText("["+keyword+"](" + media.get("media") + ")");
            }
            return message;
        }
        return null;
    }
}
