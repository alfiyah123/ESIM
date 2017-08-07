package com.example.rahmadewi.esim1.database;

import android.database.Cursor;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class CrudKesalahan {

    public void selectKesalahan(String kategori, String topik){
        System.out.println("kategori : "+kategori+" | topik : "+topik);
        Cursor cc1;
        String query2 = "SELECT * FROM data_kesalahan where kategori='"+kategori+"' AND topik='"+topik+"';";
        cc1 = DatabaseHelper.rawQuery(query2);
        if (cc1 != null && cc1.getCount() != 0) {
            if (cc1.moveToFirst()) {
                do {
                    int idd = cc1.getInt(cc1.getColumnIndex("id_salah"));
                    int tngSalah = cc1.getInt(cc1.getColumnIndex("jumlah")) + 1;

                    String updateQuery = "UPDATE data_kesalahan SET jumlah='" + tngSalah + "' WHERE id_salah=" + idd + ";";
                    DatabaseHelper.execute(updateQuery);
                } while (cc1.moveToNext());
            }
            cc1.close();
        }else{
            int tngSalah = 1;
            Cursor cc2;
            int id = 1;
            String query3 = "SELECT * FROM data_kesalahan;";
            cc2 = DatabaseHelper.rawQuery(query3);
            if (cc2 != null && cc2.getCount() != 0) {
                if (cc2.moveToFirst()) {
                    do{
                        id = cc2.getInt(cc2.getColumnIndex("id_salah")) + 1;
                    }while(cc2.moveToNext());
                }
                cc2.close();
            }else{
                id = 1;
            }
            String insertQuery = "INSERT INTO data_kesalahan VALUES("
                    + id + "," + "'"
                    + kategori + "'," + "'"
                    + topik + "'," +
                    + tngSalah + ")";

            DatabaseHelper.execute(insertQuery);
        }
    }

    public void getDataKesalahan(String kategori, ArrayList<Entry> yvaluesKesalahan, ArrayList<String> xValsKesalahan){
        int jumlah = 0;
        List<Integer> a = new ArrayList<>();
        String query = "SELECT * FROM data_kesalahan WHERE kategori='"+kategori+"'";
        Cursor c1 = DatabaseHelper.rawQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    a.add(c1.getInt(c1.getColumnIndex("jumlah")));
                    jumlah = jumlah + (c1.getInt(c1.getColumnIndex("jumlah")));
                    xValsKesalahan.add(c1.getString(c1.getColumnIndex("topik")));
                } while (c1.moveToNext());
            }
        }
        for(int i=0; i<a.size(); i++){
            float hasil = ((float)a.get(i)/jumlah) * 100;
            System.out.println("aaaaa : "+(float)a.get(i)/jumlah);
            yvaluesKesalahan.add(new Entry(hasil,i));
        }
    }

}
