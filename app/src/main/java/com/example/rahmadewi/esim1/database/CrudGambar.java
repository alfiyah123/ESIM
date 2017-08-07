package com.example.rahmadewi.esim1.database;

import com.example.rahmadewi.esim1.models.gambar.ResultItem;

/**
 * Created by Rahmadewi on 7/31/2017.
 */

public class CrudGambar {
    public void insertData(ResultItem resultItem){
        String insertQuery = "INSERT INTO data_gambar (id_gambar, materi, nama_gambar)" +
                "VALUES(" +
                "'"+ Integer.parseInt(resultItem.getIdGambar()) + "'," +
                "'"+ resultItem.getMateri() + "'," +
                "'"+ resultItem.getNamaGambar() + "')";
        DatabaseHelper.execute(insertQuery);
    }
}
