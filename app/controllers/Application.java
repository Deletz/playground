package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.*;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;


import javax.persistence.PersistenceException;


/**
 * Manage a database of computers
 */
public class Application extends Controller {

	/**
	 * Handle default path requests, redirect to computers list
	 */
	@Transactional
	public static Result index() {

		if (JPA.em().createNamedQuery(Folder.QUERY_FETCH_ALL).getResultList().isEmpty()) {
			Group_ g1 = new Group_();
			g1.name = "Gruppe1";
			Group_ g2 = new Group_();
			g2.name = "Gruppe2";
			try {
				JPA.em().persist(g1);
				JPA.em().persist(g2);
			} catch (Exception ex) {
				throw ex;
			}
			Folder f0 = new Folder(); //FolderController.createFolder("root", null);
			f0.name = "root";
			f0.depth = 0;
			f0.parent = null;
			f0.group = null;
			f0.create();

			FolderController.createFolder("Gruppe1", 3L);
			FolderController.createFolder("Gruppe2", 4l);
		}
		return printAll();
	}


    @Transactional
	public static Result createMedia() {

		FolderController.createFolder("Ordner xyz", 3L);
		FolderController.createFolder("Ordner abc", 4L);

		Media m1 = new Media();
		m1.name = "Media1";
		Media m2 = new Media();
		m2.name = "Media2";
		m1.inFolder = Folder.findById(6L);
		m2.inFolder = Folder.findById(7L);

		try {
			JPA.em().persist(m1);
			JPA.em().persist(m2);
		} catch (Exception ex) {
			throw ex;
		}
//		return ok(m1.toAlternateString());
		return printAll();
	}

	@Transactional
	public static Result createMoreMedia() {
		Long f1 = FolderController.createFolder("Ordner xyz", 4L);
		Long f2 = FolderController.createFolder("Ordner abc", 3L);
		Media m1 = new Media();
		m1.name = UUID.randomUUID().toString();
		m1.inFolder = Folder.findById(7L);
		Media m2 = new Media();
		m2.name = UUID.randomUUID().toString();
		m2.inFolder = Folder.findById(4L);
		try {
			JPA.em().persist(m1);
			JPA.em().persist(m2);
		} catch (Exception ex) {
			throw ex;
		}
//		return ok(views.html.list.render(null));
		return printAll();
	}

    @Transactional
	public static Result printAll() {
		List<Media> media = new ArrayList<Media>();
//		try {
			media = JPA.em().createNamedQuery(Media.QUERY_FETCH_ALL).getResultList();
//		} catch (PersistenceException e) {
//			//Blabla
//		}
		return ok(views.html.list.render(media));
	}

	@Transactional
	public static Result listMediaInFolder() {
		String path = "";
		try {
			path = Folder.findById(6L).getPath();
		} catch (NullPointerException e) {
			path = "Fehler !!!! Kein Ordner vorhanden";
		}
		List<Media> media = FolderController.getAllMediaInFolder(6L);
		return ok(views.html.folder.render(path,media));
	}

//    @Transactional
//	public static Result findByNameWarrr() {
//		List<Media> medias = new ArrayList<Media>();
//
//		medias = JPA.em().createNamedQuery(Media.QUERY_FIND_BY_NAME).setParameter(Media.PARAM_NAME, "Media1").getResultList();
//    	String result = "";
//    	for(Media m : medias) {
//    		result += m.toAlternateString() + "\n";
//    	}
//
//		result += "\n\nOrdner2\n";
//		medias = FolderController.getAllMediaInFolder(1L);
//
//		for(Media m : medias)
//			result += "\t" + m.toAlternateString() + "\n";
//    	return ok(result);
//	}
}
