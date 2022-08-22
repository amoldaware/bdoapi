package com.cgtmse.dao;

public class Desc {
    private String AckNo;

    private String AckDt;

    private String Irn;

    public void setAckNo(String AckNo){
        this.AckNo = AckNo;
    }
    public String getAckNo(){
        return this.AckNo;
    }
    public void setAckDt(String AckDt){
        this.AckDt = AckDt;
    }
    public String getAckDt(){
        return this.AckDt;
    }
    public void setIrn(String Irn){
        this.Irn = Irn;
    }
    public String getIrn(){
        return this.Irn;
    }
	
	@Override
	   public String toString() {
	      return "Desc [AckNo=" + AckNo+ ", AckDt=" + AckDt +", Irn=" + Irn+ "]";
	   }
}
