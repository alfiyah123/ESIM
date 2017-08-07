package com.example.rahmadewi.esim1.database;

import android.database.Cursor;

import com.example.rahmadewi.esim1.feature.artikel.ArtikelActivity;
import com.example.rahmadewi.esim1.models.materi.ResultItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CrudMateri {

    public void insertData(ResultItem resultItem){
        String insertQuery = "INSERT INTO data_materi (id_materi, kategori, topik, sub_topik, materi)" +
                "VALUES(" +
                "'"+ Integer.parseInt(resultItem.getIdMateri()) + "'," +
                "'"+ resultItem.getKategori() + "'," +
                "'"+ resultItem.getTopik() + "'," +
                "'"+ resultItem.getSubTopik() + "'," +
                "'"+ resultItem.getMateri() + "')";
        DatabaseHelper.execute(insertQuery);
    }

    public HashMap<String, List<String>> getDataMateri(HashMap<String, List<String>> data, String kategori){
        List<String> sub_topik = new ArrayList<>();
        String topik;

        String query = "SELECT DISTINCT topik FROM data_materi WHERE kategori='"+kategori+"' OR kategori='UMUM'";
        Cursor c1 = DatabaseHelper.rawQuery(query);
        if(c1 != null && c1.getCount() != 0){
            if(c1.moveToFirst()){
                do {
                    topik = c1.getString(c1.getColumnIndex("topik"));
                    String query1 = "SELECT * FROM data_materi WHERE topik='" + topik + "' AND kategori='" + kategori + "' OR kategori='UMUM'";
                    Cursor c2 = DatabaseHelper.rawQuery(query1);
                    if (c2 != null && c2.getCount() != 0) {
                        if (c2.moveToFirst()) {
                            do {
                                sub_topik.add(c2.getString(c2.getColumnIndex("sub_topik")));
                            } while (c2.moveToNext());
                        }
                    }
                    String[] a = new String[sub_topik.size()];
                    for(int k = 0; k<a.length; k++){
                        a[k] = sub_topik.get(k);
                    }
                    data.put(topik, Arrays.asList(a));
                    sub_topik.clear();
                }while(c1.moveToNext());
            }
        }
        return data;
    }

    public ResultItem getDataBySubTopik(String sub_topik){
        ResultItem resultItem = new ResultItem();
        String query = "SELECT * FROM data_materi where sub_topik='"+sub_topik+"'";
        Cursor c1 = DatabaseHelper.rawQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    resultItem.setSubTopik(c1.getString(c1.getColumnIndex("sub_topik")));
                    resultItem.setMateri(c1.getString(c1.getColumnIndex("materi")));
                } while (c1.moveToNext());
            }
        }
        return resultItem;
    }

    public ArrayList<Integer> getMaxId(String[] kategori){
        ArrayList<Integer> data = new ArrayList<>();
        for (String aKategori : kategori) {
            String queryMateri = "SELECT Max(id_materi)as id_materi FROM data_materi WHERE kategori='" + aKategori + "';";
            Cursor cMateri = DatabaseHelper.rawQuery(queryMateri);
            if (cMateri != null && cMateri.getCount() != 0) {
                if (cMateri.moveToFirst()) {
                    do {
                        data.add(cMateri.getInt(cMateri.getColumnIndex("id_materi")));
                    } while (cMateri.moveToNext());
                }
            }
        }
        return data;
    }
}
