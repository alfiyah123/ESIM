package com.example.rahmadewi.esim1.network;

import com.example.rahmadewi.esim1.models.gambar.Gambar;
import com.example.rahmadewi.esim1.models.kategori.Kategori;
import com.example.rahmadewi.esim1.models.materi.Materi;
import com.example.rahmadewi.esim1.models.nilai.Nilai;
import com.example.rahmadewi.esim1.models.rambu.Rambu;
import com.example.rahmadewi.esim1.models.soal.Soal;
import com.example.rahmadewi.esim1.models.topik.Topik;
import com.example.rahmadewi.esim1.models.user.User;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rahmadewi on 7/29/2017.
 */

public interface NetworkStores {

    //untuk unduh data

    @GET("kategori")
    Observable<Kategori> getDataKategori();

    @GET("topik")
    Observable<Topik> getDataTopik();

    @GET("soal")
    Observable<Soal> getDataSoal(@Query("kategori[]") String[] key);

    @GET("materi")
    Observable<Materi> getDataMateri(@Query("kategori[]") String[] key);

    @GET("rambu")
    Observable<Rambu> getDataRambu();

    @GET("gambar")
    Observable<Gambar> getGambar(@Query(
            "id[]") String[] key);

    //untuk grafik nilai user

    @GET("nilai/{kategori}")
    Observable<Nilai> getNilai(@Path("kategori") String kategori);

    //untuk get MaxID

    @GET("topik/maxId")
    Observable<Topik> getMaxIdTopik();

    @GET("rambu/maxId")
    Observable<Rambu> getMaxIdRambu();

    @GET("soal/maxId")
    Observable<Soal> getMaxIdSoal(@Query("kategori[]") String[] key);

    @GET("materi/maxId")
    Observable<Materi> getMaxIdMateri(@Query("kategori[]") String[] key);

    //untuk update data

    @GET("topik/update/{id}")
    Observable<Topik> updateTopik(@Path("id") int key);

    @GET("rambu/update/{id}")
    Observable<Rambu> updateRambu(@Path("id") int key);

    @GET("soal/update")
    Observable<Soal> updateSoal(@Query("id[]") int[] key, @Query("kategori[]") String[] kategori);

    @GET("materi/update")
    Observable<Materi> updateMateri(@Query("id[]") int[] key, @Query("kategori[]") String[] kategori);

    //untuk login dan register user

    @FormUrlEncoded
    @POST("user")
    Observable<User> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("nomor") String nomor);

    @FormUrlEncoded
    @POST("user/login")
    Observable<User> login(
            @Field("username") String username,
            @Field("password") String password);

    //untuk share nilai dashboard
    @FormUrlEncoded
    @POST("nilai")
    Observable<Nilai> shareNilai(
            @Field("username") String username,
            @Field("nilai") int nilai,
            @Field("kategori") String kategori);


}
