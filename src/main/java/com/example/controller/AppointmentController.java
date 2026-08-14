package com.example.controller;
	
	import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Appointment;
import com.example.repository.AppointmentRepository;
import com.example.repository.DoctorRepository;
import com.example.repository.UserRepository;
import com.example.service.AppointmentService;
import com.example.service.DoctorService;
import com.example.service.UserService;

import jakarta.servlet.http.HttpSession;
	
	@Controller
	@RequestMapping("/appointment")
	@CrossOrigin("*")
	public class AppointmentController {
	
	    @Autowired
	    private AppointmentService appointmentService;
	    
	    //for dynamic patient count of cards 
	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private DoctorRepository doctorRepository;

	    @Autowired
	    private AppointmentRepository appointmentRepository;	
	    
	    //for dr count 
	    @Autowired
	    private DoctorService doctorService;

	    @Autowired
	    private UserService userService;
	    
	    //
	    @PostMapping("/book")
	    public String bookAppointment(@ModelAttribute Appointment appointment,
	                                  HttpSession session) {

	        String email = (String) session.getAttribute("email");

	        if(email == null) {
	            return "redirect:/login.html";
	        }

	        appointment.setEmail(email);

	        appointmentService.bookAppointment(appointment);

	        return "patient-dashboard";
	    }
	    
	    @GetMapping("/patient-dashboard")
	    public String patientDashboard() {
	        return "redirect:/patient-dashboard.html";
	    }
	    @ResponseBody
	    @GetMapping("/all")
	    public List<Appointment> getAllAppointments() {
	        return appointmentService.getAllAppointments();
	    }
	
	    @GetMapping("/all-appointments")
	    public String allAppointmentsPage() {
	        return "all-appointments";
	    }
	    
	    @PutMapping("/update/{id}")
	    public Appointment updateAppointment(
	            @PathVariable Long id,
	            @RequestBody Appointment appointment
	    ) {
	        return appointmentService.updateAppointment(id, appointment);
	    }
	
	    @DeleteMapping("/delete/{id}")
	    public String deleteAppointment(@PathVariable Long id) {
	
	        appointmentService.deleteAppointment(id);
	
	        return "Appointment Deleted Successfully";
	    }
	    
	//    @PostMapping("/myappointments")
	//    public List<Appointment> getMyAppointments(HttpSession session) {
	//
	//        String email = (String) session.getAttribute("email");
	//
	//      
	//		return appointmentService.getAppointmentsByEmail(email);
	//    }
	 
	    
	    @GetMapping("/myappointments")
	    @ResponseBody
	    public List<Appointment> getMyAppointments(HttpSession session) {

	        String email = (String) session.getAttribute("email");

	        if (email == null) {
	            return List.of();
	        }

	        return appointmentService.getAppointmentsByEmail(email);
	    }
	// Get All Users
	    
	    @GetMapping("/counts")//not working need to check but 1st let me create dr dashboard
	    public Map<String, Long> getCounts() {
	
	        Map<String, Long> map = new HashMap<>();
	
	        List<Appointment> all = appointmentService.getAllAppointments();
	
	        System.out.println("TOTAL DATA = " + all.size());
	
	        // Normalize status safely
	        long upcoming = all.stream()
	                .filter(a -> {
	                    if (a.getStatus() == null) return false;
	
	                    String status = a.getStatus().trim().toLowerCase();
	
	                    return status.equals("booked") || status.equals("approved");
	                })
	                .count();
	
	        map.put("totalAppointments", (long) all.size());
	        map.put("upcomingVisits", upcoming);
	
	        map.put("prescriptions", (long) (all.size() / 2));
	
	        return map;
	    }
	    
	    @GetMapping("/admin-counts")
	    @ResponseBody
	    public Map<String, Long> getAdminCounts() {

	        Map<String, Long> map = new HashMap<>();

	        map.put("patients",
	                (long) userService.getAllUsers().size());

	        map.put("doctors",
	                (long) doctorService.getAllDoctors().size());

	        map.put("appointments",
	                (long) appointmentService.getAllAppointments().size());
	        
	        long pending =
	                appointmentService.getAllAppointments()
	                .stream()
	                .filter(a -> {

	                    if(a.getStatus() == null)
	                        return false;

	                    String status =
	                            a.getStatus().trim().toUpperCase();

	                    return status.equals("BOOKED");

	                })
	                .count();

	        map.put("pending", pending);

	        return map;
	    }
}
	
