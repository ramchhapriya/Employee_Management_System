package in.ramit.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.ramit.binding.EmpRegisterDetails;
import in.ramit.entity.Employee;
import in.ramit.repository.EmpDetailsRepository;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmpDetailsServiceImp implements EmpDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JavaMailSender mailSender;
	
	private EmpDetailsRepository empDetailsRepository;
	EmpDetailsServiceImp(EmpDetailsRepository empDetailsRepository){
		this.empDetailsRepository = empDetailsRepository;
	}

	@Override
	public Employee saveData(EmpRegisterDetails empRegisterDetails,String url) {
		
		Employee employee = new Employee();
		employee.setName(empRegisterDetails.getName());
		employee.setEmail(empRegisterDetails.getEmail());
		employee.setCountry(empRegisterDetails.getCountry());
		employee.setState(empRegisterDetails.getState());
		employee.setCity(empRegisterDetails.getCity());
		employee.setNumber(empRegisterDetails.getNumber());
		employee.setRole("EMPLOYEE");
		employee.setPassword(passwordEncoder.encode(empRegisterDetails.getPassword()));
		employee.setEnable(false);
		employee.setVerificationCode(UUID.randomUUID().toString());

		 employee =empDetailsRepository.save(employee);
		 
		 if(employee!=null) {
			 sendMail(employee, url);
		 }
		 
		 return employee;
	}

	@Override
	public boolean checkEmail(EmpRegisterDetails empRegisterDetiails) {
		
		return empDetailsRepository.existsByEmail(empRegisterDetiails.getEmail());
	}

	@Override
	public void sendMail(Employee employee, String url) {

		String from = "ramchhapriya12345@gmail.com";
		String to = employee.getEmail();
		String subject = "Account verication";
		String content = "Dear [[name]],<br>"+"Please click the linck below to verify your registration:<br>"
						 + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
						 +"Thank you,<br>"
						 +"RamIT";
		
		try {
			
			MimeMessage message=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message);
			
			helper.setFrom(from,"Ram");
			helper.setSubject(subject);
			helper.setTo(to);
			
			content = content.replace("[[name]]", employee.getName());
			String siteUrl = url+"/verify?code="+employee.getVerificationCode();
			
			System.out.println(siteUrl);
			
			content = content.replace("[[URL]]", siteUrl);
			helper.setText(content,true);
			
//			mailSender.send(message);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean verifyAccount(String verificationCode) {

		Employee employee = empDetailsRepository.findByVerificationCode(verificationCode);
		
		if(employee!=null) {
			employee.setEnable(true);	
			employee.setVerificationCode(null);
			
			empDetailsRepository.save(employee);
			return true;
		}
		else
			return false;
		
	}

	@Override
	public Employee forgotPassword(String email) {
		 Employee employee = empDetailsRepository.findByEmail(email);
		return employee;
	}

	@Override
	public void sendMailForForgot(Employee employee, String url) {
		
		String verificationCode = UUID.randomUUID().toString();
		String from = "ramchhapriya12345@gmail.com";
		String to = employee.getEmail();
		String subject = "Account forgot Password";
		String content = "Dear [[name]],<br>"+"Please click the linck below to forgot your account password:<br>"
				 + "<h3><a href=\"[[URL]]\" target=\"_self\">forgot password</a></h3>"
				 +"Thank you,<br>"
				 +"RamIT";
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message);
			
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			
			content = content.replace("[[name]]", employee.getName());
			String siteUrl = url+"/account/forgot/reset/key/"+to+"/"+verificationCode;
			System.out.println(siteUrl);
			
			content = content.replace("[[URL]]", siteUrl);
			messageHelper.setText(content,true);
			
//			mailSender.send(message);	
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Employee updatePassword(Employee employee, String newPassword) {
		newPassword = passwordEncoder.encode(newPassword);
		employee.setPassword(newPassword);
		
		return empDetailsRepository.save(employee);
	}

	@Override
	public Employee getEmployeeOrAdmin(String email, String role) {
		return empDetailsRepository.findByEmail(email);
		
	}

	@Override
	public List<Employee> getAllEmployeeOrAdmin(String role) {
		return empDetailsRepository.findByRole(role);
	}

	@Override
	public Employee updateProfile(Employee emp) {
		return  empDetailsRepository.save(emp);
	}
	
}
