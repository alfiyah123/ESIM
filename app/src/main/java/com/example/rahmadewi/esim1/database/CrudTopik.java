package com.example.rahmadewi.esim1.database;

import android.database.Cursor;

import com.example.rahmadewi.esim1.models.topik.ResultItem;

public class CrudTopik {
    public void insertData(ResultItem resultItem){
        String insertQuery = "INSERT INTO data_topik (id_topik, nama_topik)" +
                "VALUES(" +
                "'"+ Integer.parseInt(resultItem.getIdTopik()) + "'," +
                "'" + resultItem.getNamaTopik() + "')";
        DatabaseHelper.execute(insertQuery);
    }

    public int getMaxId(){
        int id_topik = 0;
        String queryTopik = "SELECT Max(id_topik) as id_topik FROM data_topik;";
        Cursor cTopik = DatabaseHelper.rawQuery(queryTopik);
        if (cTopik != null && cTopik.getCount() != 0) {
            if (cTopik.moveToFirst()) {
                do {
                    id_topik = cTopik.getInt(cTopik.getColumnIndex("id_topik"));
                } while (cTopik.moveToNext());
            }
        }
        return id_topik;
    }
}
