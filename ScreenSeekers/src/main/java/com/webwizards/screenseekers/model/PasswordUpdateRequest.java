package com.webwizards.screenseekers.model;

public class PasswordUpdateRequest {
	
    private String newPassword;
    private String oldPassword;
    
    public PasswordUpdateRequest(String newPassword, String oldPassword) {
    	this.newPassword = newPassword;
    	this.oldPassword = oldPassword;
    }
    
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
    
    

}
