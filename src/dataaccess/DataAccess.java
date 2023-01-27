package dataaccess;

import java.util.HashMap;
import java.util.List;

import business.Book;
import business.CheckoutRecordDTO;
import business.LibraryMember;
import business.LibrarySystemException;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess {
	public HashMap<String, Book> readBooksMap();

	public HashMap<String, User> readUserMap();

	public HashMap<String, LibraryMember> readMemberMap();

	public void saveNewMember(LibraryMember member);

	public void addBook(Book book) throws LibrarySystemException;

	public void addMember(LibraryMember member) throws LibrarySystemException;

	public void updateBook(Book book);

	public void saveToCheckoutRecord(String libraryMemberId, CheckoutRecordDTO dto);

	public List<CheckoutRecordDTO> getCheckoutRecordByMemberId(String libraryMemberId);

	public HashMap<String, List<CheckoutRecordDTO>> readCheckoutRecord();
}
