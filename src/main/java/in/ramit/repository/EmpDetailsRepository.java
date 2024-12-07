package in.ramit.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.ramit.entity.Employee;

@Repository
public interface EmpDetailsRepository extends CrudRepository<Employee, Long> {

	public Employee findByEmail(String email);

	public boolean existsByEmail(String email);
	
	public Employee findByVerificationCode(String verificationCode);

	public List<Employee> findByRole(String role);

}
