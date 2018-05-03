package com.go_sharp.gomh.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class DtoBundle implements Parcelable {

    private long idPdv;
    private long idReportLocal;

    public long getIdPdv() {
        return idPdv;
    }

    public DtoBundle setIdPdv(long idPdv) {
        this.idPdv = idPdv;
        return this;
    }

    public long getIdReportLocal() {
        return idReportLocal;
    }

    public DtoBundle setIdReportLocal(long idReportLocal) {
        this.idReportLocal = idReportLocal;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.idPdv);
        dest.writeLong(this.idReportLocal);
    }

    public DtoBundle() {
    }

    protected DtoBundle(Parcel in) {
        this.idPdv = in.readLong();
        this.idReportLocal = in.readLong();
    }

    public static final Parcelable.Creator<DtoBundle> CREATOR = new Parcelable.Creator<DtoBundle>() {
        @Override
        public DtoBundle createFromParcel(Parcel source) {
            return new DtoBundle(source);
        }

        @Override
        public DtoBundle[] newArray(int size) {
            return new DtoBundle[size];
        }
    };
}