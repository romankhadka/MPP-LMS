package business;

import java.io.Serializable;
import java.util.List;

public class CheckoutRecord implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private List<CheckoutRecordDTO> checkoutRecordDTOList;
	private LibraryMember libmember;
	
	
	
	public CheckoutRecord(List<CheckoutRecordDTO> checkoutRecordDTOList, LibraryMember libmember) {
		super();
		this.checkoutRecordDTOList = checkoutRecordDTOList;
		this.libmember = libmember;
	}
	public List<CheckoutRecordDTO> getCheckoutRecordDTOList() {
		return checkoutRecordDTOList;
	}
	public void setCheckoutRecordDTOList(List<CheckoutRecordDTO> checkoutRecordDTOList) {
		this.checkoutRecordDTOList = checkoutRecordDTOList;
	}
	public LibraryMember getLibmember() {
		return libmember;
	}
	public void setLibmember(LibraryMember libmember) {
		this.libmember = libmember;
	}
	
	

}
