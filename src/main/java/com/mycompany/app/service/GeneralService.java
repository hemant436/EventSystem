package service;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET; 
import javax.ws.rs.POST;
import javax.ws.rs.Path; 
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


@Path("/event")
public class GeneralService {
	
	
		@POST 
		@Path("/schedule") 
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON) 
	   public String getUsers(String json ) throws JSONException{ 
			
		JSONObject obj = new JSONObject(json);
		Event evnt = new Event();
		evnt.setSpeaker(obj.getString("speaker"));
		evnt.setTime(obj.getString("time"));
		evnt.setDate(obj.getString("date"));
		evnt.setVenue(obj.getString("venue"));
		evnt.setEventName(obj.getString("event_name"));
		RouteProcess rp = new RouteProcess();
		rp.scheduleEvent(evnt);
		System.out.println(json);
		JSONObject o = new JSONObject();
		o.put("message", "event scheduled");
		
		return o.toString();
		
		
	   } 
		
		@POST 
		@Path("/fetchDetail") 
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON) 
	   public String fetchDetail(String json ) throws JSONException{ 
			
		JSONObject obj = new JSONObject(json);
		Event evnt = new Event();
		evnt.setFromDate(obj.getString("start date"));
		evnt.setToDate(obj.getString("end date"));
		evnt.setVenue(obj.getString("venue"));
		
		RouteProcess rp = new RouteProcess();
		return rp.fetchDetail(evnt);
		
		
	   } 
		@RolesAllowed("ADMIN")
		@POST 
		@Path("/deleteEvent") 
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON) 
		public String deleteEvent(String json)  throws JSONException{
			
			
			JSONObject obj = new JSONObject(json);
			JSONObject j = new JSONObject();
			Event e = new Event();
			e.setDate(obj.getString("date"));
			e.setVenue(obj.getString("venue"));
			e.setEventName(obj.getString("event"));
			RouteProcess rp = new RouteProcess();
			
			 if(rp.deleteEvent(e)){
				 j.put("message" , "Event Deleted");
				 
			 }else
				 j.put("message" , "Event Deletion failure");
			
			
			
			return j.toString();
			
		}
		
		
		
}
