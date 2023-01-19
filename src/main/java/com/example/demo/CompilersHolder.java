package com.example.demo;

import com.example.demo.compilers.MessageCompiler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class CompilersHolder {
    public CompilersHolder(@Qualifier("ig-reels") MessageCompiler igReelsCompiler,

                           @Qualifier("ig-posts") MessageCompiler igPostsCompiler,

                           @Qualifier("ig-stories") MessageCompiler igStoriesCompiler) {
        this.igReelsCompiler = igReelsCompiler;
        this.igPostsCompiler = igPostsCompiler;
        this.igStoriesCompiler = igStoriesCompiler;
    }


    private final MessageCompiler igReelsCompiler;

    private final MessageCompiler igPostsCompiler;
    private final MessageCompiler igStoriesCompiler;

    private boolean validURL(String link){
        if (!link.contains(".")) return false;
        try {
            URL url = new URL(link);
            url.toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return true;

    }

    public MessageCompiler getCompiler(String link) {
        if(!validURL(link)) return null;
        if (link.contains("instagram.com/reel")) {
            return igReelsCompiler;
        }
        else if (link.contains("instagram.com/p")) {
            return igPostsCompiler;
        } else if (link.contains("instagram.com/stories")) {
            return igStoriesCompiler;
        }

        return null;
    }
}
