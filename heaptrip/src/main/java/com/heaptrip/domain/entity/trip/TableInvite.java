package com.heaptrip.domain.entity.trip;

/**
 * 
 * Invitation to an external email address
 * 
 */
public class TableInvite extends BaseTableMember {

	// email address
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
