package com.heaptrip.domain.entity.user;

/**
 * 
 * entity use for user registration on the website and through the social network
 * 
 */

public class UserRegistration extends User {

	private String email;
	
	private String password;
	
	private String profileImageId;
	
	private String imageId;
	
	private SocialNetwork[] net;
	
	private Byte[] imageCRC;

	public SocialNetwork[] getNet() {
		return net;
	}

	public void setNet(SocialNetwork[] net) {
		this.net = net;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getProfileImageId() {
		return profileImageId;
	}

	public void setProfileImageId(String profileImageId) {
		this.profileImageId = profileImageId;
	}

	public Byte[] getImageCRC() {
		return imageCRC;
	}

	public void setImageCRC(Byte[] imageCRC) {
		this.imageCRC = imageCRC;
	}
}
