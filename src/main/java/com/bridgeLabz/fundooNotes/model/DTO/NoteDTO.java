package com.bridgeLabz.fundooNotes.model.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class NoteDTO {
	@NotBlank(message = "field should not be empty")
	@Pattern(regexp = "[a-zA-Z]*", message = "only alphabets are allowed")
	private String title;
	@NotBlank(message = "field should not be empty")
	@Pattern(regexp = "[a-zA-Z]*", message = "only alphabets are allowed")
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
