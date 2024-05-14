package in.nk.tech.association.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import in.nk.tech.association.entity.Author;

public interface AuthorRepo extends MongoRepository<Author, String> {

	Optional<Author> findByEmail(String email);

	Optional<Author> findByBooks_Id(String bookId);

//	@Query(fields = "{_id: 0, name: 1}")
//	Author findByBook_Id(String bookId);

}
