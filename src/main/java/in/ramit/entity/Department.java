package in.ramit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Department {

	@Id
	private Integer deptNo;
	private String deptName;
	
}
