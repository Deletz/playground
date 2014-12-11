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
			Folder f0 = new Folder(); //FolderController.createFolder("root", null);
			f0.name = "root";
			f0.depth = 0;
			f0.parent = null;
			f0.group = null;
			f0.create();

			Group_Controller.createGroup("Gruppe1");
			Group_Controller.createGroup("Gruppe2");
		}
		return printAll();
	}

    @Transactional
	public static Result createMedia(Long folderID) {
		MediaController.createMedia(UUID.randomUUID().toString(),folderID);
		return redirect("/folder/" + folderID);
	}

	@Transactional
	public static Result createFolder(Long folderID) {

//		String uri = request().uri();
//		int index = uri.lastIndexOf("/");
//		Long folder = Long.valueOf(uri.substring(index));

		FolderController.createFolder("Ordner xyz", folderID);
		return redirect("/folder/" + folderID);
	}

    @Transactional
	public static Result printAll() {
		List<Media> media = JPA.em().createNamedQuery(Media.QUERY_FETCH_ALL).getResultList();
		List<Folder> folder = JPA.em().createNamedQuery(Folder.QUERY_FETCH_ALL).getResultList();
		return ok(views.html.list.render(folder,media));
	}

	@Transactional
	public static Result listMediaInFolder(Long folderID) {
		String path = "";
		try {
			path = Folder.findById(folderID).getPath();
		} catch (NullPointerException e) {
			path = "Fehler !!!! Kein Ordner vorhanden";
		}
		List<Media> media = FolderController.getAllMediaInFolder(folderID);
		List<Folder> folder = Folder.findById(folderID).childs;
		return ok(views.html.folder.render(path,folder,media,folderID));
	}
}
