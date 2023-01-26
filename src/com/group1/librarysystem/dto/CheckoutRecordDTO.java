package com.group1.librarysystem.dto;

import java.io.Serializable;
import java.time.LocalDate;

import business.BookCopy;

public class CheckoutRecordDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private BookCopy bookCopy;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private double fineAmount;
	private LocalDate bookReturnDate;
	private String memberId;
	
	public CheckoutRecordDTO(BookCopy bookCopy, String libMemberId) {
		this.bookCopy = bookCopy;
		this.checkoutDate = LocalDate.now();
		this.dueDate = LocalDate.now().plusDays(bookCopy.getBook().getMaxCheckoutLength());
		this.memberId = libMemberId;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public double getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(double fineAmount) {
		this.fineAmount = fineAmount;
	}

	public LocalDate getBookReturnDate() {
		return bookReturnDate;
	}

	public void setBookReturnDate(LocalDate bookReturnDate) {
		this.bookReturnDate = bookReturnDate;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	

}
