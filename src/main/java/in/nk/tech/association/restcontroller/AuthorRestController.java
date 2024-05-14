package in.nk.tech.association.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nk.tech.association.entity.Author;
import in.nk.tech.association.service.AuthorService;

@RestController
@RequestMapping("/api/author")
public class AuthorRestController {

	@Autowired
	private AuthorService authorService;

	@PostMapping
	public ResponseEntity<Author> insertAuthor(@RequestBody Author author) {
		Author insertedAuthor = authorService.saveAuthor(author);
		if (null != insertedAuthor) {
			return ResponseEntity.ok(insertedAuthor);
		}
		return ResponseEntity.badRequest().body(insertedAuthor);
	}

	@GetMapping
	public ResponseEntity<List<Author>> getAllAuthors() {
		return ResponseEntity.ok(authorService.getAllAuthors());
	}

	@PutMapping
	public ResponseEntity<?> updateAuthor(@RequestBody Author author) {
		boolean isUpdated = authorService.updateAuthor(author);
		
		if(!isUpdated) {
			return ResponseEntity.badRequest().body(author);
		}
		return ResponseEntity.ok(author);
	}

	@GetMapping("/{authorId}")
	public ResponseEntity<?> getAuthorDetailsById(@PathVariable String authorId) {
		Author findAuthorById = authorService.findAuthorById(authorId);
		if (null == findAuthorById) {
			return new ResponseEntity<>("Author Not Found Author Id: "+authorId, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(findAuthorById);
	}

	@DeleteMapping("/{authorId}")
	public ResponseEntity<String> deleteAuthor(@PathVariable String authorId) {
		boolean isDeleted = authorService.deleteAuthor(authorId);
		
		if(!isDeleted) {
			return ResponseEntity.badRequest().body("Unable to delete, Author Id: "+authorId);
		}
		return ResponseEntity.ok("Author deleted successfully, Author Id: "+authorId);
	}
	
	/*@PutMapping("/update")
	public ResponseEntity<Author> update(@RequestBody Author author) {
		Author update = authorService.update(author);
		if (null == update) {
			return new ResponseEntity<>(update, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(update);
	}
	*/
	
	@GetMapping("/get/{bookId}")
	public ResponseEntity<List<Author>> findAuthorByBookId(@PathVariable String bookId){
		List<Author> findAuthorByBookId = authorService.findAuthorByBookId(bookId);
		return ResponseEntity.ok(findAuthorByBookId);
	}
}


