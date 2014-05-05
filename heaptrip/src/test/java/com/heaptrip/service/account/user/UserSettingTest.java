package com.heaptrip.service.account.user;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.user.*;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.mail.MessagingException;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class UserSettingTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test(enabled = true, priority = 1, expectedExceptions = AccountException.class)
    public void saveSettingFakeUser() {
        UserSetting setting = new UserSetting();
        setting.setAdsFromClub(true);
        setting.setAdsFromAgency(false);
        setting.setAdsFromCompany(false);
        userService.saveSetting(UserDataProvider.FAKE_USER_ID, setting);
    }

    @Test(enabled = true, priority = 2, expectedExceptions = AccountException.class)
    public void saveSettingNotConfirmedUser() {
        UserSetting setting = new UserSetting();
        setting.setAdsFromClub(true);
        setting.setAdsFromAgency(false);
        setting.setAdsFromCompany(false);
        userService.saveSetting(UserDataProvider.NOT_CONFIRMED_USER_ID, setting);
    }

    @Test(enabled = true, priority = 4)
    public void saveSetting() {
        User user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getSetting());
        ((UserSetting) user.getSetting()).setAdsFromClub(true);
        ((UserSetting) user.getSetting()).setAdsFromAgency(false);
        ((UserSetting) user.getSetting()).setAdsFromCompany(false);

        userService.saveSetting(UserDataProvider.NET_USER_ID, (UserSetting) user.getSetting());
        user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getSetting());
        Assert.assertFalse((user.getSetting()).getAdsFromAgency());
        Assert.assertTrue((user.getSetting()).getAdsFromClub());
        Assert.assertFalse((user.getSetting()).getAdsFromCompany());
    }

    @Test(enabled = true, priority = 11, expectedExceptions = {AccountException.class, MessagingException.class})
    public void changePasswordFakeUser() {
        userService.changePassword(UserDataProvider.FAKE_USER_ID,
                                    UserDataProvider.FAKE_USER_PSWD,
                                    UserDataProvider.FAKE_USER_PSWD);
    }

    @Test(enabled = true, priority = 12, expectedExceptions = {AccountException.class, MessagingException.class})
    public void changePasswordNotConfirmedUser() {
        userService.changePassword(UserDataProvider.NOT_CONFIRMED_USER_ID,
                                    UserDataProvider.NOT_CONFIRMED_USER_PSWD,
                                    UserDataProvider.NOT_CONFIRMED_USER_PSWD);
    }

    @Test(enabled = true, priority = 13)
    public void changePasswordActiveUser() {
        userService.changePassword(UserDataProvider.ACTIVE_USER_ID,
                                    UserDataProvider.ACTIVE_USER_PSWD,
                                    UserDataProvider.ACTIVE_USER_PSWD_NEW);
    }

    @Test(enabled = true, priority = 21, expectedExceptions = {AccountException.class, MessagingException.class})
    public void changeEmailFakeUser() {
        userService.changeEmail(UserDataProvider.FAKE_USER_ID,
                                UserDataProvider.FAKE_USER_EMAIL,
                                UserDataProvider.FAKE_USER_EMAIL);
    }

    @Test(enabled = true, priority = 22, expectedExceptions = {AccountException.class, MessagingException.class})
    public void changeEmailNotConfirmedUser() {
        userService.changeEmail(UserDataProvider.NOT_CONFIRMED_USER_ID,
                                UserDataProvider.NOT_CONFIRMED_USER_EMAIL,
                                UserDataProvider.NOT_CONFIRMED_USER_EMAIL);
    }

    @Test(enabled = true, priority = 23, expectedExceptions = {AccountException.class})
    public void changeEmailIncorrectNewEmail() {
        userService.changeEmail(UserDataProvider.EMAIL_USER_ID,
                                UserDataProvider.EMAIL_USER_EMAIL,
                                UserDataProvider.INCORRECT_EMAIL);
    }

    @Test(enabled = true, priority = 24, expectedExceptions = {AccountException.class})
    public void changeEmailWrongCurrentEmail() {
        userService.changeEmail(UserDataProvider.EMAIL_USER_ID,
                                UserDataProvider.EMAIL_USER_EMAIL_NEW,
                                UserDataProvider.EMAIL_USER_EMAIL_NEW);
    }

    @Test(enabled = true, priority = 25)
    public void changeEmail() {
        userService.changeEmail(UserDataProvider.EMAIL_USER_ID,
                                UserDataProvider.EMAIL_USER_EMAIL,
                                UserDataProvider.EMAIL_USER_EMAIL_NEW);
    }

    @Test(enabled = true, priority = 31, expectedExceptions = AccountException.class)
    public void profileImageFromFakeUser() {
        userService.profileImageFrom(UserDataProvider.FAKE_USER_ID, SocialNetworkEnum.VK);
    }

    @Test(enabled = true, priority = 32, expectedExceptions = AccountException.class)
    public void profileImageFromNotConfirmedUser() {
        userService.profileImageFrom(UserDataProvider.NOT_CONFIRMED_USER_ID, SocialNetworkEnum.VK);
    }

    @Test(enabled = true, priority = 33, expectedExceptions = AccountException.class)
    public void profileImageFromEmailUser() {
        userService.profileImageFrom(UserDataProvider.EMAIL_USER_ID, SocialNetworkEnum.VK);
    }

    @Test(enabled = true, priority = 34)
    public void profileImageFrom() {
        userService.profileImageFrom(UserDataProvider.NET_USER_ID, SocialNetworkEnum.NONE);
        User user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);
        Assert.assertFalse(user.getExternalImageStore().equals(SocialNetworkEnum.NONE));
    }

    @Test(enabled = true, priority = 41, expectedExceptions = AccountException.class)
    public void unlinkSocialNetworkFakeUser() {
        userService.unlinkSocialNetwork(UserDataProvider.FAKE_USER_ID, SocialNetworkEnum.FB);
    }

    @Test(enabled = true, priority = 42, expectedExceptions = AccountException.class)
    public void unlinkSocialNetworkNotConfirmedUser() {
        userService.unlinkSocialNetwork(UserDataProvider.NOT_CONFIRMED_USER_ID, SocialNetworkEnum.FB);
    }

    @Test(enabled = true, priority = 43, expectedExceptions = AccountException.class)
    public void unlinkSocialNetworkEmailUser() {
        userService.unlinkSocialNetwork(UserDataProvider.EMAIL_USER_ID, SocialNetworkEnum.FB);
    }

    @Test(enabled = true, priority = 44, expectedExceptions = AccountException.class)
    public void unlinkSocialNetworkWrongSocialNetwork() {
        userService.unlinkSocialNetwork(UserDataProvider.NET_USER_ID, SocialNetworkEnum.FB);
    }

    @Test(enabled = true, priority = 45, expectedExceptions = AccountException.class)
    public void unlinkSocialNetworkEmptyPassword() {
        userService.unlinkSocialNetwork(UserDataProvider.NET_USER_ID, SocialNetworkEnum.FB);
    }

    @Test(enabled = true, priority = 46)
    public void unlinkSocialNetwork() {
        userService.changePassword(UserDataProvider.NET_USER_ID, "", UserDataProvider.NET_USER_PSWD);
        userService.unlinkSocialNetwork(UserDataProvider.NET_USER_ID, SocialNetworkEnum.VK);
        User user = userRepository.findOne(UserDataProvider.NET_USER_ID);
        if (user.getNet() != null) {
            SocialNetwork[] nets = user.getNet();

            for (SocialNetwork net : nets) {
                Assert.assertTrue(!net.getId().equals(SocialNetworkEnum.VK.toString()));
            }
        }
    }

    @Test(enabled = true, priority = 51, expectedExceptions = AccountException.class)
    public void linkSocialNetworkFakeUser() {
        userService.linkSocialNetwork(UserDataProvider.FAKE_USER_ID, UserDataProvider.getFB());
    }

    @Test(enabled = true, priority = 52, expectedExceptions = AccountException.class)
    public void linkSocialNotConfirmedUser() {
        userService.linkSocialNetwork(UserDataProvider.NOT_CONFIRMED_USER_ID, UserDataProvider.getFB());
    }

    @Test(enabled = true, priority = 53)
    public void linkSocialNetwork() {
        userService.linkSocialNetwork(UserDataProvider.NET_USER_ID, UserDataProvider.getFB());
        User user = userRepository.findOne(UserDataProvider.NET_USER_ID);
        if (user.getNet() != null) {
            SocialNetwork[] nets = user.getNet();

            for (SocialNetwork net : nets) {
                Assert.assertTrue(net.getId().equals(SocialNetworkEnum.FB.toString()));
            }
        }
    }

    @Test(enabled = true, priority = 54, expectedExceptions = AccountException.class)
    public void linkSocialNetworkExists() {
        userService.linkSocialNetwork(UserDataProvider.NET_USER_ID, UserDataProvider.getFB());
    }

    @Test(enabled = true, priority = 61, expectedExceptions = AccountException.class)
    public void deleteUserFakeUser() {
        userService.delete(UserDataProvider.FAKE_USER_ID);
    }

    @Test(enabled = true, priority = 62, expectedExceptions = AccountException.class)
    public void deleteUserNotConfirmedUser() {
        userService.delete(UserDataProvider.NOT_CONFIRMED_USER_ID);
    }

    @Test(enabled = true, priority = 63)
    public void deleteUser() {
        userService.delete(UserDataProvider.NET_USER_ID);
        User user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);
        Assert.assertTrue(user.getStatus().equals(AccountStatusEnum.DELETED));
    }
}
