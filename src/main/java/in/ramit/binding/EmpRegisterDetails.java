package in.ramit.binding;

import lombok.Data;

@Data
public class EmpRegisterDetails {

	private String name;
	private String email;
	private String country;
	private String state;
	private String city;
	private Long number;
	private String password;
}
