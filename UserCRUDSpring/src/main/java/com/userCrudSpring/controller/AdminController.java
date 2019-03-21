package com.userCrudSpring.controller;

import com.userCrudSpring.model.Role;
import com.userCrudSpring.model.RoleType;
import com.userCrudSpring.model.User;
import com.userCrudSpring.service.RoleService;
import com.userCrudSpring.service.UserService;
import com.userCrudSpring.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    // private static final Logger log = LogManager.getLogger(AdminController.class.getName());
    
    // private static final String ADMIN_ENDPOINT = "/admin";
    //
    // private static final String CREATE_HEADER = "Create user";
    // private static final String CREATE_ENDPOINT = ADMIN_ENDPOINT + "/create-user";
    //
    // private static final String EDIT_HEADER = "Edit user";
    // private static final String EDIT_ENDPOINT = ADMIN_ENDPOINT + "/edit-user";
    //
    // private static final String DELETE_ENDPOINT = ADMIN_ENDPOINT + "/delete-user";
    
    private final UserService userService;
    private final RoleService roleService;
    
    // private User userToEdit;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    
    // @RequestMapping(path = "/admin", method = RequestMethod.GET )
    // public ModelAndView redirectFromAdmin() {
    //     return new ModelAndView("redirect:/");
    // }
    
    // ALL users
    // GET /admin
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ModelAndView usersTable() {
        return new ModelAndView("admin", "userList", userService.getUsers()).addObject("errorMessage", "");
    }
    
    // // CREATE user
    // // GET /admin/create-user
    // @RequestMapping(path = "/create-user", method = RequestMethod.GET)
    // public ModelAndView createUserView() {
    //     ModelAndView mv = new ModelAndView();
    //     // Set header param
    //     setParamToUserView(mv, CREATE_HEADER, CREATE_ENDPOINT, null);
    //     return mv;
    // }
    
    // POST /admin/create-user
    @RequestMapping(path = "/create-user", method = RequestMethod.POST)
    public ModelAndView createUser(@ModelAttribute User user,
                                   @RequestParam(value = "ADMIN", required = false) Boolean isAdmin,
                                   @RequestParam(value = "USER", required = false) Boolean isUser) {
        ModelAndView mv = new ModelAndView();
    
        // Set Roles to user
        Set<RoleType> selRoles = new HashSet<>();
        if (isAdmin != null) {
            selRoles.add(RoleType.ADMIN);
        }
        if (isUser != null) {
            selRoles.add(RoleType.USER);
        }
    
        setUserRoles(user, selRoles, roleService.getRoles());
    
        // Check user fields
        if (UserUtil.invalidUserFields(user)) {
            mv.addObject("errorMessage", "Invalid user fields.");
            mv.addObject("user", user);
            mv.setViewName("redirect:/admin");
            return mv;
        }
        
        try {
            userService.add(user);
            // Remove attr for modelView
            // model.remove("errorMessage");
            // model.remove("user");
        } catch (Exception e) {
            mv.addObject("errorMessage", e.getMessage());
            mv.addObject("user", user);
        }
    
        mv.setViewName("redirect:/admin");
        return mv;
    }
    
    // // EDIT user
    // // GET /admin/edit-user
    // @RequestMapping(path = "/edit-user", method = RequestMethod.GET)
    // public ModelAndView editView(@RequestParam("login") String login) {
    //     ModelAndView mv = new ModelAndView();
    //     // Set user by login
    //     try {
    //         // Save user to change
    //         userToEdit = userService.getByLogin(login);
    //         mv.addObject("user", userToEdit);
    //         setParamToUserView(mv, EDIT_HEADER, EDIT_ENDPOINT, null);
    //     } catch (Exception e) {
    //         setParamToUserView(mv, EDIT_HEADER, EDIT_ENDPOINT, String.format("Can't getByLogin user by login (%s).", login));
    //     }
    //     return mv;
    // }
    
    // POST /admin/edit-user
    @RequestMapping(path = "/edit-user", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute User user,
                                 @RequestParam(value = "ADMIN", required = false) Boolean isAdmin,
                                 @RequestParam(value = "USER", required = false) Boolean isUser) {
        ModelAndView mv = new ModelAndView();
    
        // Set Roles to user
        Set<RoleType> selRoles = new HashSet<>();
        if (isAdmin != null) {
            selRoles.add(RoleType.ADMIN);
        }
        if (isUser != null) {
            selRoles.add(RoleType.USER);
        }
    
        setUserRoles(user, selRoles, roleService.getRoles());
        
        // Check user fields
        if (UserUtil.invalidUserFields(user)) {
            // mv.addObject("errorMessage", "Invalid user fields.");
            // mv.addObject("user", user);
            mv.setViewName("redirect:/admin");
            return mv;
        }
        
        try {
            User userToEdit = userService.getByLogin(user.getLogin());
    
            // Copy id for save in db changed data
            user.setId(userToEdit.getId());
            user.setPassword(userToEdit.getPassword());
    
            // Update user
            userService.update(user);
            mv.setViewName("redirect:/admin");
        } catch (Exception e) {
            // setParamToUserView(mv, EDIT_HEADER, EDIT_ENDPOINT, e.getMessage());
            // mv.addObject("user", user);
        }
        
        mv.setViewName("redirect:/admin");
        return mv;
    }
    
    // DELETE user
    // POST admin/delete-user
    @RequestMapping(path = "/delete-user", method = RequestMethod.POST)
    public ModelAndView deleteView(@RequestParam("login") String login) {
        ModelAndView mv = new ModelAndView();
        // Delete user by login
        try {
            userService.remove(login);
            mv.setViewName("redirect:/admin");
        } catch (Exception e) {
            // setParamToUserView(mv, EDIT_HEADER, EDIT_ENDPOINT, String.format("Can't delete user by login (%s).", login));
        }
        return mv;
    }
    
    // // Util method for init ModelAndView object
    // private void setParamToUserView(ModelAndView modelAndView, String header, String endpoint, String errorMsg) {
    //     // Check input params
    //     if (modelAndView == null) {
    //         System.out.println("setParamToUserView - param \"modelAndView\" is null");
    //         return;
    //     }
    //     modelAndView.addObject("blockHeader", header);
    //     modelAndView.addObject("servletUrl", endpoint);
    //
    //     // If we have some error message
    //     if (errorMsg != null) {
    //         modelAndView.addObject("errorMessage", errorMsg);
    //     }
    //
    //     modelAndView.setViewName("userView");
    // }
    
    private void setUserRoles(User user, Set<RoleType> selectedRoles, List<Role> rolesFromDb) {
        for (Role role : rolesFromDb) {
            // If we have sel role and not exist in User, add this role
            if (selectedRoles.contains(role.getRole()) && !user.getRoles().contains(role)) {
                user.getRoles().add(role);
            } else { // else delete
                user.getRoles().remove(role);
            }
        }
    }
}