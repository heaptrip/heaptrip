package com.heaptrip.domain.entity.content.trip;

/**
 * 
 * Invitation to an external email address
 * 
 */
public class TableInvite extends TableMember {

	// email address
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
