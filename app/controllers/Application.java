package controllers;

import play.mvc.*;
import play.data.*;
import play.db.jpa.JPA;
import static play.data.Form.*;

import views.html.*;

import models.*;

/**
 * Manage a database of computers
 */
public class Application extends Controller {
    
 
    
    /**
     * Handle default path requests, redirect to computers list
     */
    public static Result index() {
    	Media m = new Media();
    	m.name = "bla";
    	
    	Media m2 = new Media();
    	m.name = "warrr";
    	try {

        	JPA.em().persist(m);
        	JPA.em().persist(m2);
            	
    	} catch (Exception ex) {
    		throw ex;
    	}
    	
    	return ok();
    }

}
            
