package com.example.rahmadewi.esim1.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Rahmadewi on 3/5/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static SQLiteDatabase sqliteDb;
    private static DatabaseHelper instance;
    private static final int DATABASE_VERSION = 1;

    private Context context;
    static Cursor cursor = null;

    DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    private static void initialize(Context context, String databaseName){
        if(instance == null){
            if(!checkDatabase(context, databaseName)){
                try {
                    copyDatabase(context, databaseName);
                }catch (IOException e){
                    System.out.println(databaseName + " does not exists");
                }
            }
            instance = new DatabaseHelper(context, databaseName, null, DATABASE_VERSION);
            sqliteDb = instance.getWritableDatabase();

            System.out.println("instance of "+databaseName+" created");
        }
    }

    public SQLiteDatabase getDatabase(){
        return sqliteDb;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void copyDatabase(Context aContext, String databaseName) throws IOException{
        InputStream myInput = aContext.getAssets().open(databaseName);
        String outFileName = getDatabasePath(aContext, databaseName);

        File f = new File("/data/data/" + aContext.getPackageName() + "/databases/");
        if(!f.exists()){
            f.mkdir();
        }
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

        System.out.println(databaseName + " copied");
    }

    public static boolean checkDatabase(Context aContext, String databaseName){
        SQLiteDatabase checkDB = null;

        try {
            String myPath = getDatabasePath(aContext, databaseName);
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        }catch (SQLException e){
            System.out.println(databaseName + "does not exits");
        }
        return checkDB != null ? true : false;
    }

    private static String getDatabasePath(Context aContext, String databaseName){
        return "/data/data/"+aContext.getPackageName()+"/databases/"+databaseName;
    }

    public static Cursor rawQuery(String query){
        try{
            if(sqliteDb.isOpen()){
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            cursor = null;
            cursor = sqliteDb.rawQuery(query, null);
        }catch (Exception e){
            System.out.println("DB ERROR "+e.getMessage());
            e.printStackTrace();
        }
        return cursor;
    }

    public static void execute(String query){
        try{
            if(sqliteDb.isOpen()){
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();
            sqliteDb.execSQL(query);
            System.out.println("sukses " + query);
        }catch (Exception e){
            System.out.println("DB ERROR " +e.getMessage());
            e.printStackTrace();
        }
    }

    public static final DatabaseHelper getInstance(Context context,
                                                   String databaseName) {
        initialize(context, databaseName);
        return instance;
    }
}
