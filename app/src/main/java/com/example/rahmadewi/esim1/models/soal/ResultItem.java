package com.example.rahmadewi.esim1.models.soal;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResultItem{

	@SerializedName("opsi_2")
	private String opsi2;

	@SerializedName("topik")
	private String topik;

	@SerializedName("opsi_3")
	private String opsi3;

	@SerializedName("soal")
	private String soal;

	@SerializedName("opsi_1")
	private String opsi1;

	@SerializedName("id_soal")
	private String idSoal;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("jawaban")
	private String jawaban;

	@SerializedName("gambar")
	private String gambar;

	public void setOpsi2(String opsi2){
		this.opsi2 = opsi2;
	}

	public String getOpsi2(){
		return opsi2;
	}

	public void setTopik(String topik){
		this.topik = topik;
	}

	public String getTopik(){
		return topik;
	}

	public void setOpsi3(String opsi3){
		this.opsi3 = opsi3;
	}

	public String getOpsi3(){
		return opsi3;
	}

	public void setSoal(String soal){
		this.soal = soal;
	}

	public String getSoal(){
		return soal;
	}

	public void setOpsi1(String opsi1){
		this.opsi1 = opsi1;
	}

	public String getOpsi1(){
		return opsi1;
	}

	public void setIdSoal(String idSoal){
		this.idSoal = idSoal;
	}

	public String getIdSoal(){
		return idSoal;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	public void setJawaban(String jawaban){
		this.jawaban = jawaban;
	}

	public String getJawaban(){
		return jawaban;
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
			"opsi_2 = '" + opsi2 + '\'' + 
			",topik = '" + topik + '\'' + 
			",opsi_3 = '" + opsi3 + '\'' + 
			",soal = '" + soal + '\'' + 
			",opsi_1 = '" + opsi1 + '\'' + 
			",id_soal = '" + idSoal + '\'' + 
			",kategori = '" + kategori + '\'' + 
			",jawaban = '" + jawaban + '\'' + 
			",gambar = '" + gambar + '\'' + 
			"}";
		}
}