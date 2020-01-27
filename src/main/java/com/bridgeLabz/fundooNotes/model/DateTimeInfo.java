package com.bridgeLabz.fundooNotes.model;

import java.time.LocalDateTime;

public class DateTimeInfo {
	private LocalDateTime createdInfo;
	private LocalDateTime updatedInfo;
	private LocalDateTime remainderInfo;

	public LocalDateTime getCreatedInfo() {
		return createdInfo;
	}

	public void setCreatedInfo(LocalDateTime createdInfo) {
		this.createdInfo = createdInfo;
	}

	public LocalDateTime getUpdatedInfo() {
		return updatedInfo;
	}

	public void setUpdatedInfo(LocalDateTime updatedInfo) {
		this.updatedInfo = updatedInfo;
	}

	public LocalDateTime getRemainderInfo() {
		return remainderInfo;
	}

	public void setRemainderInfo(LocalDateTime remainderInfo) {
		this.remainderInfo = remainderInfo;
	}

}
