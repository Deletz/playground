package controllers;

import models.Folder;
import models.Group_;
import play.db.jpa.JPA;

import java.util.List;

/*
Created by meichris on 10.12.14.

Represents a group controller with a "root folder"


 */
public class Group_Controller {


    /*
    Creates a Group with the delivered name

    @param argGroupName The delivered group name
    */
    public static void createGroup(String argGroupName) {
        Group_ g = new Group_();
        Folder f = new Folder();
        if (allowToCreate(argGroupName)) {
            g.name = argGroupName;
            g.create();
            f.name = argGroupName;
            f.parent = Folder.findById(1L);
            f.group = g;
            f.depth = 1;
            f.create();
        }
    }
    /*
    Checks whether it is allowed or not to create a new group

    @param argName The groups name
    @param argParentId The groups parent ID

    @return true if there isn't allready a group with the same name, false if there is.
    */
    private static boolean allowToCreate(String argName) {
        boolean allow = true;
        List<Group_> groups = JPA.em().createNamedQuery(Group_.QUERY_FETCH_ALL).getResultList();
        if (!groups.isEmpty())
            for (Group_ g: groups)
                if (g.name.equals(argName))
                    allow =false;
        return allow;
    }
}
