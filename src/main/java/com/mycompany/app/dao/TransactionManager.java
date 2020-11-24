package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service.Event;

public class TransactionManager {

	 static final String JDBC_DRIVER = "org.h2.Driver";   
	   //static final String DB_URL = "jdbc:h2:~/test";
	 static final String DB_URL =  "jdbc:h2:tcp://localhost/~/test";
	   
	   //  Database credentials 
	   static final String USER = "hemant"; 
	   static final String PASS = "hemant"; 
	   
	   
	   public boolean scheduleEvent(Event event){
		   	
		   	  Connection conn = null; 
		      PreparedStatement stmt = null; 
		      try { 
		         
		         Class.forName(JDBC_DRIVER); 
		         conn = DriverManager.getConnection(DB_URL,USER,PASS); 
		         
		         String sql = "insert into event_schedule (speaker ,venue,date,time,event_name)values(?,?,to_date(?,'DD-MM-YYYY'),?,?)";
		         stmt = conn.prepareStatement(sql);
		         int sq = 1;
		         stmt.setString(sq++, event.getSpeaker());
		         stmt.setString(sq++, event.getVenue());
		         stmt.setString(sq++, event.getDate());
		         stmt.setString(sq++, event.getTime());
		         stmt.setString(sq++, event.getEventName());
		         
		        int i=  stmt.executeUpdate();
		        System.out.println("Event Updated Count"+i);
		         
		         stmt.close(); 
		         conn.close(); 
		      } catch(SQLException se) { 
		          
		         se.printStackTrace(); 
		      } catch(Exception e) { 
		          
		         e.printStackTrace(); 
		      } finally { 
		          
		         try{ 
		            if(stmt!=null) stmt.close(); 
		         } catch(SQLException se2) { 
		         } 
		         try { 
		            if(conn!=null) conn.close(); 
		         } catch(SQLException se){ 
		            se.printStackTrace(); 
		         } 
		      }  
		   	return false;
	   }
	   
	   public Map<String ,List<Event>> fetchDetail(Event event){
		   
		      Connection conn = null; 
		      PreparedStatement stmt = null;
		      List<Event> list = new ArrayList<>();
		      Map<String ,List<Event>> map = new HashMap<>();
		      try { 
		         
		         Class.forName(JDBC_DRIVER); 
		         conn = DriverManager.getConnection(DB_URL,USER,PASS); 
		         
		         String sql = "select speaker , venue , date , time ,event_name,no_of_users from event_schedule where date >= to_date(?,'DD-MM-YYYY') and date <= to_date(?,'DD-MM-YYYY') and venue like ?";
		         stmt = conn.prepareStatement(sql);
		         int sq = 1;
		         stmt.setString(sq++, event.getFromDate());
		         stmt.setString(sq++, event.getToDate());
		         stmt.setString(sq++, event.getVenue());
		         
		         ResultSet rs = stmt.executeQuery();
		         
		         while(rs.next()){
		        	 Event e = new Event();
		        	 String speaker = rs.getString(1);
		        	 String venue = rs.getString(2);
		        	 String date = rs.getString(3);
		        	 String time = rs.getString(4);
		        	 String eventName = rs.getString(5);
		        	 String noOfusers = rs.getString(6);
		        	 e.setSpeaker(speaker);
		        	 e.setVenue(venue);
		        	 e.setDate(date);
		        	 e.setTime(time);
		        	 e.setNoOfUsers(noOfusers);
		        	 e.setEventName(eventName);
		        	 if(map != null && map.containsKey(date)){
		        		 List<Event> localList = map.get(date);
		        		 localList.add(e);
		        		 map.put(date, localList);
		        	 }else{
		        		 List<Event> l = new ArrayList<>();
		        		 l.add(e);
		        		 map.put(date, l);
		        	 }
		        	 
		        	 list.add(e);
		        	 
		         }
		        
		         stmt.close(); 
		         conn.close(); 
		      } catch(SQLException se) { 
		          
		         se.printStackTrace(); 
		      } catch(Exception e) { 
		          
		         e.printStackTrace(); 
		      } finally { 
		          
		         try{ 
		            if(stmt!=null) stmt.close(); 
		         } catch(SQLException se2) { 
		         } 
		         try { 
		            if(conn!=null) conn.close(); 
		         } catch(SQLException se){ 
		            se.printStackTrace(); 
		         } 
		      }  
		   return map;
	   }
	
	   public boolean deleteEvent(Event event){
		   	
		   	  Connection conn = null; 
		      PreparedStatement stmt = null; 
		      boolean isDeleted = false;
		      try { 
		         
		         Class.forName(JDBC_DRIVER); 
		         conn = DriverManager.getConnection(DB_URL,USER,PASS); 
		         
		         String sql = "delete from event_schedule where date = to_date(?,'DD-MM-YYYY') and venue like ?  and event_name like ? ";
		         stmt = conn.prepareStatement(sql);
		         int sq = 1;
		        
		         stmt.setString(sq++, event.getDate());
		         stmt.setString(sq++, event.getVenue());
		         stmt.setString(sq++, event.getEventName());
		       
		         
		        int i=  stmt.executeUpdate();
		        System.out.println("row deleted "+i);
		        isDeleted = true;
		         stmt.close(); 
		         conn.close(); 
		      } catch(SQLException se) { 
		          
		         se.printStackTrace(); 
		      } catch(Exception e) { 
		          
		         e.printStackTrace(); 
		      } finally { 
		          
		         try{ 
		            if(stmt!=null) stmt.close(); 
		         } catch(SQLException se2) { 
		         } 
		         try { 
		            if(conn!=null) conn.close(); 
		         } catch(SQLException se){ 
		            se.printStackTrace(); 
		         } 
		      }  
		   	return isDeleted;
	   }	
}
