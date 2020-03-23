package com.bridgelabz.fundoonotes.model.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author Durgasankar Mishra
 * @created 2020-03-01
 * @version 1.0
 *
 */
public class LabelDTO {

	@ApiModelProperty(notes = "Name of the label", name = "label name")
	private String labelName;

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

}
