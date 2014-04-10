package com.heaptrip.security;

import com.heaptrip.domain.entity.account.user.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationListener implements IInvokedMethodListener {

    public static final String DEFAULT_USERID = "-1";

    public static final String DEFAULT_USERNAME = "default";

    private static final String FAKE_PASSWORD = "fakePassword";

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod() || method.isConfigurationMethod()) {
            ITestNGMethod testMethod = method.getTestMethod();
            Method javaMethod = testMethod.getConstructorOrMethod().getMethod();
            Authenticate userAnnotation = javaMethod.getAnnotation(Authenticate.class);
            if (userAnnotation != null) {
                String userid = userAnnotation.userid();
                String username = userAnnotation.username();
                if (StringUtils.isEmpty(userid)) {
                    userid = DEFAULT_USERID;
                }
                if (StringUtils.isEmpty(username)) {
                    username = DEFAULT_USERNAME;
                }
                String[] roles = userAnnotation.roles(); // role may be null/empty
                authenticateUser(userid, username, roles);
            }
        }
    }

    private void authenticateUser(String userid, String username, String... roles) {
        User user = new User();
        user.setId(userid);
        user.setName(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (roles != null) {
            for (String role : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role));
            }
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, FAKE_PASSWORD, grantedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            SecurityContextHolder.clearContext();
        }
    }

}