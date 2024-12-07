package in.ramit.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.ramit.binding.EmpRegisterDetails;
import in.ramit.entity.Employee;
import in.ramit.service.EmpDetailsService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {
	
	private EmpDetailsService empDetailsService;
	
	public RegisterController(EmpDetailsService empDetailsService) {
		this.empDetailsService=empDetailsService;
	}
	
	@GetMapping("/register")
	public String getRegisterForm() {
		
		return "register"; //it will go to register.html
	}
	
	@PostMapping(value="/result")
	public String handleRegisterButton(@ModelAttribute EmpRegisterDetails empRegisterDetails, Model model, HttpServletRequest request) {
		
		String url = request.getRequestURL().toString();
		System.out.println(url);
		
		url = url.replace(request.getServletPath(), "");
		System.out.println(url);
		
		if(empDetailsService.checkEmail(empRegisterDetails)) {
			
			model.addAttribute("email","Email already exist");
			return "register";
		}
		else {
			Employee user = empDetailsService.saveData(empRegisterDetails, url); //to save data to database
			if(user!=null) {
				model.addAttribute("msg","Registration sucessfully completed"); //to print msg on wepage
				System.out.println("Registration Successfull");				
			}
			else {
				System.out.println("Internal server error");
			}
		}
		return "success";

	}
	
	
	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code, Model m) {
		
		boolean ans = empDetailsService.verifyAccount(code);
		
		if (ans) {
			m.addAttribute("msg", "Sucessfully your account is verified");
		} else {
			m.addAttribute("msg", "may be your vefication code is incorrect or already veified ");
		}
		
		return "success";
	}
}
