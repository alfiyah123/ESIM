package com.example.rahmadewi.esim1.database;

import android.database.Cursor;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class CrudNilai {
    public void insertNilai(int nilai, String kategori){
        int id = 0;
        String query = "SELECT * FROM data_nilai";
        Cursor c1 = DatabaseHelper.rawQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    id = c1.getInt(c1.getColumnIndex("id_nilai")) + 1;
                    System.out.println("id : "+id);
                } while (c1.moveToNext());
            }
        }else{
            id = 1;
        }

        String insertQuery = "INSERT INTO data_nilai VALUES("
                + id + "," + "'"
                + kategori + "'," + "'"
                + nilai + "')";

        DatabaseHelper.execute(insertQuery);
    }

    public void getData(String kategori, ArrayList<Entry> yvalues, ArrayList<Integer> dataY, ArrayList<String> xVals1){
        int no = 0;
        String query = "SELECT * FROM data_nilai WHERE kategori='"+kategori+"'";
        Cursor c1 = DatabaseHelper.rawQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    yvalues.add(new Entry(c1.getInt(c1.getColumnIndex("nilai")),no));
                    dataY.add(c1.getInt(c1.getColumnIndex("nilai")));
                    xVals1.add(String.valueOf((no+1)));
                    no++;
                } while (c1.moveToNext());
            }
        }
    }
}
