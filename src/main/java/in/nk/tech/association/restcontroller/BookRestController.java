package in.nk.tech.association.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nk.tech.association.entity.Book;
import in.nk.tech.association.service.BookService;

@RestController
@RequestMapping("/api/bms")
public class BookRestController {

	@Autowired
	private BookService bookService;

	@PostMapping("/{email}")
	public ResponseEntity<String> insertBook(@RequestBody Book book, @PathVariable String email) {
		boolean insertedBook = bookService.saveBook(book, email);
		if (insertedBook) {
			return ResponseEntity.ok("Author "+email+" Published Book, book name - "+book.getBookName());
		}
		return ResponseEntity.badRequest().body("Unbale to publish book - "+book.getBookName());
	}

	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	
	@PutMapping("/{email}")
	public ResponseEntity<?> updateBook(@RequestBody Book book, @PathVariable String email){
		boolean isUpdatedBook = bookService.updateBook(book, email);
		if(isUpdatedBook) {
			return ResponseEntity.ok("Book Updated");
		}
		return ResponseEntity.badRequest().body("Unbale to Update book");
	}
	
	@DeleteMapping("/{id}/{email}")
	public ResponseEntity<?> deleteBook(@PathVariable String id, @PathVariable String email){
		boolean isDeleteBook = bookService.deleteBook(id, email);
		if(isDeleteBook) {
			return ResponseEntity.ok("Book Deleted");
		}
		return ResponseEntity.badRequest().body("Unbale to Delete book");
	}
}
