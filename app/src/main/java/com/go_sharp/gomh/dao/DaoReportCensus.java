package com.go_sharp.gomh.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gshp.api.utils.Crypto;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.contextApp.ContextApp;
import com.go_sharp.gomh.dto.DtoReportCensus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 17/02/18.
 */

public class DaoReportCensus extends DAO {

    private SQLiteDatabase db;
    private Cursor cursor;
    public static String TABLE_NAME = "report_census";

    public static String PK_FIELD = "id";
    private final String ID = "id";
    private final String IDREPORTLOCAL = "id_report_local";
    private final String STATE = "state";
    private final String TOWN = "town";
    private final String SUBURB = "suburb";
    private final String ADDRESS = "address";
    private final String EXTERNAL_NUMBER = "external_number";
    private final String LAT = "lat";
    private final String LON = "lon";
    private final String HASH = "hash";
    private final String SEND = "send";
    private final String PROVIDER = "provider";
    private final String INTERNAL_NUMBER = "internal_number";
    private final String ADDRESSLEFT = "address_left";
    private final String ADDRESSRIGHT = "address_right";
    private final String CP = "cp";
    private final String NUMBERPHONE = "number_phone";
    private final String EMAIL = "email";
    private final String PATH = "path";
    private final String IDPUBLICITY = "id_publicity";

    public DaoReportCensus() {
        super(TABLE_NAME, PK_FIELD);
    }

    /**
     * INSERT
     */

    public int insert(DtoReportCensus obj) {
        db = helper.getWritableDatabase();
        ContentValues cv;
        int resp = 0;
        try {
            db.beginTransaction();

            cv = new ContentValues();
            cv.put(IDREPORTLOCAL, obj.getIdReporteLocal());
            cv.put(STATE, obj.getState());
            cv.put(TOWN, obj.getTown());
            cv.put(SUBURB, obj.getSuburb());
            cv.put(ADDRESS, obj.getAddress());
            cv.put(CP, obj.getCp());
            cv.put(LAT, obj.getLat());
            cv.put(LON, obj.getLon());
            cv.put(HASH, Crypto.MD5(System.currentTimeMillis() + obj.getIdReporteLocal() + " " + Math.random()));
            cv.put(SEND, obj.getSend());
            cv.put(PROVIDER, obj.getProvider());
            cv.put(INTERNAL_NUMBER, obj.getInternalNumber());
            cv.put(EXTERNAL_NUMBER, obj.getExternalNumber());
            cv.put(ADDRESSLEFT, obj.getAddress_left());
            cv.put(ADDRESSRIGHT, obj.getAddress_right());
            cv.put(EMAIL, obj.getEmail());
            cv.put(NUMBERPHONE, obj.getNumber_phone());
            cv.put(PATH, obj.getPath());
            cv.put(IDPUBLICITY, obj.getIdPublicity());
            resp = (int) db.insert(TABLE_NAME, null, cv);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();
        return resp;
    }

    public List<List<DtoReportCensus>> selectToSend() {
        db = helper.getWritableDatabase();
        String qry = "SELECT DISTINCT\n" +
                "report_census.id_report_local,\n" +
                "report_census.lat,\n" +
                "report_census.lon,\n" +
                "report_census.suburb,\n" +
                "report_census.town,\n" +
                "report_census.state,\n" +
                "report_census.cp,\n" +
                "report_census.external_number,\n" +
                "report_census.internal_number,\n" +
                "report_census.address,\n" +
                "report_census.address_left,\n" +
                "report_census.address_right,\n" +
                "report_census.id_publicity,\n" +
                "report_census.provider,\n" +
                "report_census.hash,\n" +
                "report.id_report_server\n" +
                "FROM\n" +
                "report_census\n" +
                "INNER JOIN report ON report.id=report_census.id_report_local AND report.id_report_server>0\n" +
                "WHERE report_census.send=0\n" +
                "ORDER BY report_census.id_report_local ASC";
        cursor = db.rawQuery(qry, null);
        List<List<DtoReportCensus>> lst = new ArrayList<>();
        List<DtoReportCensus> subLst = new ArrayList<>();
        DtoReportCensus catalogo;
        long tmpidReport;

        if (cursor.moveToFirst()) {
            tmpidReport = cursor.getLong(cursor.getColumnIndexOrThrow("id_report_local"));
            do {
                catalogo = new DtoReportCensus();
                catalogo.setIdReport(cursor.getInt(cursor.getColumnIndexOrThrow("id_report_server")));
                catalogo.setState(cursor.getString(cursor.getColumnIndexOrThrow(STATE)));
                catalogo.setSuburb(cursor.getString(cursor.getColumnIndexOrThrow(SUBURB)));
                catalogo.setTown(cursor.getString(cursor.getColumnIndexOrThrow(TOWN)));
                catalogo.setLat(cursor.getDouble(cursor.getColumnIndexOrThrow(LAT)));
                catalogo.setLon(cursor.getDouble(cursor.getColumnIndexOrThrow(LON)));
                catalogo.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS)));
                catalogo.setAddress_right(cursor.getString(cursor.getColumnIndexOrThrow(ADDRESSRIGHT)));
                catalogo.setAddress_left(cursor.getString(cursor.getColumnIndexOrThrow(ADDRESSLEFT)));
                catalogo.setCp(cursor.getString(cursor.getColumnIndexOrThrow(CP)));
                catalogo.setProvider(cursor.getString(cursor.getColumnIndexOrThrow(PROVIDER)));
                catalogo.setHash(cursor.getString(cursor.getColumnIndexOrThrow(HASH)));
                catalogo.setInternalNumber(cursor.getString(cursor.getColumnIndexOrThrow(INTERNAL_NUMBER)));
                catalogo.setExternalNumber(cursor.getString(cursor.getColumnIndexOrThrow(EXTERNAL_NUMBER)));
                catalogo.setIdReporteLocal(cursor.getLong(cursor.getColumnIndexOrThrow(IDREPORTLOCAL)));
                catalogo.setIdPublicity(cursor.getLong(cursor.getColumnIndexOrThrow(IDPUBLICITY)));
                if (tmpidReport == cursor.getInt(cursor.getColumnIndexOrThrow(IDREPORTLOCAL))) {
                    subLst.add(catalogo);
                } else if (tmpidReport != cursor.getInt(cursor.getColumnIndexOrThrow(IDREPORTLOCAL))) {
                    tmpidReport = cursor.getInt(cursor.getColumnIndexOrThrow(IDREPORTLOCAL));
                    lst.add(subLst);
                    subLst = new ArrayList<>();
                    subLst.add(catalogo);

                }

                if (cursor.isLast())
                    lst.add(subLst);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lst;
    }

    public void updateSend(String idReporteLocal) {
        db = helper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("send", 1);
            db.update(TABLE_NAME, cv, IDREPORTLOCAL + "=" + idReporteLocal, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }


    public void deleteByIdReport(long idReporteLocal) {
        int resp = 0;
        try {
            db = helper.getWritableDatabase();
            resp = db.delete(TABLE_NAME, IDREPORTLOCAL + "=?", new String[]{String.valueOf(idReporteLocal)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public String getAddress(long idReportLocal) {
        db = helper.getWritableDatabase();
        String qry = "Select \n" +
                "report_census.address\n" +
                "FROM\n" +
                TABLE_NAME + "\n"
                + "WHERE " + IDREPORTLOCAL + "=" + idReportLocal;
        cursor = db.rawQuery(qry, null);
        String address = "";
        if (cursor.moveToFirst()) {
            address = cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS));
        }
        cursor.close();
        db.close();
        return address;
    }

    public boolean isCompleteReportCensus(long idReport) {
        boolean isReportSupervisor = false;
        db = helper.getReadableDatabase();
        String qry = "SELECT\n" +
                "COUNT(*) count\n" +
                "FROM\n" +
                "report_census\n" +
                "INNER JOIN report ON report.id= report_census.id_report_local \n" +
                "WHERE report_census.id_report_local=" + idReport;
        cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getColumnIndexOrThrow("count");

            isReportSupervisor = cursor.getInt(count) > 0;

        }
        cursor.close();
        db.close();
        return isReportSupervisor;

    }

    public List<DtoReportCensus> selecttoSendPhoto() {
        db = helper.getReadableDatabase();
        String qry = "SELECT DISTINCT\n" +
                "report_census.id,\n" +
                "report_census.id_report_local,\n" +
                "report_census.hash,\n" +
                "report_census.send,\n" +
                "report.id_report_server,\n" +
                "report_census.path,\n" +
                "report.id_report_server,\n" +
                "report.id_pdv\n" +
                "FROM\n" +
                "report_census\n" +
                "INNER JOIN report ON report.id = report_census.id_report_local AND report.id_report_server>0\n" +
                "WHERE report_census.send = 1 \n" +
                "ORDER BY report_census.id_report_local ASC";

        cursor = db.rawQuery(qry, null);
        List<DtoReportCensus> obj = new ArrayList<>();
        DtoReportCensus catalogo;
        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndexOrThrow("id");
            int id_report_local = cursor.getColumnIndexOrThrow("id_report_local");
            int id_report_server = cursor.getColumnIndexOrThrow("id_report_server");
            int path = cursor.getColumnIndexOrThrow("path");
            int hash = cursor.getColumnIndexOrThrow("hash");
            int id_pdv = cursor.getColumnIndexOrThrow("id_pdv");

            do {
                catalogo = new DtoReportCensus();
                catalogo.setIdReporteLocal(cursor.getInt(id_report_local));
                catalogo.setId(cursor.getInt(id));
                catalogo.setHash(cursor.getString(hash));
                catalogo.setIdReport(cursor.getInt(id_report_server));
                catalogo.setPath(cursor.getString(path));
                catalogo.setPlaceId(1);
                catalogo.setUserId("@user");
                catalogo.setTableName("report_pdv_censo");
                catalogo.setVersion(1);
                catalogo.setPersonId("@person");
                catalogo.setDescription("<Description>");

                if (new File(catalogo.getPath()).exists()) {
                    String md5 = null;

                    md5 = Crypto.MD5CheckSum(catalogo.getPath());

                    catalogo.setMd5(md5);
                    obj.add(catalogo);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return obj;
    }

    public void updateSendPhoto(String id) {
        db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("send", 2);
        db.update(TABLE_NAME, cv, "id=" + id, null);
        db.close();
    }
}
