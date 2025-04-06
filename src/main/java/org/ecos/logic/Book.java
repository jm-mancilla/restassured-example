package org.ecos.logic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Book {
    protected String isbn;
    protected String title;
    protected String subTitle;
    protected String author;
    @JsonProperty("publish_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSXXXX")
    protected OffsetDateTime publishDate;
    protected String publisher;
    protected int pages;
    protected String description;
    protected String website;
}