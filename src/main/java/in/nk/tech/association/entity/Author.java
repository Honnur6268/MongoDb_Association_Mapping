package in.nk.tech.association.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

//	@MongoId(targetType = FieldType.OBJECT_ID,value = FieldType.OBJECT_ID)
	@Id
	private String id;

	private String name;
	
	@Indexed(unique = true)
	@NotNull
	private String email;

	@Field(name = "books")
	@DBRef
	private List<Book> books = new ArrayList<>();
}
