//package com.revature.overflowingStacks.util.web.servlets;
//
//import com.revature.overflowingStacks.user.UserServices;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//
//
//// @WebServlet("/auth") // this requires a default No-Args constructor, but we make our own constructor in line 24
//@RestController
//@CrossOrigin
//@RequestMapping("/auth")
//public class AuthServlet {
//
//    private final UserServices userServices;
//
//    @Autowired
//    public AuthServlet(UserServices userServices){
//        this.userServices = userServices;
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void authorizeUser(@RequestBody LoginCreds loginCreds, HttpSession httpSession){
//        User authUser = userServices.authenticateUser(loginCreds.getUsername(), loginCreds.getPassword());
//        httpSession.setAttribute("authUser", authUser);
//    }
//
//    @DeleteMapping
//    public void logout(HttpSession session){
//        session.invalidate();
//    }
//
//}
