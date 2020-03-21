package com.bridgelabz.fundoonotes.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_id")
	private long noteId;
	@Column(length = 200)
	private String title;
	@Column(length = 7000)
	private String description;
	private boolean isArchived;
	private boolean isPinned;
	private boolean isTrashed;
	@Column(length = 30)
	private String color;
	@Column(length = 30)
	private LocalDateTime createdDate;
	@Column(length = 30)
	private LocalDateTime updatedDate;
	@Column(length = 30)
	private String remainderTime;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "note_label", joinColumns = { @JoinColumn(name = "note_id") }, inverseJoinColumns = {
			@JoinColumn(name = "label_id") })
	@JsonIgnore
	private List<Label> labelsList;
	@JsonIgnore
	@ManyToMany(mappedBy = "colaboratedNotes")
	private List<User> colaboratedUsers;

	/**
	 * Getter method for id
	 * 
	 * @return Long
	 */
	public long getNoteId() {
		return noteId;
	}

	/**
	 * setter method for id
	 * 
	 * @param noteId as Long input parameter
	 */
	public void setNoteId(long noteId) {
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

	
	public List<Label> getLabelsList() {
		return labelsList;
	}

	public void setLabelsList(List<Label> labelsList) {
		this.labelsList = labelsList;
	}

	public List<User> getColaboratedUsers() {
		return colaboratedUsers;
	}

	public void setColaboratedUsers(List<User> colaboratedUsers) {
		this.colaboratedUsers = colaboratedUsers;
	}

	public String getRemainderTime() {
		return remainderTime;
	}

	public void setRemainderTime(String remainderTime) {
		this.remainderTime = remainderTime;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", title=" + title + ", description=" + description + ", isArchived="
				+ isArchived + ", isPinned=" + isPinned + ", isTrashed=" + isTrashed + ", color=" + color
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", remainderTime=" + remainderTime
				+ ", labelsList=" + labelsList + ", colaboratedUsers=" + colaboratedUsers + "]";
	}

	/**
	 * ToString method to print the data in String format
	 */
	
}
