package com.bridgeLabz.fundooNotes.model.dto;

import java.time.LocalDateTime;

/**
 * Remainder DTO class for remainder functionality
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-30
 * @version 1.0
 */
public class RemainderDTO {

	private LocalDateTime remainder;

	/**
	 * Getter method to get the remainder date
	 * 
	 * @return LocalDateTime
	 */
	public LocalDateTime getRemainder() {
		return remainder;
	}

	/**
	 * Setter method to set remainder date
	 * 
	 * @param remainder as {@link LocalDateTime}
	 */
	public void setRemainder(LocalDateTime remainder) {
		this.remainder = remainder;
	}

	/**
	 * toString method to print the data
	 */
	@Override
	public String toString() {
		return "RemainderDTO [remainder=" + remainder + "]";
	}

}
