package models;

import javax.persistence.*;
import play.db.jpa.JPA;


/**
 * Created by meichris on 02.12.14.
 */


@Entity
public class Folder {

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "depth")
    public int depth;

    @OneToOne
    public Folder parent;

    @ManyToOne
    public Group_ group;

    public static Folder findById(long id) {
        return JPA.em().find(Folder.class, id);
    }
}

