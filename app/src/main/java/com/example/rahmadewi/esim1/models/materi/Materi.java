package com.example.rahmadewi.esim1.models.materi;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Materi{

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("code")
	private int code;

	@SerializedName("status")
	private String status;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Materi{" + 
			"result = '" + result + '\'' + 
			",code = '" + code + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}