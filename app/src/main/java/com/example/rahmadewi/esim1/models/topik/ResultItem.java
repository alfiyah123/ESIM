package com.example.rahmadewi.esim1.models.topik;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResultItem{

	@SerializedName("nama_topik")
	private String namaTopik;

	@SerializedName("id_topik")
	private String idTopik;

	public void setNamaTopik(String namaTopik){
		this.namaTopik = namaTopik;
	}

	public String getNamaTopik(){
		return namaTopik;
	}

	public void setIdTopik(String idTopik){
		this.idTopik = idTopik;
	}

	public String getIdTopik(){
		return idTopik;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"nama_topik = '" + namaTopik + '\'' + 
			",id_topik = '" + idTopik + '\'' + 
			"}";
		}
}