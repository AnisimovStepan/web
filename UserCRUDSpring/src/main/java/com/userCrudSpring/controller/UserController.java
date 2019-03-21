package com.userCrudSpring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public ModelAndView usersTable() {
        return new ModelAndView("user");
    }
    
}
