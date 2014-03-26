package com.heaptrip.security;

import com.heaptrip.domain.entity.account.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Listeners(AuthenticationListener.class)
public class AuthenticationListenerTest {

    @Test
    public void testNotAuthenticatedMethod() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.assertNull(authentication);
    }

    @Test
    @Authenticate(userid = "1", username = "tester", roles = "ROLE_USER")
    public void testAuthenticatedMethod() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.assertNotNull(authentication);
        Assert.assertNotNull(authentication.getPrincipal());
        Assert.assertTrue(authentication.getPrincipal() instanceof User);
        User user = (User) authentication.getPrincipal();
        Assert.assertEquals(user.getId(), "1");
        Assert.assertEquals(user.getName(), "tester");
        Assert.assertEquals(authentication.getAuthorities().size(), 1);
        Assert.assertEquals(authentication.getAuthorities().iterator().next().getAuthority(), "ROLE_USER");
    }

    @Test
    @Authenticate(userid = "1", username = "tester", roles = {"ROLE_USER", "ROLE_ADMIN"})
    public void testAuthenticatedMethodWithManyRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.assertNotNull(authentication);
        Assert.assertEquals(authentication.getAuthorities().size(), 2);
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            roles.add(auth.getAuthority());
        }
        Assert.assertTrue(roles.contains("ROLE_USER"));
        Assert.assertTrue(roles.contains("ROLE_ADMIN"));
    }


    @Test
    @Authenticate(userid = "1", username = "tester", roles = {"ROLE_USER", "ROLE_USER"})
    public void testAuthenticatedMethodWithDuplicateRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.assertNotNull(authentication);
        Assert.assertEquals(authentication.getAuthorities().size(), 1);
        Assert.assertEquals(authentication.getAuthorities().iterator().next().getAuthority(), "ROLE_USER");
    }

    @Test
    @Authenticate(userid = "1", username = "tester")
    public void testMissingRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.assertNotNull(authentication);
        Assert.assertTrue(authentication.getAuthorities().isEmpty());
    }

    @Test
    @Authenticate(userid = "1")
    public void testMissingUsernameAndRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.assertNotNull(authentication);
        Assert.assertNotNull(authentication.getPrincipal());
        Assert.assertTrue(authentication.getPrincipal() instanceof User);
        User user = (User) authentication.getPrincipal();
        Assert.assertEquals(user.getName(), AuthenticationListener.DEFAULT_USERNAME);
        Assert.assertTrue(authentication.getAuthorities().isEmpty());
    }

    @Test
    @Authenticate
    public void testMissingAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.assertNotNull(authentication);
        Assert.assertNotNull(authentication.getPrincipal());
        Assert.assertTrue(authentication.getPrincipal() instanceof User);
        User user = (User) authentication.getPrincipal();
        Assert.assertEquals(user.getId(), AuthenticationListener.DEFAULT_USERID);
        Assert.assertEquals(user.getName(), AuthenticationListener.DEFAULT_USERNAME);
        Assert.assertTrue(authentication.getAuthorities().isEmpty());
    }

}
