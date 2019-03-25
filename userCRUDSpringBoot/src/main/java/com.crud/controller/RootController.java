package com.crud.controller;

import com.crud.util.UserUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RootController {
    // Root url
    @RequestMapping(path = { "/", "/index" }, method = RequestMethod.GET)
    public String getRoot() {
        String redirectUrl = UserUtil.determineTargetUrl(SecurityContextHolder.getContext().getAuthentication());
        
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        } else {
            return "signIn";
        }
    }
}
