package lib.smd.SMDLIB.model;

import jakarta.persistence.Entity;

@Entity
public record Book(
		int book_id, 
		String title, 
		String author, 
		int isbn, 
		int available_copies
		){
}
