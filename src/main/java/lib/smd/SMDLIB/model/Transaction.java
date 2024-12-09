package lib.smd.SMDLIB.model;

public record Transaction(
		int transaction_id,
		String user_id,
		String book_id,
		String action,
		String date
		){
}
