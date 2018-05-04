package com.go_sharp.gomh.dto;

/**
 * Created by gnu on 4/05/18.
 */

public class DtoMessage {
    private long id;
    private long type_id;
    private String description;
    private String title;
    private String content;

    public long getId() {
        return id;
    }

    public DtoMessage setId(long id) {
        this.id = id;
        return this;
    }

    public long getType_id() {
        return type_id;
    }

    public DtoMessage setType_id(long type_id) {
        this.type_id = type_id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DtoMessage setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DtoMessage setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public DtoMessage setContent(String content) {
        this.content = content;
        return this;
    }
}
