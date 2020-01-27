package com.bridgeLabz.fundooNotes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Note {

	// id,title,description,pinned,remainder,color,archive,created,updated,trash,dlt
	// permantly,getallnotes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private String description;
	private boolean isArchived;
	private boolean isPinned;
	private boolean isTrashed;
	private boolean isDeletedPermanently;
	private String color;
	private DateTimeInfo dateTimeInfo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public boolean isArchived() {
		return isArchived;
	}

	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	public boolean isTrashed() {
		return isTrashed;
	}

	public void setTrashed(boolean isTrashed) {
		this.isTrashed = isTrashed;
	}

	public boolean isDeletedPermanently() {
		return isDeletedPermanently;
	}

	public void setDeletedPermanently(boolean isDeletedPermanently) {
		this.isDeletedPermanently = isDeletedPermanently;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public DateTimeInfo getDateTimeInfo() {
		return dateTimeInfo;
	}

	public void setDateTimeInfo(DateTimeInfo dateTimeInfo) {
		this.dateTimeInfo = dateTimeInfo;
	}

}
