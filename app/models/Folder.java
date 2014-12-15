package models;

import javax.persistence.*;
import models.base.BaseModel;
import play.db.jpa.JPA;
import java.util.List;

/**
 * Created by meichris on 02.12.14.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = Folder.QUERY_FETCH_ALL, query = "SELECT f FROM Folder f ORDER BY f.name"),
        @NamedQuery(name = Folder.QUERY_FIND_ALL_GROUPFOLDER, query = "SELECT f FROM Folder f WHERE f.depth = 0"),
})
public class Folder extends BaseModel{

    public static final String QUERY_FETCH_ALL = "Folder.fetchAll";
    public static final String QUERY_FIND_ALL_GROUPFOLDER = "Folder.findAllGroupFolder";

    @Column(name = "name")
    public String name;

    @Column(name = "depth")
    public int depth;

    @ManyToOne
    public Folder parent;

    @ManyToOne
    public Group_ group;

    @OneToMany(mappedBy = "inFolder")
    public List<Media> files;

    @OneToMany(mappedBy = "parent")
    public List<Folder> childs;

    public static Folder findById(long id) {
        return JPA.em().find(Folder.class, id);
    }

    /*
    Creates a folder model
    */
    @Override
    public void create() {
        try {
            JPA.em().persist(this);
        } catch (Exception e) {
            //Blabla
        }
    }
    /*
    Updates a folder model
    */
    @Override
    public void update() {
        JPA.em().merge(this);
    }

    /*
    Deletes a folder model
    */
    @Override
    public void delete() {
        if (this.isEmpty())
            JPA.em().remove(this);
    }
    /*
    Reurns the path of the folder
    @return Formatted path of the Folder
    */
    public String getPath() {
        String result = "";
        String tmp = "";
        Folder f = this;
        while (f.depth > 1) {
            tmp = f.name;
            result = tmp.concat("/").concat(result);
            f = f.parent;
        }
        return result;
    }
    /*
    Checks whether the folder is empty
    @return true if empty, false if not
    */
    public boolean isEmpty() {
        boolean result = false;
        if (this.files.isEmpty())
            result = true;
        return result;
    }
    /*
    Returns a string consisting a folders name and its id
    @return a string consisting a folders name and its id
    */
    public String toAlternateString() {
        StringBuilder sb = new StringBuilder();
        sb.append("folder { ").append("id :").append(id).append(", ")
                .append("name :").append(name).append(" }");
        return sb.toString();
    }
}

