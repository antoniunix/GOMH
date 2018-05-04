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

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }

    public long getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(long idMessage) {
        this.idMessage = idMessage;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTimestampCel() {
        return timestampCel;
    }

    public void setTimestampCel(String timestampCel) {
        this.timestampCel = timestampCel;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getTimeZoneCel() {
        return timeZoneCel;
    }

    public void setTimeZoneCel(String timeZoneCel) {
        this.timeZoneCel = timeZoneCel;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
