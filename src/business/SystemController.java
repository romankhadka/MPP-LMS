package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.BookDueDateDTO.BookDateInternalDTO;
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

		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
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
			throw new Exception("Not a member of library");

		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookMapList = da.readBooksMap();
		if (!bookMapList.containsKey(isbn))
			throw new Exception("Book does not exist!");

		Book book = bookMapList.get(isbn);
		if (!book.isAvailable())
			throw new Exception("Requested book with ISBN " + isbn + " is not available");
		return addToCheckout(book.getNextAvailableCopy(), libraryMemberId);
	}

	private BookCopy addToCheckout(BookCopy bookCopy, String libraryMemberId) throws Exception {
		CheckoutRecordDTO dto = new CheckoutRecordDTO(bookCopy, libraryMemberId);
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
		HashMap<String, LibraryMember> mapList = da.readMemberMap();
		return mapList.containsKey(libraryMemberId) ? true : false;
	}

	@Override
	public List<CheckoutRecordDTO> getCheckoutRecordByMemberId(String libraryMemberId) throws Exception {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> libMemberMap = da.readMemberMap();
		if (!libMemberMap.containsKey(libraryMemberId))
			throw new Exception("Member Id does not exist!!");
		List<CheckoutRecordDTO> checkoutList = da.getCheckoutRecordByMemberId(libraryMemberId);
		return checkoutList;

	}

	@Override
	public BookDueDateDTO getOverdueBooks(String isbnNumber) throws LibrarySystemException {
		BookDueDateDTO bookDueDateDto = new BookDueDateDTO();
		DataAccess da = new DataAccessFacade();
		HashMap<String, List<CheckoutRecordDTO>> checkoutRecordList = da.readCheckoutRecord();
		List<BookDateInternalDTO> overDueList = new ArrayList<>();
		HashMap<String, Book> bookMap = da.readBooksMap();
		Book book = bookMap.get(isbnNumber);
		if (book == null) 
			throw new LibrarySystemException("No such book exist");
		
		String title = book.getTitle();
		checkoutRecordList.forEach((key, value) -> {
			value.forEach(p -> {
				if ((p.getBookCopy().getBook().getIsbn().equals(isbnNumber))
						&& ((p.getDueDate()).isBefore(LocalDate.now()))) {
					int copyNumber = p.getBookCopy().getCopyNum();
					String memberId = p.getMemberId();
					LocalDate dueDate = p.getDueDate();
					BookDateInternalDTO dto = bookDueDateDto.new BookDateInternalDTO();
					dto.setCopyNumber(copyNumber);
					dto.setMemberId(memberId);
					dto.setDueDate(dueDate);
					overDueList.add(dto);
				}
			});
		});

		bookDueDateDto.setIsbn(isbnNumber);
		bookDueDateDto.setOverDueLists(overDueList);
		bookDueDateDto.setTitle(title);
		return bookDueDateDto;
	}

	@Override
	public Book getBook(String isbn) throws Exception {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookMapList = da.readBooksMap();
		if (!bookMapList.containsKey(isbn))
			throw new Exception("Book not found "+isbn);
		Book book = bookMapList.get(isbn);
		return book;
	}

	@Override
	public Book addNewBookCopy(String isbn) throws Exception {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookMapList = da.readBooksMap();
		if (!bookMapList.containsKey(isbn))
			throw new Exception("Book not found "+isbn);
		Book book = bookMapList.get(isbn);
		book.addCopy();
		da.updateBook(book);
		return book;
	}

}
