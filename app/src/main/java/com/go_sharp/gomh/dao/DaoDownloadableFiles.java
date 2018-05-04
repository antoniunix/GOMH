package com.go_sharp.gomh.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.go_sharp.gomh.dto.DtoDownloadableFiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gnu on 25/04/18.
 */

public class DaoDownloadableFiles extends DAO {

    private SQLiteDatabase db;
    private Cursor cursor;

    public static String TABLE_NAME = "download_file";
    public static String PK_FIELD = "id";


    private final String ID = "id";
    private final String EXT = "ext";
    private final String END_DATE = "end_date";
    private final String DESCRIPTION = "description";
    private final String TITLE = "title";
    private final String URL = "url";
    private final String START_DATE = "start_date";
    private final String MD5 = "md5";

    public DaoDownloadableFiles() {
        super(TABLE_NAME, PK_FIELD);
    }

    /**
     * insert
     */
    public int insert(List<DtoDownloadableFiles> obj) {
        db = helper.getWritableDatabase();
        int resp = 0;
        try {
            SQLiteStatement inssStatement = db.compileStatement("INSERT INTO "
                    + TABLE_NAME + " (" + EXT + "," + END_DATE + "," + DESCRIPTION + "," + TITLE + "," + URL + "," + START_DATE + "," + MD5
                    + ") VALUES(?,?,?,?,?,?,?);");
            db.beginTransaction();

            for (DtoDownloadableFiles dto : obj) {
                try {
                    inssStatement.bindString(1, dto.getExt());
                } catch (Exception e) {
                    inssStatement.bindNull(1);
                }
                try {
                    inssStatement.bindString(2, dto.getEndDate());
                } catch (Exception e) {
                    inssStatement.bindNull(2);
                }
                try {
                    inssStatement.bindString(3, dto.getDescription());
                } catch (Exception e) {
                    inssStatement.bindNull(3);
                }
                try {
                    inssStatement.bindString(4, dto.getTitle());
                } catch (Exception e) {
                    inssStatement.bindNull(4);
                }
                try {
                    inssStatement.bindString(5, dto.getUrl());
                } catch (Exception e) {
                    inssStatement.bindNull(5);
                }
                try {
                    inssStatement.bindString(6, dto.getStartDate());
                } catch (Exception e) {
                    inssStatement.bindNull(6);
                }
                try {
                    inssStatement.bindString(7, dto.getMd5());
                } catch (Exception e) {
                    inssStatement.bindNull(7);
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

    public List<DtoDownloadableFiles> select() {
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT\n" +
                "download_file.id,\n" +
                "download_file.ext,\n" +
                "download_file.end_date,\n" +
                "download_file.description,\n" +
                "download_file.title,\n" +
                "download_file.url,\n" +
                "download_file.start_date,\n" +
                "download_file.md5\n" +
                "FROM\n" +
                TABLE_NAME, null);

        List<DtoDownloadableFiles> obj = new ArrayList<>(cursor.getCount());
        DtoDownloadableFiles dto;

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndexOrThrow("id");
            int ext = cursor.getColumnIndexOrThrow("ext");
            int end_date = cursor.getColumnIndexOrThrow("end_date");
            int description = cursor.getColumnIndexOrThrow("description");
            int title = cursor.getColumnIndexOrThrow("title");
            int url = cursor.getColumnIndexOrThrow("url");
            int start_date = cursor.getColumnIndexOrThrow("start_date");
            int md5 = cursor.getColumnIndexOrThrow("md5");

            do {
                dto = new DtoDownloadableFiles();
                dto.setId(cursor.getLong(id));
                dto.setExt(cursor.getString(ext));
                dto.setEndDate(cursor.getString(end_date));
                dto.setDescription(cursor.getString(description));
                dto.setTitle(cursor.getString(title));
                dto.setUrl(cursor.getString(url));
                dto.setStartDate(cursor.getString(start_date));
                dto.setMd5(cursor.getString(md5));
                obj.add(dto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return obj;
    }
}
