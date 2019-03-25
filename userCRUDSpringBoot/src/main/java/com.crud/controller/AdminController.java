package com.crud.controller;

import com.crud.model.Role;
import com.crud.model.RoleType;
import com.crud.model.User;
import com.crud.service.RoleService;
import com.crud.service.UserService;
import com.crud.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    
    // ALL users
    // GET /admin
    @RequestMapping(path = "", method = RequestMethod.GET)
    public String usersTable(Model model, HttpSession session) {
        model.addAttribute("userList", userService.getUsers());
        session.removeAttribute("errorMessage");
        return "admin";
    }
    
    // POST /admin/create-user
    @RequestMapping(path = "/create-user", method = RequestMethod.POST)
    public String createUser(@ModelAttribute User user,
                                   @RequestParam(value = "ADMIN", required = false) Boolean isAdmin,
                                   @RequestParam(value = "USER", required = false) Boolean isUser,
                                    HttpSession session) {
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
            session.setAttribute("errorMessage", "Invalid user fields.");
            return "redirect:/admin";
        }
        
        try {
            userService.add(user);
            session.removeAttribute("errorMessage");
        } catch (Exception e) {
            session.setAttribute("errorMessage", e.getMessage());
        }
    
        return "redirect:/admin";
    }
    
    // POST /admin/edit-user
    @RequestMapping(path = "/edit-user", method = RequestMethod.POST)
    public String editUser(@ModelAttribute User user,
                             @RequestParam(value = "ADMIN", required = false) Boolean isAdmin,
                             @RequestParam(value = "USER", required = false) Boolean isUser,
                             HttpSession session) {
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
            session.setAttribute("errorMessageModal", "Invalid user fields.");
            return "redirect:/admin";
        }
        
        try {
            User userToEdit = userService.getById(user.getId());
    
            // Copy id for save in db changed data
            user.setPassword(userToEdit.getPassword());
    
            // Update user
            userService.update(user);
            session.removeAttribute("errorMessage");
        } catch (Exception e) {
            session.setAttribute("errorMessageModal", e.getMessage());
        }
        
        return "redirect:/admin";
    }
    
    // DELETE user
    // POST admin/delete-user
    @RequestMapping(path = "/delete-user", method = RequestMethod.POST)
    public String deleteView(@RequestParam("id") long id, HttpSession session) {
        try {
            // Delete user by login
            userService.remove(id);
            session.removeAttribute("errorMessageModal");
        } catch (Exception e) {
            session.setAttribute("errorMessageModal", e.getMessage());
        }
        return "redirect:/admin";
    }
    
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