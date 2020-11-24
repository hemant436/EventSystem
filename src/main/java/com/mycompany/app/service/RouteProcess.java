package service;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.TransactionManager;

public class RouteProcess {

	
	public void scheduleEvent(Event e){
		TransactionManager dao = new TransactionManager(); 
		try{
			dao.scheduleEvent(e);
			
		}catch(Exception e1){
			e1.printStackTrace();
		}
	}
	
	public String fetchDetail(Event e){
		TransactionManager dao = new TransactionManager();
		JSONArray globalArray = new JSONArray();
		String json = "";
		try{
			Map<String , List<Event>> map = dao.fetchDetail(e);
			if(map != null && map.size() > 0 ){
				for (Map.Entry<String, List<Event>> mp : map.entrySet()){
					
					String key = mp.getKey();
					List<Event> l = mp.getValue();
					JSONObject objk = new JSONObject();
					JSONArray array = new JSONArray();
					
					for( Event e1 : l){
						JSONObject obj = new JSONObject();
						obj.put("event name", e1.getEventName());
						obj.put("venue", e1.getVenue());
						obj.put("time", e1.getTime());
						obj.put("speaker", e1.getSpeaker());
						obj.put("users", e1.getNoOfUsers());
						array.put(obj);
					}
					objk.put(key, array);
					globalArray.put(objk);
				}
			}
			json = globalArray.toString();
		}catch(Exception e1){
			e1.printStackTrace();
		}
		return json;
	}
	

	public boolean deleteEvent(Event e){
		TransactionManager dao = new TransactionManager();
		boolean isDeleted = false;
		try{
			isDeleted = dao.deleteEvent(e);
			
		}catch(Exception e1){
			e1.printStackTrace();
		}
		return isDeleted;
	}


}
