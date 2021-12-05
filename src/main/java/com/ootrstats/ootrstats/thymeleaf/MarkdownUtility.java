package com.ootrstats.ootrstats.thymeleaf;

import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.List;

public class MarkdownUtility {
    final Parser parser;
    final HtmlRenderer htmlRenderer;

    public MarkdownUtility() {
        var extensions = List.of(AutolinkExtension.create());

        this.parser = Parser.builder()
                .extensions(extensions)
                .build();

        this.htmlRenderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();
    }

    public String parse(String markdown) {
        var document = parser.parse(markdown);
        return htmlRenderer.render(document);
    }
}
