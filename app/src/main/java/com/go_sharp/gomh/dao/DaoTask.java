package com.go_sharp.gomh.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.go_sharp.gomh.dto.DtoTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gnu on 4/05/18.
 */

public class DaoTask extends DAO {
    private SQLiteDatabase db;
    private Cursor cursor;
    public static String TABLE_NAME = "message";
    public static String PK_FIELD = "id";
    private final String ID = "id";
    private final String ID_TYPE = "id_type";
    private final String DESCRIPTION = "description";
    private final String TITLE = "title";
    private final String CONTENT = "content";

    public DaoTask() {
        super(TABLE_NAME, PK_FIELD);
    }

    /**
     * insert
     */
    public int insert(List<DtoTask> obj) {
        db = helper.getWritableDatabase();
        int resp = 0;
        try {
            SQLiteStatement inssStatement = db.compileStatement("INSERT INTO " + TABLE_NAME + " (" + ID + "," + ID_TYPE + "," + DESCRIPTION + "," + TITLE + "," + CONTENT + ") VALUES(?,?,?,?,?);");
            db.beginTransaction();
            for (DtoTask dto : obj) {

                try {
                    inssStatement.bindLong(1, dto.getId());
                } catch (Exception e) {
                    inssStatement.bindNull(1);
                }
                try {
                    inssStatement.bindLong(2, dto.getType_id());
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
                    inssStatement.bindString(5, dto.getContent());
                } catch (Exception e) {
                    inssStatement.bindNull(5);
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

    public List<DtoTask> select() {
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT\n"
                + "message.id,\n"
                + "message.id_type,\n"
                + "message.description,\n"
                + "message.title,\n"
                + "message.content\n"
                + "FROM\n" + TABLE_NAME, null);
        List<DtoTask> obj = new ArrayList<>(cursor.getCount());
        DtoTask dto;
        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndexOrThrow("id");
            int idType = cursor.getColumnIndexOrThrow("id_type");
            int description = cursor.getColumnIndexOrThrow("description");
            int title = cursor.getColumnIndexOrThrow("title");
            int content = cursor.getColumnIndexOrThrow("content");
            do {
                dto = new DtoTask();
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

    public DtoTask selectById(long idTask) {
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT\n"
                + "message.id,\n"
                + "message.id_type,\n"
                + "message.description,\n"
                + "message.title,\n"
                + "message.content\n"
                + "FROM\n" + TABLE_NAME + " WHERE message.id = " + idTask, null);
        DtoTask dto = new DtoTask();
        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndexOrThrow("id");
            int idType = cursor.getColumnIndexOrThrow("id_type");
            int description = cursor.getColumnIndexOrThrow("description");
            int title = cursor.getColumnIndexOrThrow("title");
            int content = cursor.getColumnIndexOrThrow("content");

            dto.setId(cursor.getLong(id));
            dto.setType_id(cursor.getLong(idType));
            dto.setDescription(cursor.getString(description));
            dto.setTitle(cursor.getString(title));
            dto.setContent(cursor.getString(content));
        }
        cursor.close();
        db.close();
        return dto;
    }
}
