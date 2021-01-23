package com.automation.testapplication.controllers;

import com.automation.testapplication.entities.User;
import com.automation.testapplication.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final IUserRepository userReporsitory;

    @Autowired
    public UserController(IUserRepository userReporsitory) {
        this.userReporsitory = userReporsitory;
    }

    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("users", userReporsitory.findAll());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignupForm(User user){
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "add-user";
        }
        userReporsitory.save(user);
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userReporsitory.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid User Id:" + id));
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return  "update-error";
        }

        userReporsitory.save(user);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userReporsitory.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid User Id: " + id));
        userReporsitory.delete(user);
        return "redirect:/index";
    }
    
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<String> showUsers() {
        List<String> users = new ArrayList<>();
        for (User user : userReporsitory.findAll()) {
            users.add(user.toString());
        }
        return users;
    }
}
