package com.example.demo.compilers;


import com.example.demo.parsers.AbstractLinkParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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
@Data
@Getter
@AllArgsConstructor
public class IGReelsMessageCompiler implements MessageCompiler {



    @Qualifier("ig")
    private final AbstractLinkParser parser;


    @Override
    public SendMessage compile(String link, long chatId)  {
        JSONObject media;
        try {
            media = parser.parse(link);
        } catch (IOException | InterruptedException | ParseException e) {
         return null;
        }
        if(media.get("media")!=null) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("[reels](" + media.get("media") + ")");
            message.setParseMode(ParseMode.MARKDOWN);
            return message;
        }
        return null;
    }
}
