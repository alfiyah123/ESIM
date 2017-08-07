package com.example.rahmadewi.esim1.models.rambu;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResultItem{

	@SerializedName("topik")
	private String topik;

	@SerializedName("id_rambu")
	private String idRambu;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("arti")
	private String arti;

	@SerializedName("gambar")
	private String gambar;

	public void setTopik(String topik){
		this.topik = topik;
	}

	public String getTopik(){
		return topik;
	}

	public void setIdRambu(String idRambu){
		this.idRambu = idRambu;
	}

	public String getIdRambu(){
		return idRambu;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	public void setArti(String arti){
		this.arti = arti;
	}

	public String getArti(){
		return arti;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"topik = '" + topik + '\'' + 
			",id_rambu = '" + idRambu + '\'' + 
			",kategori = '" + kategori + '\'' + 
			",arti = '" + arti + '\'' + 
			",gambar = '" + gambar + '\'' + 
			"}";
		}
}