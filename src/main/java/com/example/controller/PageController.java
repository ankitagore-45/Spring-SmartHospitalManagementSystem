//package com.example.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import jakarta.servlet.http.HttpSession;
//
//@Controller
//public class PageController {
//
//    @GetMapping("/appointment-page")
//    public String appointmentPage(HttpSession session) {
//
//    	if(session.getAttribute("email") == null){
//
//            return "redirect:/login.html";
//        }
//
//        return "appointment";
//    }
//
//}

package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    @GetMapping("/appointment-page")
    public String appointmentPage(HttpSession session) {

        Object role = session.getAttribute("role");

        if(role == null) {

            return "redirect:/login.html";
        }

        return "appointment";
    }
}