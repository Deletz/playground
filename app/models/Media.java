package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import play.db.jpa.JPA;

@Entity
@NamedQueries({
		@NamedQuery(name = Media.QUERY_FETCH_ALL, query = "SELECT m FROM Media m ORDER BY m.name"),
		@NamedQuery(name = Media.QUERY_FIND_BY_NAME, query = "SELECT m FROM Media m WHERE m.name = :"
				+ Media.PARAM_NAME + " ORDER BY m.name"),

})
public class Media {

	public static final String QUERY_FETCH_ALL = "Media.fetchAll";
	public static final String QUERY_FIND_BY_NAME = "Media.findByName";
	public static final String PARAM_NAME = "param_name";

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
		sb.append("media { ").append("id :").append(id).append(", ")
				.append("name :").append(name).append(" }");
		return sb.toString();
	}

}
