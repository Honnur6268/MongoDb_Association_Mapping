package in.nk.tech.association.service;

import java.util.List;

import in.nk.tech.association.entity.Author;

public interface AuthorService {

	public Author saveAuthor(Author author);
	
	public List<Author> getAllAuthors();
	
	public boolean updateAuthor(Author author);
//
	public Author findAuthorById(String authorId);
//	
	public boolean deleteAuthor(String authorId);
	
//	public Author update(Author author);
	
	public List<Author> findAuthorByBookId(String bookId);
	
}
