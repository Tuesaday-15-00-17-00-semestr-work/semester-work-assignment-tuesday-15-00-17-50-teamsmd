package lib.smd.SMDLIB.model;

import jakarta.persistence.Entity;

@Entity
public record Transaction(
		int transaction_id,
		int user_id,
		int book_id,
		String action,
		String date
		){
}
