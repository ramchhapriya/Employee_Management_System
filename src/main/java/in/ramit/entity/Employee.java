package in.ramit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="EMPLOYEE_DETAILS")
public class Employee {

	
	@Id
	@Column(name="EMP_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@ManyToOne()
	@JoinColumn(name="DEPTNO")
	private Department dept;
	
	@Column(name="EMP_NAME")
	private String name;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="COUNTRY")
	private String country;
	
	@Column(name="STATE")
	private String state;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="MOBILE_NO")
	private Long number;
	
	@Column(name="PASSWORD")
	private String password;
	
	private String role;
	
	private boolean enable;
	
	private String verificationCode;
}

