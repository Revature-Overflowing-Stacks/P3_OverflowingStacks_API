package com.revature.overflowingStacks.user;

import com.revature.overflowingStacks.util.interfaces.Authable;

import com.revature.overflowingStacks.util.web.dto.CodeCheck;
import dev.turingcomplete.kotlinonetimepassword.GoogleAuthenticator;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.overflowingStacks.util.web.dto.ResetPasswordCreds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserServlet implements Authable {
    private final UserServices userServices;

    @Autowired
    public UserServlet(UserServices userServices){
        this.userServices=userServices;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User newUser = userServices.create(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @PutMapping("/resetPassword")
    public String resetPassword(@RequestBody ResetPasswordCreds rpc){

        User oldPassword = userServices.readById(rpc.getEmail());

        oldPassword.setPassword(rpc.getNewpassword());

        User newPassword = userServices.update(oldPassword);

        String message = "Your password has been reset";
        return message;
    }


    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user){

        val secret = GoogleAuthenticator.Companion.createRandomSecret();

        System.out.println(secret);
        user.setSecret(secret);
        User newUser = userServices.create(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/authCheck")
    public ResponseEntity<User> checkAuth(@RequestBody CodeCheck codeCheck){

        User user = userServices.readById(codeCheck.getEmail());
        System.out.println(user + "0");
        String usersSecret = user.getSecret();
        System.out.println(usersSecret + "1");
        String sixCode = userServices.getTOTPCode(usersSecret);
        System.out.println(sixCode);

        System.out.println("---------------------");
        System.out.println(codeCheck.getCode());

        if(codeCheck.getCode().equals(sixCode)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else if(!codeCheck.getCode().equals(sixCode)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }


}
