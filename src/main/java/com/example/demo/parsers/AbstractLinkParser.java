package com.example.demo.parsers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public abstract class AbstractLinkParser {
    protected JSONParser jsonParser;

    public AbstractLinkParser(JSONParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public abstract JSONObject parse(String link) throws IOException, InterruptedException, ParseException;
}
