package com.cgtmse.dao;

import java.util.Date;

public class DataFile {
	
	private String tax_inv_id;
	private String dan_id ;
	private String dan_type ;
	private String fy_year ;
	private String generated_irrn ;
	private String status ;
	private String res_status ;
	private String res_ackno ;
	private String res_signedinvoice ;
	private String irn_qr_code ;
	private Date irn_process_date ;
	private Date resackdt ;
	
	public String getTax_inv_id() {
		return tax_inv_id;
	}
	public void setTax_inv_id(String tax_inv_id) {
		this.tax_inv_id = tax_inv_id;
	}
	public String getDan_id() {
		return dan_id;
	}
	public void setDan_id(String dan_id) {
		this.dan_id = dan_id;
	}
	public String getDan_type() {
		return dan_type;
	}
	public void setDan_type(String dan_type) {
		this.dan_type = dan_type;
	}
	public String getFy_year() {
		return fy_year;
	}
	public void setFy_year(String fy_year) {
		this.fy_year = fy_year;
	}
	public String getGenerated_irrn() {
		return generated_irrn;
	}
	public void setGenerated_irrn(String generated_irrn) {
		this.generated_irrn = generated_irrn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRes_ackno() {
		return res_ackno;
	}
	public void setRes_ackno(String res_ackno) {
		this.res_ackno = res_ackno;
	}
	public String getRes_signedinvoice() {
		return res_signedinvoice;
	}
	public void setRes_signedinvoice(String res_signedinvoice) {
		this.res_signedinvoice = res_signedinvoice;
	}
	public String getIrn_qr_code() {
		return irn_qr_code;
	}
	public void setIrn_qr_code(String irn_qr_code) {
		this.irn_qr_code = irn_qr_code;
	}
	public String getRes_status() {
		return res_status;
	}
	public void setRes_status(String res_status) {
		this.res_status = res_status;
	}
	
	public Date getResackdt() {
		return resackdt;
	}
	public void setResackdt(Date resackdt) {
		this.resackdt = resackdt;
	}
	public Date getIrn_process_date() {
		return irn_process_date;
	}
	public void setIrn_process_date(Date irn_process_date) {
		this.irn_process_date = irn_process_date;
	}
	
}
