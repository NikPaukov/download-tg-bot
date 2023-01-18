package com.example.demo.compilers;

import com.example.demo.parsers.AbstractLinkParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;

@Component
@Qualifier("ig-posts")
public class IgPostMessageCompiler implements MessageCompiler {

    private final AbstractLinkParser parser;

    public IgPostMessageCompiler(@Qualifier("ig") AbstractLinkParser parser) {
        this.parser = parser;
    }

    @Override
    public SendMessage compile(String link, long chatId) {
        SendMessage message = new SendMessage();

        JSONObject media = null;
             try {
                media = parser.parse(link);
             } catch (IOException | InterruptedException | ParseException e) {
                return null;
           }

        if (media.get("error") != null && ((String) media.get("error")).contains("private")) {
            message.setText("This post is private or error has occurred");
        }

        if (media.get("media") != null) {
            message.setChatId(chatId);
            message.setParseMode(ParseMode.MARKDOWN);

            if (media.get("title") != null && ((String) media.get("title")).length() < 150) {
                message.setText("[post](" + media.get("media") + ")\n" + media.get("title"));
            } else {
                message.setText("[post](" + media.get("media") + ")");
            }
            return message;
        }
        return null;
    }
}
