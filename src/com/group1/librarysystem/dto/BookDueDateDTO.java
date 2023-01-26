package com.group1.librarysystem.dto;

import java.time.LocalDate;
import java.util.List;


public class BookDueDateDTO {
	
	private String ISBN;
	private String title;
	private List<BookDateInternalDTO> overDueLists;
	
	
	public String getISBN() {
		return ISBN;
	}


	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public List<BookDateInternalDTO> getOverDueLists() {
		return overDueLists;
	}


	public void setOverDueLists(List<BookDateInternalDTO> overDueLists) {
		this.overDueLists = overDueLists;
	}


	public class BookDateInternalDTO {
		public BookDateInternalDTO() {};
		private int copyNumber;
		private String memberId;
		private LocalDate dueDate;
		public int getCopyNumber() {
			return copyNumber;
		}
		public void setCopyNumber(int copyNumber) {
			this.copyNumber = copyNumber;
		}
		public String getMemberId() {
			return memberId;
		}
		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}
		public LocalDate getDueDate() {
			return dueDate;
		}
		public void setDueDate(LocalDate dueDate) {
			this.dueDate = dueDate;
		}
		
		
	}
	

}
