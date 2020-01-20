package com.bridgeLabz.fundooNotes.DTOModel;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UserDTO {

//	@NotBlank(message="field should not be empty")
//	@Pattern(regexp="[a-zA-Z]*",message="only alphabets are allowed")
	private String name;

//	@Email
	private String email;

//	@NotBlank(message="field should not be empty")
//	@Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",message="Enter a valid password")
	private String password;

//	@NotNull(message="field should not be empty")
	private Long mobileNumber;

	public UserDTO() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
