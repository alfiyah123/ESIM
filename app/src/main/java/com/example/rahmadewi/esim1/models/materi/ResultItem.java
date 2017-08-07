package com.example.rahmadewi.esim1.models.materi;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResultItem{

	@SerializedName("topik")
	private String topik;

	@SerializedName("materi")
	private String materi;

	@SerializedName("id_materi")
	private String idMateri;

	@SerializedName("sub_topik")
	private String subTopik;

	@SerializedName("kategori")
	private String kategori;

	public void setTopik(String topik){
		this.topik = topik;
	}

	public String getTopik(){
		return topik;
	}

	public void setMateri(String materi){
		this.materi = materi;
	}

	public String getMateri(){
		return materi;
	}

	public void setIdMateri(String idMateri){
		this.idMateri = idMateri;
	}

	public String getIdMateri(){
		return idMateri;
	}

	public void setSubTopik(String subTopik){
		this.subTopik = subTopik;
	}

	public String getSubTopik(){
		return subTopik;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"topik = '" + topik + '\'' + 
			",materi = '" + materi + '\'' + 
			",id_materi = '" + idMateri + '\'' + 
			",sub_topik = '" + subTopik + '\'' + 
			",kategori = '" + kategori + '\'' + 
			"}";
		}
}