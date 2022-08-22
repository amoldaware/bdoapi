package com.cgtmse.dao;

import java.util.List;

public class TokenStatus {
	private int Status;

    private List<ErrorDetails> ErrorDetails;

    private String Data;

    private String InfoDtls;

    public void setStatus(int Status){
        this.Status = Status;
    }
    public int getStatus(){
        return this.Status;
    }
    public void setErrorDetails(List<ErrorDetails> ErrorDetails){
        this.ErrorDetails = ErrorDetails;
    }
    public List<ErrorDetails> getErrorDetails(){
        return this.ErrorDetails;
    }
    public void setData(String Data){
        this.Data = Data;
    }
    public String getData(){
        return this.Data;
    }
    public void setInfoDtls(String InfoDtls){
        this.InfoDtls = InfoDtls;
    }
    public String getInfoDtls(){
        return this.InfoDtls;
    }
}
