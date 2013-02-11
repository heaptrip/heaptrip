package com.heaptrip.web.controller.adm;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.heaptrip.domain.entity.adm.User;
import com.heaptrip.web.model.adm.RegistrationInfo;

@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired(required = false)
	private HttpServletRequest request;

	@RequestMapping(value = "registration", method = RequestMethod.POST)
	public ModelAndView registration(RegistrationInfo registrationInfo) {

		logger.info(registrationInfo.toString());

		if (registrationInfo.getFirstName().equals("123")) {

			ModelAndView view = new ModelAndView();

			view.addObject("error", "Ошибкааааааа.....");

			return view.addObject("registrationInfo", registrationInfo);
		}

		User user = new User();

		user.setFirstName(registrationInfo.getFirstName());
		user.setSecondName(registrationInfo.getSecondName());
		String[] roles = { "ROLE_USER" };
		user.setRoles(roles);

		authenticateInternal(user);

		return new ModelAndView("redirect:tidings.html");
	}

	public void authenticateInternal(User user) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (user.getRoles() != null) {
			for (String role : user.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role));

			}
		}

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
		auth.setDetails(new WebAuthenticationDetails(request));
		SecurityContextHolder.getContext().setAuthentication(auth);

	}

}
