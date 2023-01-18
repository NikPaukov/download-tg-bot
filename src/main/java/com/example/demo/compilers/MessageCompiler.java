package com.example.demo.compilers;

import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;

public interface MessageCompiler {
    public SendMessage compile(String link, long chatId);
}
