package com.heaptrip.domain.entity.socnet.fb;

public class FBAccessToken {

	private String access_token;
	private Long expires;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Long getExpires() {
		return expires;
	}

	public void setExpires(Long expires) {
		this.expires = expires;
	}

}
