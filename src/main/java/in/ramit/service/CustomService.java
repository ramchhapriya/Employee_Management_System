package in.ramit.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.ramit.binding.CustomUser;
import in.ramit.entity.Employee;
import in.ramit.repository.EmpDetailsRepository;

@Service
public class CustomService implements UserDetailsService {

	EmpDetailsRepository empDetailsRepository;
	
	public CustomService(EmpDetailsRepository userDetailsRepository) {
		this.empDetailsRepository=userDetailsRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Employee employee = empDetailsRepository.findByEmail(username);
		System.out.println(employee);
		if(employee == null)
			 throw new UsernameNotFoundException("Username or password not found");
		return new CustomUser(employee);
		
	}
	
}
