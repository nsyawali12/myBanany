package com.example.nsyawalia.banany_v21;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

public class FtpDbHelper extends SQLiteOpenHelper{

    public static final String COLUMN_HOST_NAME = "hostName";
    public static final String COLUMN_LOCAL_DIR = "locatDirectory";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PORT = "port";
    public static final String COLUMN_REMOTE_DIR = "remoteDirectory";
    public static final String COLUMN_SERVER_NAME =  "serverName";
    public static final String COLUMN_USERNAME = "username";
    public static final String TABLE_NAME = "ftpSserver";

    public FtpDbHelper (Context context){
        super(context, TABLE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE ftpServer (serverName text primary key, hostName text, port integer, username text, password text, localDirectory text, remoteDirectory text)";
        Log.d("w4llsk1", "Create table: " + query);
        db.execSQL(query);
    }

    public long insertServer(String SERVER_NAME, String HOST_NAME, int PORT, String Username, String PASSWORD, String LOCAL_DIR, String REMOTE_DIR){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SERVER_NAME, "BANANY 2.0");
        contentValues.put(COLUMN_HOST_NAME, "192.168.4.1");
        contentValues.put(COLUMN_PORT, Integer.valueOf(21));
        contentValues.put(COLUMN_USERNAME, "pi");
        contentValues.put(COLUMN_PASSWORD. "banany");
        contentValues.put(COLUMN_LOCAL_DIR, "/storage/emulated/0/");
        contentValues.put(COLUMN_REMOTE_DIR, "/media/pi");
        Log.d("w4llsk1", "added row at position " + db.insert(TABLE_NAME, null, contentValues));
        return 1;
    }

    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void updateServer(String SERVER_NAME, String HOST_NAME, int PORT, String USERNAME, String PASSWORD, String LOCAL_DIR, String REMOTE_DIR){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HOST_NAME, HOST_NAME);
        contentValues.put(COLUMN_PORT, Integer.valueOf(PORT));
        contentValues.put(COLUMN_USERNAME, USERNAME);
        contentValues.put(COLUMN_PASSWORD, PASSWORD);
        contentValues.put(COLUMN_LOCAL_DIR, LOCAL_DIR);
        contentValues.put(COLUMN_REMOTE_DIR, REMOTE_DIR);
        Log.d("w4llsk1", "updated row at position " + ((long) db.update(TABLE_NAME, contentValues, "server_name = ? ", new String[]{SERVER_NAME})));
        return true;
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS ftpServer");
        onCreate(db);
    }

    public Cursor getServerDetails(String SERVER_NAME){
        return getReadableDatabase().rawQuery("select * from ftp_server where server_name = \"" + SERVER_NAME + "\"", null);
    }

    public ArrayList<String> getAllServers(){
        ArrayList<String> array_list = new ArrayList();
        Cursor res = getReadableDatabase().rawQuery("Select * from ftp_server", null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_SERVER_NAME)));
            res.moveToNext();
        }
        return array_list;
    }



}
