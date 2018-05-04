package com.go_sharp.gomh.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gnu on 25/04/18.
 */

public class DtoDownloadableFiles implements Parcelable {

    private long id;
    private String ext;
    private String endDate;
    private String description;
    private String title;
    private String url;
    private String startDate;
    private String md5;
    private String nameFiel;

    public String getNameFiel() {
        return nameFiel;
    }

    public DtoDownloadableFiles setNameFiel(String nameFiel) {
        this.nameFiel = nameFiel;
        return this;
    }

    public long getId() {
        return id;
    }

    public DtoDownloadableFiles setId(long id) {
        this.id = id;
        return this;
    }

    public String getExt() {
        return ext;
    }

    public DtoDownloadableFiles setExt(String ext) {
        this.ext = ext;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public DtoDownloadableFiles setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DtoDownloadableFiles setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DtoDownloadableFiles setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public DtoDownloadableFiles setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public DtoDownloadableFiles setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getMd5() {
        return md5;
    }

    public DtoDownloadableFiles setMd5(String md5) {
        this.md5 = md5;
        return this;
    }

    @Override
    public String toString() {
        return "DtoDownloadableFiles{" +
                "id=" + id +
                ", ext='" + ext + '\'' +
                ", endDate='" + endDate + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", startDate='" + startDate + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.ext);
        dest.writeString(this.endDate);
        dest.writeString(this.description);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.startDate);
        dest.writeString(this.md5);
        dest.writeString(this.nameFiel);
    }

    public DtoDownloadableFiles() {
    }

    protected DtoDownloadableFiles(Parcel in) {
        this.id = in.readLong();
        this.ext = in.readString();
        this.endDate = in.readString();
        this.description = in.readString();
        this.title = in.readString();
        this.url = in.readString();
        this.startDate = in.readString();
        this.md5 = in.readString();
        this.nameFiel = in.readString();
    }

    public static final Creator<DtoDownloadableFiles> CREATOR = new Creator<DtoDownloadableFiles>() {
        @Override
        public DtoDownloadableFiles createFromParcel(Parcel source) {
            return new DtoDownloadableFiles(source);
        }

        @Override
        public DtoDownloadableFiles[] newArray(int size) {
            return new DtoDownloadableFiles[size];
        }
    };
}
