package com.example.demo.parsers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Qualifier("ig")
public class InstagramLinkParser extends AbstractLinkParser {

    protected InstagramLinkParser(JSONParser jsonParser) {
        super(jsonParser);
    }

    @Override
    public JSONObject parse(String link) throws IOException, InterruptedException, ParseException {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://instagram-downloader-download-instagram-videos-stories.p.rapidapi.com/index?url=" + link))
                    .header("X-RapidAPI-Key", "90b03ec8a0mshe237c95d7bf652dp1e19eajsn224ee10750ea")
                    .header("X-RapidAPI-Host", "instagram-downloader-download-instagram-videos-stories.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return (JSONObject) ((jsonParser.parse(response.body())));
    }
}
