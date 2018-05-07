package com.go_sharp.gomh.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.go_sharp.gomh.dto.DtoCatalog;
import com.go_sharp.gomh.dto.DtoTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 6/05/18.
 */

public class DaoTypePublicity  extends DAO {

    private SQLiteDatabase db;
    private Cursor cursor;
    public static String TABLE_NAME = "type_publicity";
    private final String ID_TYPE = "id_type";
    private final String VALUE = "value";
    public static String PK_FIELD = "id";

    public DaoTypePublicity(){
        super(TABLE_NAME, PK_FIELD);
    }
    /**
     * insert
     */
    public int insert(List<DtoCatalog> obj) {
        db = helper.getWritableDatabase();
        int resp = 0;
        try {
            SQLiteStatement inssStatement = db.compileStatement("INSERT INTO " + TABLE_NAME + " ("  + ID_TYPE + "," + VALUE + ") VALUES(?,?);");
            db.beginTransaction();
            for (DtoCatalog dto : obj) {

                try {
                    inssStatement.bindLong(1, dto.getId());
                } catch (Exception e) {
                    inssStatement.bindNull(1);
                }
                try {
                    inssStatement.bindString(2, dto.getValue());
                } catch (Exception e) {
                    inssStatement.bindNull(2);
                }

                inssStatement.executeInsert();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();
        return resp;
    }

    public List<DtoCatalog> select() {
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT\n"
                + "type_publicity.id_type,\n"
                + "type_publicity.value\n"
                + "FROM\n" + TABLE_NAME, null);
        List<DtoCatalog> obj = new ArrayList<>(cursor.getCount());
        DtoCatalog dto;
        if (cursor.moveToFirst()) {

            int idType = cursor.getColumnIndexOrThrow("id_type");
            int value = cursor.getColumnIndexOrThrow("value");

            do {
                dto = new DtoCatalog();
                dto.setId(cursor.getLong(idType));
                dto.setValue(cursor.getString(value));
                obj.add(dto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return obj;
    }


}
