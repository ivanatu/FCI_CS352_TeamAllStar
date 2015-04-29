package com.FCI.SWE.Controller;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class UserController {
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@GET
	@Path("/signup")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}	

	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return enty point page (Home page of this application)
	 */
	@GET
	@Path("/")
	public Response index(@Context HttpServletRequest req) {//used for logout too
		Timestamp timestamp1 = new Timestamp(new Date(System.currentTimeMillis()).getTime());
		System.out.println(timestamp1);
		Timestamp timestamp2 = new Timestamp(new Date(System.currentTimeMillis()).getTime());
		System.out.println(timestamp2);
		//System.out.println(timestamp1. timestamp2);
		req.setAttribute("name", null);
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
				
		return Response.ok(new Viewable("/jsp/login")).build();
	}
	
	@GET
	@Path("/logout")
	public Response logout() {
				
		return Response.ok(new Viewable("/jsp/logout")).build();
	}
	
	/**
	 * Action function to render tempLogin page that's used for storing the value of
	 * the user name in a session then call the login service through the home controller
	 * down below
	 * 
	 * @return tempLogin page
	 */
	/*@POST
	@Path("/tempLogin")
	public Response tempLogin(@FormParam) {
		
		return Response.ok(new Viewable("/jsp/tempLogin")).build();
	}	*/

	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public Response response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		//if the field is empty do nothing
		if(email.equals("") || pass.equals("") || uname.equals(""))
		{
			return null ;	
		}
		String serviceUrl = "http://localhost:8888/rest/RegistrationService";
		String urlParameters = "uname=" + uname + "&email=" + email
				+ "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return Response.ok("Registered Successfuly!").build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.ok("Registeration Failed !").build();
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	//context is used to handle a session
	public Response home(@Context HttpServletRequest req, @FormParam("uname") String uname,
			@FormParam("password") String pass) {
		
		if(pass.equals("") || uname.equals(""))
		{
			return null ;	
		}
		String urlParameters = "uname=" + uname + "&password=" + pass;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/LoginService"
				//"http://localhost:8888/rest/LoginService"
				, urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			
			Map map = new HashMap();
			req.getSession(true).setAttribute("currentName",object.get("name"));			
			req.getSession(true).setAttribute("currentEmail",object.get("email"));
			map.put("name", object.get("name"));
			map.put("email",object.get("email"));
			//setting the session attributes
			return Response.ok(new Viewable("/jsp/home", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}