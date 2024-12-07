package in.ramit.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import in.ramit.entity.Employee;
import in.ramit.repository.EmpDetailsRepository;
import in.ramit.service.CustomService;
import in.ramit.service.EmpDetailsService;

@Controller
@RequestMapping("/user")
public class EmployeeController {
	
	@Autowired
 	private CustomService customService;
	
	@Autowired
	private EmpDetailsService empDetailsService;
	
	@Autowired
	private EmpDetailsRepository empDetailsRepository;
	
	@GetMapping("/dashboard")
	public String userProfile(Principal principal, Model model) {
		
		String email=principal.getName();
		UserDetails userDetails=customService.loadUserByUsername(email);
		model.addAttribute("user",userDetails);
		
	
		return "user_dashboard";
	}
	
	@GetMapping("/dashboard/profile")
	public String userProfile1(Principal principal,Model model) {
		
		String email = principal.getName();
		Employee user = empDetailsService.getEmployeeOrAdmin(email, "EMPLOYEE");
		model.addAttribute("user",user);
			return "user_profile";
	}
	
	@GetMapping("/dashboard/profile/update")
	public String updateProfileForm() {
		return "update";
	}
	
	@PostMapping("/dashboard/profile/updated")
	public String updateProfile(@ModelAttribute Employee employee,Model model,Principal principal) {
		Employee byEmail = empDetailsRepository.findByEmail(principal.getName());
		byEmail.setName(employee.getName());
		byEmail.setCountry(employee.getCountry());
		byEmail.setCity(employee.getCity());
		byEmail.setNumber(employee.getNumber());
		Employee updateUser = empDetailsService.updateProfile(byEmail);
		model.addAttribute("user",updateUser);
		return "user_profile";
	}
	
}
