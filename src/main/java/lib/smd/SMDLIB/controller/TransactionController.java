package lib.smd.SMDLIB.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lib.smd.SMDLIB.model.Transaction;
import lib.smd.SMDLIB.repo.TransactionRepo;

@RestController
@RequestMapping("/lib/transactions")
public class TransactionController {
	
	private final TransactionRepo transRep;
	
	public TransactionController(TransactionRepo transRep) {
		this.transRep = transRep;
	}
	
	@GetMapping("/admin/all")
	List<Transaction> findAllTransactions(){
		return transRep.displayTable();
	}
	
	@GetMapping("/admin/{id}")
	Transaction findTransactionByID(@PathVariable int id) {
		return transRep.displayTransaction(id);
	}
	
	@GetMapping("/user/{user}")
	List<Transaction> findTransactionForUser(@PathVariable int user){
		return transRep.displayTransactionForUser(user);
	}
	
	//after this you have to make a request for UPDATE bookcount
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/user/addtrans")
	void createTransaction(@RequestBody int transaction_id, int user_id, int book_id, String action, String date) {
		transRep.addTransActionToDB(transaction_id, user_id, book_id, action, date);
	}
	
	@ResponseStatus(HttpStatus.GONE)
	@DeleteMapping("/admin/deletetrans")
	void deleteTransaction(@RequestBody int transaction_id) {
		transRep.deleteTransFromDB(transaction_id);
	}
}
