package com.example.quanganhpham.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by QuangAnhPham on 3/9/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String KEY_ID_NOTE = "id";
    public static final String KEY_TITLE_NOTE = "title";
    public static final String KEY_CONTENT_NOTE = "content";
    public static final String DATABASE_NAME = "note.db";
    public static final int DATA_VERSION = 1;
    public static final String DATABASE_TABLE = "tb_note";
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(" CREATE TABLE " + DATABASE_TABLE + "(" +
                    KEY_ID_NOTE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    KEY_TITLE_NOTE + " TEXT NOT NULL," +
                    KEY_CONTENT_NOTE + " TEXT NOT NULL);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (db != null && db.isOpen()) {
            try {
                db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cursor getAll(String sql) {
        open();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        close();
        return cursor;
    }

    public long insert(String table, ContentValues values) {
        open();
        long index = db.insert(table, null, values);
        close();
        return index;
    }

    public boolean update(String table, ContentValues values, String where) {
        open();
        long index = db.update(table, values, where, null);
        close();
        return index > 0;
    }

    public boolean delete(String table, String where) {
        open();
        long index = db.delete(table, where, null);
        close();
        return index > 0;
    }

    public Note getNote(String sql) {
        Note note = null;
        Cursor cursor = getAll(sql);
        if (cursor != null) {
            note = cursorToNote(cursor);
            cursor.close();
        }
        return note;
    }

    public ArrayList<Note> getListNote(String sql) {
        ArrayList<Note> list = new ArrayList<>();
        Cursor cursor = getAll(sql);
        while (!cursor.isAfterLast()) {
            list.add(cursorToNote(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public long insertNote(Note note) {
        return insert(DATABASE_TABLE, noteToValues(note));
    }

    public boolean updateNote(Note note) {
        return update(DATABASE_TABLE, noteToValues(note), KEY_ID_NOTE + " = " + note.getId());
    }

    public boolean deleteNote(String where) {
        return delete(DATABASE_TABLE, where);
    }

    private ContentValues noteToValues(Note note) {
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE_NOTE, note.getTitle());
        values.put(KEY_CONTENT_NOTE, note.getContent());
        return values;
    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getInt(cursor.getColumnIndex(KEY_TITLE_NOTE))).setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE_NOTE))).setContent(cursor.getString(cursor.getColumnIndex(KEY_CONTENT_NOTE)));
        return note;
    }


}
