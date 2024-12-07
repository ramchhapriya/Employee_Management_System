package in.ramit.service;

import java.util.List;

import in.ramit.binding.EmpRegisterDetails;
import in.ramit.entity.Employee;

public interface EmpDetailsService{
	
	public Employee saveData(EmpRegisterDetails emplEmpRegisterDetails, String url);
		
	public boolean checkEmail(EmpRegisterDetails empRegisterDetails);
	
	public void sendMail(Employee employee, String url);
	
	public boolean verifyAccount(String verificationCode);
	
	public Employee forgotPassword(String email);
	
	public void sendMailForForgot(Employee employee,String url);
	
	public Employee updatePassword(Employee employee, String newPassword);

	public Employee getEmployeeOrAdmin(String email, String role);
	
	public List<Employee> getAllEmployeeOrAdmin(String role);
	
	public Employee updateProfile(Employee emp);
}
