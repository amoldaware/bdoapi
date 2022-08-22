package com.cgtmse.dao;

public class InvoiceUser {
	
	private String clientId;
	private String clientSecret;
	private String gstin;
	private String password;
	private String userName;
	private String authUrl;	
	private String generateEinvoiceUrl;
	private String cancelEinvoiceUrl;
	private String publickey;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getGstin() {
		return gstin;
	}
	public void setGstin(String gstin) {
		this.gstin = gstin;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAuthUrl() {
		return authUrl;
	}
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public String getGenerateEinvoiceUrl() {
		return generateEinvoiceUrl;
	}
	public void setGenerateEinvoiceUrl(String generateEinvoiceUrl) {
		this.generateEinvoiceUrl = generateEinvoiceUrl;
	}
	public String getCancelEinvoiceUrl() {
		return cancelEinvoiceUrl;
	}
	public void setCancelEinvoiceUrl(String cancelEinvoiceUrl) {
		this.cancelEinvoiceUrl = cancelEinvoiceUrl;
	}
	public String getPublickey() {
		return publickey;
	}
	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}
}
