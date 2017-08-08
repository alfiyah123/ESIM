package com.example.rahmadewi.esim1.database;

import android.database.Cursor;

import com.example.rahmadewi.esim1.models.soal.ResultItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrudSoal {

    public void insertData(ResultItem resultItem){
        String insertQuery = "INSERT INTO data_soal (id_soal, kategori, topik, soal, opsi_1, opsi_2, opsi_3, jawaban, gambar)" +
                "VALUES(" +
                "'"+ Integer.parseInt(resultItem.getIdSoal()) + "'," +
                "'"+ resultItem.getKategori() + "'," +
                "'"+ resultItem.getTopik() + "'," +
                "'"+ resultItem.getSoal() + "'," +
                "'"+ resultItem.getOpsi1() + "'," +
                "'"+ resultItem.getOpsi2() + "'," +
                "'"+ resultItem.getOpsi3() + "'," +
                "'"+ resultItem.getJawaban() + "'," +
                "'" + resultItem.getGambar() + "')";
        DatabaseHelper.execute(insertQuery);
    }

    public List<Integer> getDataRandomSimulasi(String topik, String kategori, int jumlah, List<Integer> id_soal){
        Cursor c2;
        int i=0;
        while(i < jumlah) {
            String query = "SELECT DISTINCT id_soal FROM data_soal where (kategori='UMUM' OR kategori='" + kategori + "')" +
                    " AND topik='" + topik + "' ORDER BY RANDOM() LIMIT 1";
            c2 = DatabaseHelper.rawQuery(query);
            if (c2 != null && c2.getCount() != 0) {
                if (c2.moveToFirst()) {
                    do {
                        if (!id_soal.contains(c2.getInt(c2.getColumnIndex("id_soal")))){
                            id_soal.add(c2.getInt(c2.getColumnIndex("id_soal")));
                            i++;
                        }
                    } while (c2.moveToNext());
                }
                c2.close();
            }
        }
        return id_soal;
    }

    public ResultItem getDataSoalSimulasi(int id){
        List<String> opsi = new ArrayList<>();
        ResultItem hasil = new ResultItem();
        Cursor c2;
        String query = "SELECT * FROM data_soal where id_soal='"+id+"'";
        c2 = DatabaseHelper.rawQuery(query);
        if (c2 != null && c2.getCount() != 0) {
            if(c2.moveToFirst()) {
                do {
                    opsi.add(c2.getString(c2.getColumnIndex("opsi_1")));
                    opsi.add(c2.getString(c2.getColumnIndex("opsi_2")));
                    opsi.add(c2.getString(c2.getColumnIndex("opsi_3")));

                    Collections.shuffle(opsi);
                    hasil.setSoal(c2.getString(c2.getColumnIndex("soal")));
                    hasil.setGambar(c2.getString(c2.getColumnIndex("gambar")));
                    hasil.setJawaban(c2.getString(c2.getColumnIndex("jawaban")));
                    hasil.setOpsi1(opsi.get(0));
                    hasil.setOpsi2(opsi.get(1));
                    hasil.setOpsi3(opsi.get(2));
                    hasil.setTopik(c2.getString(c2.getColumnIndex("topik")));
                    hasil.setKategori(c2.getString(c2.getColumnIndex("kategori")));

                    opsi.clear();
                } while (c2.moveToNext());
            }
            c2.close();
        }
        return hasil;
    }

    public ResultItem getDataRandomLatihan(String kategori){
        List<String> opsi = new ArrayList<>();
        ResultItem hasil = new ResultItem();
        Cursor c2;
        String query = "SELECT * FROM data_soal where kategori='UMUM' OR kategori='"+kategori+"'" +
                "  ORDER BY RANDOM() LIMIT 1";
        c2 = DatabaseHelper.rawQuery(query);
        if (c2 != null && c2.getCount() != 0) {
            if(c2.moveToFirst()) {
                do {
                    opsi.add(c2.getString(c2.getColumnIndex("opsi_1")));
                    opsi.add(c2.getString(c2.getColumnIndex("opsi_2")));
                    opsi.add(c2.getString(c2.getColumnIndex("opsi_3")));

                    Collections.shuffle(opsi);
                    hasil.setSoal(c2.getString(c2.getColumnIndex("soal")));
                    hasil.setGambar(c2.getString(c2.getColumnIndex("gambar")));
                    hasil.setJawaban(c2.getString(c2.getColumnIndex("jawaban")));
                    hasil.setOpsi1(opsi.get(0));
                    hasil.setOpsi2(opsi.get(1));
                    hasil.setOpsi3(opsi.get(2));

                    opsi.clear();
                } while (c2.moveToNext());
            }
            c2.close();
        }
        return hasil;
    }

    public ArrayList<Integer> getMaxId(String[] kategori){
        ArrayList<Integer> data = new ArrayList<>();
        for (String aKategori : kategori) {
            String querySoal = "SELECT Max(id_soal) as id_soal FROM data_soal WHERE kategori='" + aKategori + "';";
            Cursor cSoal = DatabaseHelper.rawQuery(querySoal);
            if (cSoal != null && cSoal.getCount() != 0) {
                if (cSoal.moveToFirst()) {
                    do {
                        data.add(cSoal.getInt(cSoal.getColumnIndex("id_soal")));
                    } while (cSoal.moveToNext());
                }
            }
        }
        return data;
    }
}
