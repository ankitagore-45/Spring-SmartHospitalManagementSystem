//package com.example.controller;
//
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.entity.User;
//import com.example.service.UserService;
//
//@RestController
//@RequestMapping("/user")
//@CrossOrigin("*")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    
//    // Register User
//
//    @PostMapping("/register")
//    public User registerUser(@RequestBody User user) {
//
//        return userService.registerUser(user);
//    }
//
//
//    // Login User
//
//    @PostMapping("/login")
//    public User loginUser(@RequestBody User user) {
//
//        return userService.loginUser(
//                user.getEmail(),
//                user.getPassword()
//        );
//    }
//
//
//    // Get All Users
//
//    @GetMapping("/all")
//    public List<User> getAllUsers() {
//
//        return userService.getAllUsers();
//    }
//
//
//    // Get User By Id
//
//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable Long id) {
//
//        return userService.getUserById(id);
//    }
//
//
//    // Update User
//
//    @PutMapping("/update/{id}")
//    public User updateUser(
//            @PathVariable Long id,
//            @RequestBody User updatedUser
//    ) {
//
//        return userService.updateUser(id, updatedUser);
//    }
//
//
//    // Delete User
//
//    @DeleteMapping("/delete/{id}")
//    public String deleteUser(@PathVariable Long id) {
//
//        userService.deleteUser(id);
//
//        return "User Deleted Successfully";
//    }
//
//}


package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    // Register
		    @PostMapping("/register")
		    public String registerUser(@ModelAttribute User user) {
		        
		    	user.setRole("PATIENT");   
		        userService.registerUser(user);
		
		        return "redirect:/login.html";
		    }

		    @PostMapping("/login")
		    public String loginUser(@RequestParam String email,
		                            @RequestParam String password,
		                            HttpServletRequest request) {

		        try {

		            // get logged in user
		            User user = userService.loginUser(email, password);

		            // create session
		            HttpSession session = request.getSession();

		            // store data in session
		            session.setAttribute("name", user.getFullName());
		            session.setAttribute("role", user.getRole());
		            session.setAttribute("email", user.getEmail());

		            // role-based redirect
		            if(user.getRole().equals("ADMIN")) {
		                return "redirect:/user/admin/dashboard";
		            }

		            else if(user.getRole().equals("DOCTOR")) {
		                return "redirect:/user/doctor/dashboard";
		            }

		            else if(user.getRole().equals("MEDICAL")) {

		                return "redirect:/user/medical/dashboard";
		            }

		            else {

		                return "redirect:/user/patient/dashboard";
		            }
		        } catch (Exception e) {

		        	return "redirect:/login.html?error=true";
		        }
		    }
		    
		    @GetMapping("/patient/dashboard")
		    public String patientDashboard() {
		        return "patient-dashboard";
		    }

		    @GetMapping("/admin/dashboard")
		    public String adminDashboard() {
		        return "admin-dashboard";
		    }

		    @GetMapping("/doctor/dashboard")
		    public String doctorDashboard() {
		        return "doctor-dashboard";
		    }
		    
		    @GetMapping("/medical/dashboard")
		    public String medicalDashboard() {

		        return "medical-dashboard";
		    }
		    
				
    @ResponseBody
    @GetMapping("/all")
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }
    
    @GetMapping("/my-prescription")
    public String myPrescriptionPage() {

        return "my-prescriptions";
    }
    @GetMapping("/all-patients")
    public String allPatientsPage() {
        return "all-patients";
    }
    

    // Get User By ID
    @ResponseBody
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {

        return userService.getUserById(id);
    }

    // Update User
    @ResponseBody
    @PutMapping("/update/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser) {

        return userService.updateUser(id, updatedUser);
    }

    // Delete User
    @ResponseBody
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return "User Deleted Successfully";
    }
    
    
  //to show Patient name in corner
    @GetMapping("/logged-Patient")
    @ResponseBody
    public String getLoggedPatient(HttpSession session) {

        String fullName =
                (String) session.getAttribute("name");

        if(fullName == null){
            return "Patient";
        }

        return fullName;
    }
    
    
    //to logout properly
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if(session != null) {
            session.invalidate();
        }

        return "redirect:/index.html";
    }
    
    //for forgot password
    @PostMapping("/forgot-password")
    @ResponseBody
    public String forgotPassword(
            @RequestParam String email,
            @RequestParam String password) {

        Optional<User> optionalUser =
                userRepository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            return "Email Not Found";
        }

        User user = optionalUser.get();

        user.setPassword(password);

        userRepository.save(user);

        return "Password Updated Successfully";
    }

}