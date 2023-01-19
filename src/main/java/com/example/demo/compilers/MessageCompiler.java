package com.example.demo.compilers;

import lombok.EqualsAndHashCode;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;
@EqualsAndHashCode
public abstract class MessageCompiler {
    protected String keyword;
    public abstract SendMessage compile(String link, long chatId, int messageId);
}
