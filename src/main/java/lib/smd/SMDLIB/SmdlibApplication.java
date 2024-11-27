package lib.smd.SMDLIB;

import java.awt.print.Book;

import org.slf4j.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import Database.DatabaseConnection;

@SpringBootApplication
public class SmdlibApplication {
	
	private static DatabaseConnection DBC;
	
	private static final Logger log = LoggerFactory.getLogger(SmdlibApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SmdlibApplication.class, args);
		log.info(DBC.connectToDB());
		log.info("ZMENA!");
	}
	
	@Bean
	CommandLineRunner runner() {
		return args -> {
			Book book = new Book();
			log.info(book.toString());
		};
	}

}
