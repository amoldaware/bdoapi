package com.cgtmse.modal;

import javax.crypto.SecretKey;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="irn_info")
public class IrnData {

	private long id;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
//	private String bdoSek;
	private String appkey;
	private String clientid;
	private String clientsecretencrypted;
//	private String clientsecretencrypted;
	private String appsecretkey;
	private String expiry;
	private String bdo_authtoken;
	private String bdo_sek;
	@Column(name = "expiry", nullable = true)
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	@Column(name = "bdo_authtoken", nullable = true,length = 1000)
	public String getBdo_authtoken() {
		return bdo_authtoken;
	}
	public void setBdo_authtoken(String bdo_authtoken) {
		this.bdo_authtoken = bdo_authtoken;
	}
	@Column(name = "bdo_sek", nullable = true)
	public String getBdo_sek() {
		return bdo_sek;
	}
	public void setBdo_sek(String bdo_sek) {
		this.bdo_sek = bdo_sek;
	}
	@Column(name = "clientid", nullable = false)
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	@Column(name = "clientsecretencrypted", nullable = false,length = 4000)
	public String getClientsecretencrypted() {
		return clientsecretencrypted;
	}
	public void setClientsecretencrypted(String clientsecretencrypted) {
		this.clientsecretencrypted = clientsecretencrypted;
	}
	@Column(name = "appsecretkey", nullable = false,length = 4000)
	public String getAppsecretkey() {
		return appsecretkey;
	}
	public void setAppsecretkey(String appsecretkey) {
		this.appsecretkey = appsecretkey;
	}
	@Column(name = "appkey", nullable = true)
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	
}
