package in.ramit.binding;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.ramit.entity.Employee;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomUser implements UserDetails{
	
	private Employee employee;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(employee.getRole());
		return Arrays.asList(authority);
	}
	
	public String getName() {
		return employee.getName();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return employee.isEnable();
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return employee.getPassword();
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return employee.getEmail();
	}
	
	
	
}
