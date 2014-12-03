package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import play.db.jpa.JPA;


@Entity
public class Media {
	
	@Id
	@GeneratedValue
	public Long id;
	
	@Column(name = "name")
	public String name;
	
	
	public static Media findById(long id) {
		return JPA.em().find(Media.class, id);
	}
	
	public String toAlternateString() {
		StringBuilder sb = new StringBuilder();
		sb.append("media { ").append("id :").append(id)
		.append(", ").append("name :").append(name).append(" }");
		return sb.toString();
	}

}
