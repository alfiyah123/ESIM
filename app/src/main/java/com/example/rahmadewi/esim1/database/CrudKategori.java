package com.example.rahmadewi.esim1.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rahmadewi.esim1.models.kategori.ResultItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahmadewi on 7/30/2017.
 */

public class CrudKategori{
    public void insertData(ResultItem resultItem){
        String insertQuery = "INSERT INTO data_kategori (id_kategori, nama_kategori)" +
                "VALUES(" +
                "'"+ Integer.parseInt(resultItem.getIdKategori()) + "'," +
                "'" + resultItem.getNamaKategori() + "')";
        DatabaseHelper.execute(insertQuery);
    }
}
