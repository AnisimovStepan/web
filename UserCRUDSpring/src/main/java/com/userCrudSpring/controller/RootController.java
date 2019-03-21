package com.userCrudSpring.controller;

import com.userCrudSpring.util.UserUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RootController {
    // Root url
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView getRoot() {
        String redirectUrl = UserUtil.determineTargetUrl(SecurityContextHolder.getContext().getAuthentication());
        
        if (redirectUrl != null) {
            return new ModelAndView("redirect:" + redirectUrl);
        } else {
            return new ModelAndView("signIn");
        }
    }
}
