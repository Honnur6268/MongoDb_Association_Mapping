package in.nk.tech.association.service;

import java.util.List;

import in.nk.tech.association.entity.Book;

public interface BookService {

	public boolean saveBook(Book book, String email);

	public List<Book> getAllBooks();
	
	public boolean updateBook(Book book ,String email);
	
	public boolean deleteBook(String id, String email);
}
