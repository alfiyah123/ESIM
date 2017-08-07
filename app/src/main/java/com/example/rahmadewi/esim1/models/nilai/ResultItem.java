package com.example.rahmadewi.esim1.models.nilai;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResultItem{

	@SerializedName("id_nilai")
	private String idNilai;

	@SerializedName("nilai")
	private String nilai;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("username")
	private String username;

	public void setIdNilai(String idNilai){
		this.idNilai = idNilai;
	}

	public String getIdNilai(){
		return idNilai;
	}

	public void setNilai(String nilai){
		this.nilai = nilai;
	}

	public String getNilai(){
		return nilai;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"id_nilai = '" + idNilai + '\'' + 
			",nilai = '" + nilai + '\'' + 
			",kategori = '" + kategori + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}