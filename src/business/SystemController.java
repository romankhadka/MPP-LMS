package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.group1.librarysystem.dto.BookDueDateDTO;
import com.group1.librarysystem.dto.CheckoutRecordDTO;
import com.group1.librarysystem.dto.BookDueDateDTO.BookDateInternalDTO;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws LoginException {
		if (id.isEmpty())
			throw new LoginException("ID is empty");
		if (password.isEmpty())
			throw new LoginException("Password is empty");
		System.out.println("IN add login>>"+id+" "+password);

		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
	}
	


	
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}




	@Override
	public void addBook(Book book) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		da.addBook(book);
	}




	@Override
	public void addMember(LibraryMember member) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		da.addMember(member);
	}




	@Override
	public BookCopy checkoutBook(String libraryMemberId, String isbn) throws Exception {
		if (!checkIfLoginIdExists(libraryMemberId))
			throw new Exception("You're not a member of our library");

		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookMap = da.readBooksMap();
		if (!bookMap.containsKey(isbn))
			throw new Exception("Book does not exist!!!");

		Book book = bookMap.get(isbn);
		if (!book.isAvailable())
			throw new Exception("Requested book with ISBN " + isbn + " is not available");
		System.out.println(libraryMemberId+" hello "+isbn);
		return addToCheckout(book.getNextAvailableCopy(), libraryMemberId);
	}




	private BookCopy addToCheckout(BookCopy bookCopy, String libraryMemberId) throws Exception {
		CheckoutRecordDTO dto = new CheckoutRecordDTO(bookCopy, libraryMemberId);
		System.out.println("Member ID"+dto.getMemberId());
		DataAccess da = new DataAccessFacade();
		try {
			da.saveToCheckoutRecord(libraryMemberId, dto);
			HashMap<String, Book> bookMap = da.readBooksMap();
			Book book = bookCopy.getBook();
			bookCopy.changeAvailability();
			book.updateCopies(bookCopy);
			bookMap.put(book.getIsbn(), book);
			da.updateBook(book);
		} catch (Exception e) {
			throw new Exception("Checkout Error");
		}
		return bookCopy;
	}




	private boolean checkIfLoginIdExists(String libraryMemberId) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> map = da.readMemberMap();
		return map.containsKey(libraryMemberId) ? true : false;
	}




	@Override
	public List<CheckoutRecordDTO> getCheckoutRecordByMemberId(String libraryMemberId) throws Exception {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> libMemberMap = da.readMemberMap();
		if(!libMemberMap.containsKey(libraryMemberId))
			throw new Exception("Member Id does not exist!!");
			List<CheckoutRecordDTO> checkoutList = da.getCheckoutRecordByMemberId(libraryMemberId);
			return checkoutList;
		
		
		
	}




	@Override
	public BookDueDateDTO getOverdueBooks(String isbnNumber) throws LibrarySystemException {
		BookDueDateDTO response = new BookDueDateDTO();
		DataAccess da = new DataAccessFacade();
		HashMap<String, List<CheckoutRecordDTO>> checkoutRecordList = da.readCheckoutRecord();
		List<BookDateInternalDTO> overDueList = new ArrayList<>();
		LocalDate currentDate = LocalDate.now();
		HashMap<String, Book> bookMap = da.readBooksMap();
		Book book = bookMap.get(isbnNumber);
		if(book==null) {
			throw new LibrarySystemException("No such book exist");
		}
		String title = book.getTitle();
		
		checkoutRecordList.forEach((k, v) -> {
			v.forEach(e -> {
				if ((e.getBookCopy().getBook().getIsbn().equals(isbnNumber)) && ((e.getDueDate()).isBefore(currentDate))) {
					int copyNumber = e.getBookCopy().getCopyNum();
					String memberId = e.getMemberId();
					LocalDate dueDate = e.getDueDate();
					
					BookDateInternalDTO record =  response.new BookDateInternalDTO();
					record.setCopyNumber(copyNumber);
					record.setMemberId(memberId);
					record.setDueDate(dueDate);

					
					overDueList.add(record);
				}
			});
		});
		
		response.setISBN(isbnNumber);
		response.setOverDueLists(overDueList);
		response.setTitle(title);

		return response;
	}




	@Override
	public Book getBook(String isbn) throws Exception {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookMap = da.readBooksMap();
		if (!bookMap.containsKey(isbn))
			throw new Exception("No book found, couldn't add new copy");
		Book book = bookMap.get(isbn);
		return book;
	}




	@Override
	public Book addNewBookCopy(String isbn) throws Exception {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookMap = da.readBooksMap();
		if (!bookMap.containsKey(isbn))
			throw new Exception("No book found, couldn't add new copy");
		Book b = bookMap.get(isbn);
		b.addCopy();
		da.updateBook(b);
		
		return b;
	}
	
	
}
