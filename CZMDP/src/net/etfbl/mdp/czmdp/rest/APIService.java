package net.etfbl.mdp.czmdp.rest;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.etfbl.mdp.model.TrainLine;

@Path("/linije")
public class APIService {
	
	ScheduleService service ;
	
	public APIService() {
		
		service = new ScheduleService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll (){		
		return service.getSchedule();
	}
	
	@GET
	@Path("/{city}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getByLocation(@PathParam("city") String city ){
		
		String schedule = service.getByLocation(city);
		if(!schedule.equals("")){
			return Response.status(200).entity(schedule).build();
		}
		
		return Response.status(404).build();
	}
	
}
