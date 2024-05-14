package in.nk.tech.association.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import in.nk.tech.association.entity.Book;

public interface BookRepo extends MongoRepository<Book, String> {

}
