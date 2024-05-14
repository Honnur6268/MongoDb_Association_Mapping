package in.nk.tech.association.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

	@Id
//	@MongoId(targetType = FieldType.OBJECT_ID,value = FieldType.OBJECT_ID)
	private String id;

	private String bookName;

	private String description;
}
