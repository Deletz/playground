package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Media;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.main;

/**
 * Manage a database of computers
 */
public class Application extends Controller {

	/**
	 * Handle default path requests, redirect to computers list
	 */
	@Transactional
	public static Result index() {

		return printAll(null);
	}


    @Transactional
	public static Result createMedia(String name) {
		Media m = new Media();
		m.name = name;
		try {

			JPA.em().persist(m);

		} catch (Exception ex) {
			throw ex;
		}

		return printAll("Media '" + m.name + "' hinzugefügt");
	}
    

    @Transactional
	public static Result printAll(String messages) {
		List<Media> medias = new ArrayList<Media>();
		
		medias = JPA.em().createNamedQuery(Media.QUERY_FETCH_ALL).getResultList();
    	String result = "";
    	for(Media m : medias) {
    		result += m.toAlternateString() + "\n";
    	}
    	return ok(main.render(medias, messages));
	}

    @Transactional
	public static Result findByNameWarrr() {
		List<Media> medias = new ArrayList<Media>();
		
		medias = JPA.em().createNamedQuery(Media.QUERY_FIND_BY_NAME).setParameter(Media.PARAM_NAME, "warrr").getResultList();
    	String result = "";
    	for(Media m : medias) {
    		result += m.toAlternateString() + "\n";
    	}
    	return ok(result);
	}
    

    

}
