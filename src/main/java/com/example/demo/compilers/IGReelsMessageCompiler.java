package com.example.demo.compilers;


import com.example.demo.parsers.AbstractLinkParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;

@Component
@Qualifier("ig-reels")
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class IGReelsMessageCompiler extends MessageCompiler {
    public AbstractLinkParser getParser() {
        return parser;
    }

    public IGReelsMessageCompiler(AbstractLinkParser parser) {
        this.parser = parser;
        this.keyword="reel";
    }

    @Qualifier("ig")
    private final AbstractLinkParser parser;


    @Override
    public SendMessage compile(String link, long chatId, int messageId)  {
        JSONObject media;
        try {
            media = parser.parse(link);
        } catch (IOException | InterruptedException | ParseException e) {
            log.error("Failed to access ig download api");
            return null;
        }
        if(media.get("media")!=null) {
            SendMessage message = new SendMessage();
            message.setReplyToMessageId(messageId);
            message.setChatId(chatId);
            message.setText("["+keyword+"](" + media.get("media") + ")");
            message.setParseMode(ParseMode.MARKDOWN);
            return message;
        }
        return null;
    }
}
