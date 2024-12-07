package in.ramit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.ramit.entity.Employee;
import in.ramit.repository.EmpDetailsRepository;
import in.ramit.service.EmpDetailsService;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class ForgotContoller {

	@Autowired
	EmpDetailsService empDetailsService;
	
	@Autowired
	EmpDetailsRepository empDetailsRepository;
	
	@GetMapping("/account/forgot")
	public String getForgetPage() {
		
		return "forgot";
	}
	
	@PostMapping("/account/forgot/reset")
	public String handleForgot(Employee employee,Model model, HttpServletRequest request) {
		
		String url = request.getRequestURL().toString();
		System.out.println(url);
		
		url = url.replace(request.getServletPath(), "");
		System.out.println(url);
		
		employee = empDetailsService.forgotPassword(employee.getEmail());
		if(employee!=null) {
			empDetailsService.sendMailForForgot(employee, url);	
			model.addAttribute("msg","We have mail to you email account, pleace check.");
		}
		else {
			model.addAttribute("msg","Maybe your email is not registerd.");

		}
			
		return "success";
		
	}
	
	@GetMapping("account/forgot/reset/key/{code}/{email}")
	public String getNewPassword(@PathVariable String code, @PathVariable String email) {
		
		return "newPassword";
	}
	
	@PostMapping("account/forgot/reset/key/{email}/done")
	public String resetPassword(@PathVariable String email, Employee employee,Model model) {
		Employee updateEmployee = empDetailsService.forgotPassword(email);
		String newPassword = employee.getPassword();
		System.out.println(newPassword);
		if(updateEmployee!=null) {
			System.out.println(empDetailsService.updatePassword(updateEmployee,newPassword));
			model.addAttribute("msg","Successfully your password is reset");
		}
		else {
			model.addAttribute("msg","may be your vefication code is incorrect or email not registered or link is");
		}
		return "success";
	}
}
