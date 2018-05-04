package com.go_sharp.gomh.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.go_sharp.gomh.dto.DtoMessage;

import java.util.List;

public class DaoMessage extends DAO {

    private SQLiteDatabase db;
    private Cursor cursor;

    public static String TABLE_NAME = "message";
    public static String PK_FIELD = "id";

    private final String ID = "id";
    private final String TYPE_ID = "type_id";
    private final String DESCRIPTION = "description";
    private final String TITLE = "title";
    private final String CONTENT = "content";
    private final String SEEN = "seen";
    private final String SENT = "sent";
    private final String TIMESTAMP = "timestampCel";
    private final String HASH = "hash";

    public DaoMessage() {
        super(TABLE_NAME, PK_FIELD);
    }

    public int insert(List<DtoMessage> obj) {
        db = helper.getWritableDatabase();
        int resp = 0;
        try {
            SQLiteStatement insStmnt = db.compileStatement("" + "INSERT INTO "
                    + TABLE_NAME + " (" + ID + "," + TYPE_ID + "," + DESCRIPTION + "," + TITLE
                    + "," + CONTENT + "," + SEEN + ") VALUES(?,?,?,?,?,?);");
            db.beginTransaction();

            for (DtoMessage message : obj) {
                try {
                    insStmnt.bindLong(1, message.getId());
                } catch (Exception e) {
                    insStmnt.bindNull(1);
                }
                try {
                    insStmnt.bindLong(2, message.getType_id());
                } catch (Exception e) {
                    insStmnt.bindNull(2);
                }
                try {
                    insStmnt.bindString(3, message.getDescription());
                } catch (Exception e) {
                    insStmnt.bindNull(3);
                }
                try {
                    insStmnt.bindString(4, message.getTitle());
                } catch (Exception e) {
                    insStmnt.bindNull(4);
                }
                try {
                    insStmnt.bindString(5, message.getContent());
                } catch (Exception e) {
                    insStmnt.bindNull(5);
                }
                try {
                    insStmnt.bindLong(6, message.getSeen());
                } catch (Exception e) {
                    insStmnt.bindNull(6);
                }
                insStmnt.executeInsert();
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

    public void updateSeen(long id, String time, String hash) {
        db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SEEN, 1);
        cv.put(TIMESTAMP, time);
        cv.put(HASH, hash);
        db.update(TABLE_NAME, cv, ID + "=" + id, null);
        db.close();
    }

    public void updateSent(long id) {
        db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SENT, 1);
        db.update(TABLE_NAME, cv, ID + "=" + id, null);
        db.close();
    }

    public DtoMessage selectMsg(long id) {
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT\n" +
                TYPE_ID + ",\n" +
                DESCRIPTION + ",\n" +
                TITLE + ",\n" +
                CONTENT + "\n" +
                "FROM\n" +
                TABLE_NAME + "\n" +
                "WHERE " + ID + " =" + id, null);
        DtoMessage message = new DtoMessage();

        if (cursor.moveToFirst()) {
            int type_id = cursor.getColumnIndexOrThrow(TYPE_ID);
            int description = cursor.getColumnIndexOrThrow(DESCRIPTION);
            int title = cursor.getColumnIndexOrThrow(TITLE);
            int content = cursor.getColumnIndexOrThrow(CONTENT);

            do {
                message.setId(id);
                message.setType_id(cursor.getInt(type_id));
                message.setDescription(cursor.getString(description));
                message.setTitle(cursor.getString(title));
                message.setContent(cursor.getString(content));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return message;
    }
}
