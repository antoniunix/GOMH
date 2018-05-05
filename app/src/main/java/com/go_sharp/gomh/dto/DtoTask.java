package com.go_sharp.gomh.dto;

/**
 * Created by gnu on 4/05/18.
 */

public class DtoTask {

    private long id;
    private long type_id;
    private String description;
    private String title;
    private String content;

    public long getId() {
        return id;
    }

    public DtoTask setId(long id) {
        this.id = id;
        return this;
    }

    public long getType_id() {
        return type_id;
    }

    public DtoTask setType_id(long type_id) {
        this.type_id = type_id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DtoTask setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DtoTask setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public DtoTask setContent(String content) {
        this.content = content;
        return this;
    }
}
