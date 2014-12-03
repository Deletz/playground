package models;

/**
 * Created by meichris on 03.12.14.
 */

import play.db.jpa.JPA;
import javax.persistence.*;


@Entity
public class Group_ {

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "name")
    public String name;

    public static Group_ findById(long id) {
        return JPA.em().find(Group_.class, id);
    }

}
