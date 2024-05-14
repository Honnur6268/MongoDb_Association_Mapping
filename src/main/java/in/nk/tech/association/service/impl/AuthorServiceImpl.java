package in.nk.tech.association.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Service;

import in.nk.tech.association.entity.Author;
import in.nk.tech.association.entity.Book;
import in.nk.tech.association.repo.AuthorRepo;
import in.nk.tech.association.repo.BookRepo;
import in.nk.tech.association.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorRepo authorRepo;

	@Autowired
	private BookRepo bookRepo;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Author saveAuthor(Author author) {

		return authorRepo.save(author);
	}

	@Override
	public List<Author> getAllAuthors() {

		return authorRepo.findAll();
	}

	@Override
	public boolean updateAuthor(Author author) {
		Optional<Author> findById = authorRepo.findById(author.getId());
		if (findById.isEmpty()) {
			return false;
		}
		Author updateAuthor = findById.get();
		BeanUtils.copyProperties(author, updateAuthor);
		authorRepo.save(updateAuthor);
		return true;
	}

	@Override
	public Author findAuthorById(String authorId) {
		Optional<Author> findById = authorRepo.findById(authorId);
		if (findById.isEmpty()) {
			return null;
		}
		return findById.get();
	}

	@Override
	public boolean deleteAuthor(String authorId) {
		Optional<Author> findById = authorRepo.findById(authorId);
		if (findById.isEmpty()) {
			return false;
		}
		Author author = findById.get();
		List<Book> books = author.getBooks();

		boolean isDeleted = false;

		if (books.isEmpty()) {
			isDeleted = true;
		}
		for (Book b : books) {
			boolean existsById = bookRepo.existsById(b.getId());
			if (!existsById) {
				return false;
			}
			bookRepo.deleteById(b.getId());
			isDeleted = true;
		}
		if (isDeleted) {
			authorRepo.deleteById(authorId);
			return true;
		}

		return false;
	}

	/*
	 * @Override public Author update(Author author) { Optional<Author> findById =
	 * authorRepo.findById(author.getId()); if (findById.isEmpty()) { return null; }
	 * 
	 * List<Book> books = author.getBook(); Author a = findById.get();
	 * a.setName(author.getName()); a.setBook(books);
	 * 
	 * for (Book b : books) { Optional<Book> findBook =
	 * bookRepo.findById(b.getId()); if (findBook.isEmpty()) { return null; }
	 * 
	 * Book book = findBook.get(); if (b.getId().equals(book.getId())) {
	 * book.setBookName(b.getBookName()); book.setDescription(b.getDescription());
	 * bookRepo.save(book);
	 * 
	 * Author save = authorRepo.save(a); return save; } } return null; }
	 */

	@Override
	public List<Author> findAuthorByBookId(String bookId) {
		Query query = new Query();
		Sort sort = Sort.by(Direction.DESC);
		query.addCriteria(Criteria.where("books.id").is(bookId));
		
		List<Author> find = mongoTemplate.find(query, Author.class);
		
		return find;
	}

}
