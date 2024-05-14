package in.nk.tech.association.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nk.tech.association.entity.Author;
import in.nk.tech.association.entity.Book;
import in.nk.tech.association.repo.AuthorRepo;
import in.nk.tech.association.repo.BookRepo;
import in.nk.tech.association.service.BookService;
import in.nk.tech.association.utills.EmailUtils;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepo bookRepo;

	@Autowired
	private AuthorRepo authorRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	

	@Transactional
	@Override
	public boolean saveBook(Book book, String email) {

		Optional<Author> findByEmail = authorRepo.findByEmail(email);
		if (findByEmail.isEmpty()) {
			return false;
		}
		Author author = findByEmail.get();
		Book savedBook = bookRepo.save(book);
		author.getBooks().add(savedBook);
//		int i = 1/0;
		Author save = authorRepo.save(author);
		List<Book> books = save.getBooks();
		
		
			String bookName = savedBook.getBookName();
			String subject = "Book Pubished";
			String body = "<h4>You have published following book,</h4><h3>Book Name: "+bookName+"</h3>";
			emailUtils.sendEmail(email, subject, body);
		
		return true;
	}

	@Override
	public List<Book> getAllBooks() {

		return bookRepo.findAll();
	}

	@Override
	public boolean updateBook(Book book, String email) {
		Optional<Book> findById = bookRepo.findById(book.getId());
		Optional<Author> findByBooks_Id = authorRepo.findByBooks_Id(book.getId());
		if (findById.isEmpty() || findByBooks_Id.isEmpty()) {
			return false;
		}

		Author author = findByBooks_Id.get();

		if (email.equalsIgnoreCase(author.getEmail())) {
			Book updatingBook = findById.get();
			BeanUtils.copyProperties(book, updatingBook);
			bookRepo.save(updatingBook);

			return true;
		}
		return false;

	}

	@Override
	public boolean deleteBook(String id, String email) {
		Optional<Author> findByEmail = authorRepo.findByEmail(email);
		if (findByEmail.isEmpty()) {
			return false;
		}

		Author author = findByEmail.get();
		boolean removeIf = author.getBooks().removeIf(x -> x.getId().equals(id));

		if (removeIf) {
			authorRepo.save(author);
			bookRepo.deleteById(id);
			return true;
		}

		return false;
	}

}
