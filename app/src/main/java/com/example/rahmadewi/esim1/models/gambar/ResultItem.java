package com.example.rahmadewi.esim1.models.gambar;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResultItem{

	@SerializedName("id_gambar")
	private String idGambar;

	@SerializedName("materi")
	private String materi;

	@SerializedName("nama_gambar")
	private String namaGambar;

	public void setIdGambar(String idGambar){
		this.idGambar = idGambar;
	}

	public String getIdGambar(){
		return idGambar;
	}

	public void setMateri(String materi){
		this.materi = materi;
	}

	public String getMateri(){
		return materi;
	}

	public void setNamaGambar(String namaGambar){
		this.namaGambar = namaGambar;
	}

	public String getNamaGambar(){
		return namaGambar;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"id_gambar = '" + idGambar + '\'' + 
			",materi = '" + materi + '\'' + 
			",nama_gambar = '" + namaGambar + '\'' + 
			"}";
		}
}