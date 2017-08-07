package com.example.rahmadewi.esim1.database;

import android.database.Cursor;

import com.example.rahmadewi.esim1.models.rambu.ResultItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CrudRambu {
    public void insertData(ResultItem resultItem){
        String insertQuery = "INSERT INTO data_rambu (id_rambu, kategori, topik, gambar, arti)" +
                "VALUES(" +
                "'"+ Integer.parseInt(resultItem.getIdRambu()) + "'," +
                "'"+ resultItem.getKategori() + "'," +
                "'"+ resultItem.getTopik() + "'," +
                "'"+ resultItem.getGambar() + "'," +
                "'"+ resultItem.getArti() + "')";
        DatabaseHelper.execute(insertQuery);
    }

    public HashMap<String, List<String>> getDataRambu(HashMap<String, List<String>> data){
        List<String> sub_topik = new ArrayList<>();
        String query2 = "SELECT DISTINCT topik FROM data_rambu";
        Cursor c = DatabaseHelper.rawQuery(query2);
        if (c != null && c.getCount() != 0) {
            if (c.moveToFirst()) {
                do {
                    sub_topik.add(c.getString(c.getColumnIndex("topik")));
                    System.out.println("sub_topik 1" + c.getString(c.getColumnIndex("topik")));
                } while (c.moveToNext());
            }
        }
        data.put("Rambu Lalu Lintas", sub_topik);
        return data;
    }

    public List<ResultItem> getData(String item, int PAGE_SIZE, List<ResultItem> result) {
        String query = "SELECT * FROM data_rambu WHERE topik='"+item+"' LIMIT "+PAGE_SIZE+";";
        Cursor c1 = DatabaseHelper.rawQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    ResultItem ci = new ResultItem();
                    ci.setArti(c1.getString(c1.getColumnIndex("arti")));
                    ci.setGambar(c1.getString(c1.getColumnIndex("gambar")));
                    ci.setIdRambu(c1.getString(c1.getColumnIndex("id_rambu")));

                    result.add(ci);
                } while (c1.moveToNext());
            }
        }
        return result;
    }

    public List<ResultItem> getDataNext(String item, int index, int PAGE_SIZE, List<ResultItem> result){
        String query = "SELECT * FROM data_rambu WHERE topik='"+item+"' AND id_rambu > "+index+" LIMIT "+PAGE_SIZE+";";
        Cursor c1 = DatabaseHelper.rawQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    ResultItem ci = new ResultItem();
                    ci.setArti(c1.getString(c1.getColumnIndex("arti")));
                    ci.setGambar(c1.getString(c1.getColumnIndex("gambar")));
                    ci.setIdRambu(c1.getString(c1.getColumnIndex("id_rambu")));

                    result.add(ci);
                } while (c1.moveToNext());
            }
        }
        return result;
    }

    public int getMaxId(){
        int id_rambu = 0;
        String queryRambu = "SELECT Max(id_rambu)as id_rambu FROM data_rambu;";
        Cursor cRambu = DatabaseHelper.rawQuery(queryRambu);
        if (cRambu != null && cRambu.getCount() != 0) {
            if (cRambu.moveToFirst()) {
                do {
                    id_rambu = cRambu.getInt(cRambu.getColumnIndex("id_rambu"));
                } while (cRambu.moveToNext());
            }
        }
        return id_rambu;
    }
}

