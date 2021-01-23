package com.automation.testapplication.controllers;

import com.automation.testapplication.entities.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestApplicationController {

    Map<Integer, String> users = new HashMap<>();

//    @RequestMapping (value = "/", method = RequestMethod.GET)
//    public String welcome() {
//        return "Welcome to Test Application !!!";
//    }

    @GetMapping ("/welcome")
    public String welcomeUser(@RequestParam(value = "name") Principal user) {
        return "Welcome User: " + user.getName() ;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello " ;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam String name, @RequestParam (required = false, defaultValue = "day") String time) {
//        if (time == null) {
//            time = "day";
//        }
        return "Good " + time + " !!! " + name;
    }

    @GetMapping("/user/{id}")
    public String greetUsername(@PathVariable(value = "id") int userID) {
        if (getUserName(userID) == null) {
            return "User ID: " + userID  + " doesn't exist";
        }
        return "ID: " + userID + " - User Name: " + getUserName(userID);
    }

    private String getUserName(int userID) {
        Map<Integer, String> mapUserIDName = new HashMap<>();
        mapUserIDName.put(1, "Rohan");
        mapUserIDName.put(2, "Aayan");
        mapUserIDName.put(3, "Smita");
        return mapUserIDName.get(userID);
    }

    @GetMapping ("/request-header")
    public String language(@RequestHeader (value = "Accept-Language") String language) {
        return language;
    }

//    @GetMapping ("/user")
//    public User getUser() {
//        return new User(1, "Rohan");
//    }

    @PostMapping("user/add")
    public void addUser(@RequestBody User newUser) {
     //   addUsers(newUser);
    }

//    private void addUsers(User newUser) {
//        users.put(newUser.getId(), newUser.getName());
//    }

//    @GetMapping("/users")
//    public void showAllUsers() {
//        for (Map.Entry<Integer, String> entry : users.entrySet()) {
//             System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
//        }
//    }

}
