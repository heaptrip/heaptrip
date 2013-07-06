package com.heaptrip.service.user;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.entity.user.UserRegistration;
import com.heaptrip.domain.entity.user.UserStatusEnum;
import com.heaptrip.domain.repository.user.AuthenticationRepository;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.domain.service.user.AuthenticationService;
import com.heaptrip.util.StreamUtils;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	static String EMAIL_REGEX = "^[-!#$%&'*+/0-9=?A-Z^_a-z{|}~](\\.?[-!#$%&'*+/0-9=?A-Z^_a-z{|}~])*@[a-zA-Z](-?[a-zA-Z0-9])*(\\.[a-zA-Z](-?[a-zA-Z0-9])*)+$";
	static String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]))";
	
	@Autowired
	private AuthenticationRepository authenticationRepository;
	
	@Autowired
	private ImageService imageService;
	
	@Override
	public User getUserByEmail(String email, String password) {
		return authenticationRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public User getUserBySocNetUID(String socNetName, String uid) {
		return authenticationRepository.findUserBySocNetUID(socNetName, uid);
	}

	@Override
	public User registration(UserRegistration userRegistration, InputStream isImage) throws IOException, NoSuchAlgorithmException {
		Assert.notNull(userRegistration, "userRegistration must not be null");
		Assert.notNull(userRegistration.getEmail(), "email must not be null");
		Assert.isTrue(userRegistration.getEmail().matches(EMAIL_REGEX), "email is not correct");
		
		if (userRegistration.getNet() == null) {
			Assert.notNull(userRegistration.getPassword(), "password must not be null");
			Assert.isTrue(userRegistration.getPassword().length() > 8, "length password must be at least 8 characters and maximum length of 32");
			Assert.isTrue(userRegistration.getPassword().length() < 32, "length password must be at least 8 characters and maximum length of 32");
			Assert.isTrue(!userRegistration.getPassword().matches(PASSWORD_REGEX), "password must contains 0-9, lowercase characters a-z and uppercase characters A-Z");
		} else {
			Assert.notEmpty(userRegistration.getNet(), "the array social network must have one element");
			SocialNetwork[] net = userRegistration.getNet();
			Assert.isTrue(net.length == 1, "the array social network must have one element");
			Assert.notNull(net[0].getId(), "id network must not be null");
			Assert.notNull(net[0].getUid(), "uid must not be null");
			
			if (isImage != null) {
				
				isImage = StreamUtils.getResetableInputStream(isImage);
				
				userRegistration.setImageId(imageService.saveImage(net[0].getId() + net[0].getUid(), ImageEnum.USER_PHOTO, isImage));
				isImage.reset();
				userRegistration.setProfileImageId(imageService.saveImage(net[0].getId() + net[0].getUid(), ImageEnum.USER_PHOTO_PROFILE, isImage));
				isImage.reset();
				
				MessageDigest md;
				md = MessageDigest.getInstance("MD5");
				byte[] image = new byte[isImage.available()];
				isImage.read(image);
				byte[] d = md.digest(image);
				Byte[] digest = ArrayUtils.toObject(d);
				userRegistration.setImageCRC(digest);
			}
		}
		
		userRegistration.setStatus(UserStatusEnum.NOTCONFIRMED);
		
		return authenticationRepository.save(userRegistration);
	}

	@Override
	public void hardRemoveUser(String userId) {
		Assert.notNull(userId, "userId must not be null");
		authenticationRepository.remove(userId);
	}

	@Override
	public Boolean confirmRegistration(String email) {
		return authenticationRepository.confirmRegistration(email);
	}

	@Override
	public void resetPassword(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendNewPassword(String email, String value) {
		// TODO Auto-generated method stub
		
	}
}
