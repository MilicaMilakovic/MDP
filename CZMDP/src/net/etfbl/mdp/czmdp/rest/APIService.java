package net.etfbl.mdp.czmdp.rest;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.etfbl.mdp.model.TrainLine;
import net.etfbl.mdp.model.TrainStation;

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
	@Path("/stanice/{city}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getByLocation(@PathParam("city") String city ){
		
		String schedule = service.getByLocation(city);
		if(!schedule.equals("")){
			return Response.status(200).entity(schedule).build();
		}
		
		return Response.status(404).build();
	}
	
	
	@PUT
	@Path("/{id}")	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response record(TrainStation station,@PathParam("id") int id) {
		
		if(service.update(id, station))
			return Response.status(200).entity(station).build();
		
		return Response.status(404).build();	
		
	}
	
	@GET
	@Path("/{id}/{city}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getStationByLine(@PathParam("id") int id, @PathParam("city") String city) {
		
		TrainStation station = service.getStation(id, city);
		if(station != null)
			return Response.status(200).entity(station).build();
		return Response.status(404).build();
		
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addLine(TrainLine line) {
		
		service.addLine(line);
		return Response.status(200).entity(line).build();		
	}
	
	@DELETE
	@Path("/{id}")
	public Response remove(@PathParam("id") int id) {
		service.deleteLine(id);
		return Response.status(200).build();
	}
}
