package in.ramit.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ramit.entity.Employee;
import in.ramit.repository.EmpDetailsRepository;
import in.ramit.service.CustomService;
import in.ramit.service.EmpDetailsService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private CustomService customService;
	
	@Autowired
	EmpDetailsService empDetailsService;
	
	@Autowired
	EmpDetailsRepository empDetailsRepository;
	
	@GetMapping("/dashboard")
	public String adminProfile(Principal principal, Model model) {
		
		String email=principal.getName();
		UserDetails userDetails=customService.loadUserByUsername(email);
		model.addAttribute("admin",userDetails);
		
		return "admin_dashboard";
	}
	
	@GetMapping("/dashboard/profile")
	public String userProfile1(Principal principal,Model model) {
		
		String email = principal.getName();
		Employee user = empDetailsService.getEmployeeOrAdmin(email, "ADMIN");
		model.addAttribute("user",user);
			return "admin_profile";
	}
	
	@GetMapping("/dashboard/viewAdmin")
	public String viewAdmins(Model model) {
		List<Employee> users = empDetailsService.getAllEmployeeOrAdmin("ADMIN");
		model.addAttribute("users",users);
		return "all_users";
	}
	
	@GetMapping("/dashboard/viewUsers")
	public String viewUsers(Model model) {
		List<Employee> users = empDetailsService.getAllEmployeeOrAdmin("EMPLOYEE");
		model.addAttribute("users",users);
		return "all_users";
	}
	
	@GetMapping("/dashboard/profile/update")
	 public String updateProfileForm(@RequestParam("id") Integer id,Model model) {
		model.addAttribute("id",id);
		return "update";
	}
	
	@PostMapping("/dashboard/profile/updated")
	public String updateProfile(@ModelAttribute Employee employee,Model model,Principal principal) {
		Optional<Employee> op = empDetailsRepository.findById(employee.getId().longValue());
		Employee updateProfile = null;
		if(op.isEmpty()) {
		}else {
			Employee update = op.get();
			update.setName(employee.getName());
			update.setCountry(employee.getCountry());
			update.setCity(employee.getCity());
			update.setNumber(employee.getNumber());
			updateProfile = empDetailsService.updateProfile(update);
		}
		if(updateProfile.getRole().equalsIgnoreCase("admin")) {
			return "redirect:/admin/dashboard/viewAdmin";			
		}else {
			return "redirect:/admin/dashboard/viewUsers";
		}
	}
	
	@GetMapping("/dashboard/profile/delete")
	public String deleteProfile(@RequestParam("id") Integer id,@RequestParam("role") String role) {
		empDetailsRepository.deleteById(id.longValue());
		if(role.equalsIgnoreCase("admin")) {
			return "redirect:/admin/dashboard/viewAdmin";						
		}else {
			return "redirect:/admin/dashboard/viewUsers";	
		}	
	}
}
