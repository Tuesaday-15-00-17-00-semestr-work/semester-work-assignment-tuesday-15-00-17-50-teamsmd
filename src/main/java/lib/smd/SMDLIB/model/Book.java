package lib.smd.SMDLIB.model;

public record Book(
		int book_id, 
		String title, 
		String author, 
		int isbn, 
		int available_copies
		){
}
