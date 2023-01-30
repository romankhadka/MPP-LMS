package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public void addBook(Book book) throws LibrarySystemException;
	public void addMember(LibraryMember member) throws LibrarySystemException;
	public BookCopy checkoutBook(String libraryMemberId, String isbn) throws Exception;
	public List<CheckoutRecordDTO> getCheckoutRecordByMemberId(String libraryMemberId) throws Exception;
	public BookDueDateDTO getOverdueBooks(String isbnNumber) throws LibrarySystemException;
	public Book getBook(String isbn) throws Exception;
	public Book addNewBookCopy(String isbn) throws Exception;
	
}
