package com.example.demo.compilers;

import com.example.demo.parsers.AbstractLinkParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@Qualifier("ig-stories")
public class IGStoriesMessageCompiler extends IGPostMessageCompiler {
    public IGStoriesMessageCompiler(AbstractLinkParser parser) {
        super(parser);
        this.keyword="story";
    }
}
