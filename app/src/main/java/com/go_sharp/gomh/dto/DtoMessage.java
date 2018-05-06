package com.go_sharp.gomh.dto;


public class DtoMessage {

    private long id;
    private int type_id;
    private String description;
    private String title;
    private String content;
    private int seen;
    private long idMessage;
    private String idUser;
    private String timestampCel;
    private String imei;
    private String timeZoneCel;
    private String hash;

    public long getId() {
        return id;
    }

    public DtoMessage setId(long id) {
        this.id = id;
        return this;
    }

    public int getType_id() {
        return type_id;
    }

    public DtoMessage setType_id(int type_id) {
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

    public int getSeen() {
        return seen;
    }

    public DtoMessage setSeen(int seen) {
        this.seen = seen;
        return this;
    }

    public long getIdMessage() {
        return idMessage;
    }

    public DtoMessage setIdMessage(long idMessage) {
        this.idMessage = idMessage;
        return this;
    }

    public String getIdUser() {
        return idUser;
    }

    public DtoMessage setIdUser(String idUser) {
        this.idUser = idUser;
        return this;
    }

    public String getTimestampCel() {
        return timestampCel;
    }

    public DtoMessage setTimestampCel(String timestampCel) {
        this.timestampCel = timestampCel;
        return this;
    }

    public String getImei() {
        return imei;
    }

    public DtoMessage setImei(String imei) {
        this.imei = imei;
        return this;
    }

    public String getTimeZoneCel() {
        return timeZoneCel;
    }

    public DtoMessage setTimeZoneCel(String timeZoneCel) {
        this.timeZoneCel = timeZoneCel;
        return this;
    }

    public String getHash() {
        return hash;
    }

    public DtoMessage setHash(String hash) {
        this.hash = hash;
        return this;
    }
}
