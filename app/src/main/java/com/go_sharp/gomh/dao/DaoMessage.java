package com.go_sharp.gomh.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.go_sharp.gomh.dto.DtoDownloadableFiles;
import com.go_sharp.gomh.dto.DtoMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gnu on 4/05/18.
 */

public class DaoMessage extends DAO {

    private SQLiteDatabase db;
    private Cursor cursor;

    public static String TABLE_NAME = "message";
    public static String PK_FIELD = "id";

    private final String ID = "id";
    private final String ID_TYPE = "id_type";
    private final String DESCRIPTION = "description";
    private final String TITLE = "title";
    private final String CONTENT = "content";

    public DaoMessage() {
        super(TABLE_NAME, PK_FIELD);
    }

    /**
     * insert
     */
    public int insert(List<DtoMessage> obj) {
        db = helper.getWritableDatabase();
        int resp = 0;
        try {
            SQLiteStatement inssStatement = db.compileStatement("INSERT INTO "
                    + TABLE_NAME + " (" + ID_TYPE + "," + DESCRIPTION + "," + TITLE + "," + CONTENT
                    + ") VALUES(?,?,?,?);");
            db.beginTransaction();

            for (DtoMessage dto : obj) {
                try {
                    inssStatement.bindLong(1, dto.getType_id());
                } catch (Exception e) {
                    inssStatement.bindNull(1);
                }
                try {
                    inssStatement.bindString(2, dto.getDescription());
                } catch (Exception e) {
                    inssStatement.bindNull(2);
                }
                try {
                    inssStatement.bindString(3, dto.getTitle());
                } catch (Exception e) {
                    inssStatement.bindNull(3);
                }
                try {
                    inssStatement.bindString(4, dto.getContent());
                } catch (Exception e) {
                    inssStatement.bindNull(4);
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

    public List<DtoMessage> select() {
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT\n" +
                "message.id,\n" +
                "message.id_type,\n" +
                "message.description,\n" +
                "message.title,\n" +
                "message.content,\n" +
                "FROM\n" +
                TABLE_NAME, null);

        List<DtoMessage> obj = new ArrayList<>(cursor.getCount());
        DtoMessage dto;

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndexOrThrow("id");
            int idType = cursor.getColumnIndexOrThrow("id_type");
            int description = cursor.getColumnIndexOrThrow("description");
            int title = cursor.getColumnIndexOrThrow("title");
            int content = cursor.getColumnIndexOrThrow("content");

            do {
                dto = new DtoMessage();
                dto.setId(cursor.getLong(id));
                dto.setType_id(cursor.getLong(idType));
                dto.setDescription(cursor.getString(description));
                dto.setTitle(cursor.getString(title));
                dto.setContent(cursor.getString(content));
                obj.add(dto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return obj;
    }
}
