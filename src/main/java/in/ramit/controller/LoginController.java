package in.ramit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}
	
//	@GetMapping("/login/dashboard")
//	public String handleLoginRequest(Principal principal, Model model) {
//		
//		String email = principal.getName();
//		UserDetails userDetails = customService.loadUserByUsername(email);
//		model.addAttribute("user",userDetails);
//		return "dashboard";
//	}
//	
}
