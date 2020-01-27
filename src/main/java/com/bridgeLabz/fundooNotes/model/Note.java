package com.bridgeLabz.fundooNotes.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Note model which has the parameters, which will hit with the database
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-27
 * @version 1.0
 */
@Entity
@Table(name = "note_details")
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "note_id")
	private long noteId;
	private String title;
	private String description;
	private boolean isArchived;
	private boolean isPinned;
	private boolean isTrashed;
	private String color;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private LocalDateTime remainderDate;

	/**
	 * Getter method for id
	 * 
	 * @return Long
	 */
	public long getId() {
		return noteId;
	}

	/**
	 * setter method for id
	 * 
	 * @param noteId as Long input parameter
	 */
	public void setId(long noteId) {
		this.noteId = noteId;
	}

	/**
	 * Getter method for Title
	 * 
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter method
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter method for note description
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter method for setting the description.
	 * 
	 * @param description as String input parameter
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter method for isAchieved
	 * 
	 * @return Boolean
	 */
	public boolean isArchived() {
		return isArchived;
	}

	/**
	 * Setter method for is achieved
	 * 
	 * @param isArchived as Boolean input parameter
	 */
	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	/**
	 * Getter method for is pinned
	 * 
	 * @return Boolean
	 */
	public boolean isPinned() {
		return isPinned;
	}

	/**
	 * Setter method for is pinned
	 * 
	 * @param isPinned as Boolean input parameter
	 */
	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	/**
	 * Getter method for is Trashed
	 * 
	 * @return Boolean
	 */
	public boolean isTrashed() {
		return isTrashed;
	}

	/**
	 * Setter method for is achieved
	 * 
	 * @param isArchived as Boolean input parameter
	 */
	public void setTrashed(boolean isTrashed) {
		this.isTrashed = isTrashed;
	}

	/**
	 * Getter method for fetching colors
	 * 
	 * @return String
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Setter method for setting colors
	 * 
	 * @param color as String input parameter
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Getter method to get created date and time.
	 * 
	 * @return LocalDateTime
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * Setter method to set created date.
	 * 
	 * @param createdDate as LocalDateTime input parameter
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Getter method to get updated date and time.
	 * 
	 * @return LocalDateTime
	 */
	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * Setter method to set updated date.
	 * 
	 * @param updatedDate as LocalDateTime input parameter
	 */
	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * Getter method to get remainder date and time.
	 * 
	 * @return LocalDateTime
	 */
	public LocalDateTime getRemainderDate() {
		return remainderDate;
	}

	/**
	 * Setter method to set remainder date.
	 * 
	 * @param remainderDate as LocalDateTime input parameter
	 */
	public void setRemainderDate(LocalDateTime remainderDate) {
		this.remainderDate = remainderDate;
	}

	/**
	 * ToString method to print the data in String format
	 */
	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", title=" + title + ", description=" + description + ", isArchived="
				+ isArchived + ", isPinned=" + isPinned + ", isTrashed=" + isTrashed + ", color=" + color
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", remainderDate=" + remainderDate
				+ "]";
	}

}
