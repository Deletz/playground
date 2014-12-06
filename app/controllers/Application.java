package controllers;

import java.util.ArrayList;
import java.util.List;

import models.*;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Manage a database of computers
 */
public class Application extends Controller {

	/**
	 * Handle default path requests, redirect to computers list
	 */
	@Transactional
	public static Result index() {

		return printAll();
	}


    @Transactional
	public static Result createMedia() {
		Media m1 = new Media();
		m1.name = "Media1";
		Media m2 = new Media();
		m2.name = "Media2";

		Group_ g1 = new Group_();
		g1.name = "Gruppe1";
		Group_ g2 = new Group_();
		g2.name = "Gruppe2";

		Folder f1 = new Folder();
		f1.name = "Ordner1";
		f1.parent = Folder.findById(0);
		f1.group = g1;
		f1.depth = f1.parent.depth +1;
		f1.create();

		Folder f2 = new Folder();
		f2.name = "Ordner2";
		f2.parent = f1;
		f2.group = g1;
		f2.depth = f2.parent.depth +1;
		f2.create();

		m1.inFolder = f1;
		m2.inFolder = f1;

		try {
			JPA.em().persist(m1);
			JPA.em().persist(m2);
			JPA.em().persist(g1);
			JPA.em().persist(g2);
		} catch (Exception ex) {
			throw ex;
		}

		f2.parent = Folder.findById(1);
		f2.update();

		return ok(m1.toAlternateString());
	}
    

    @Transactional
	public static Result printAll() {
		List<Media> medias = new ArrayList<Media>();
		
		medias = JPA.em().createNamedQuery(Media.QUERY_FETCH_ALL).getResultList();
    	String result = "";
    	for(Media m : medias) {
    		result += m.toAlternateString() + "\n";
    	}

		result += "\n\n";

		List<Folder> folders = new ArrayList<Folder>();

		folders = JPA.em().createNamedQuery(Folder.QUERY_FIND_BY_NAME).setParameter(Folder.PARAM_NAME, "Ordner2").getResultList();
		for(Folder f : folders) {
			result += f.getPath();
		}

    	return ok(result);
	}

    @Transactional
	public static Result findByNameWarrr() {
		List<Media> medias = new ArrayList<Media>();
		
		medias = JPA.em().createNamedQuery(Media.QUERY_FIND_BY_NAME).setParameter(Media.PARAM_NAME, "Media1").getResultList();
    	String result = "";
    	for(Media m : medias) {
    		result += m.toAlternateString() + "\n";
    	}
    	return ok(result);
	}
    

    

}
