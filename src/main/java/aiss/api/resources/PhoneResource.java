package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.api.resources.comparators.ComparatorNamePhone;
import aiss.api.resources.comparators.ComparatorNamePhoneReversed;
import aiss.api.resources.comparators.ComparatorPricePhone;
import aiss.api.resources.comparators.ComparatorPricePhoneReversed;
import aiss.api.resources.comparators.ComparatorReleaseDatePhone;
import aiss.api.resources.comparators.ComparatorReleaseDatePhoneReversed;
import aiss.model.Phone;
import aiss.model.repository.BrandRepository;
import aiss.model.repository.MapBrandRepository;


@Path("/phones")
public class PhoneResource {

	public static PhoneResource _instance=null;
	BrandRepository repository;
	
	private PhoneResource(){
		repository=MapBrandRepository.getInstance();
	}
	
	public static PhoneResource getInstance()
	{
		if(_instance==null)
			_instance=new PhoneResource();
		return _instance; 
	}
	
	@GET
	@Produces("application/json")
	public Collection<Phone> getAll(@QueryParam("order") String order, @QueryParam("q") String q, @QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset)
	{
		List<Phone> result = new ArrayList<Phone>();
		
		for (Phone phone : repository.getAllPhones()) {
			if (q == null
					|| phone.getName().toLowerCase().contains(q.toLowerCase())
					|| phone.getPrice().toString().toLowerCase().contains(q.toLowerCase())
					|| phone.getReleaseDate().toString().toLowerCase().contains(q.toLowerCase())
					|| phone.getSize().toLowerCase().contains(q.toLowerCase())
					|| phone.getResolution().toLowerCase().contains(q.toLowerCase())
					) {
				result.add(phone);
			}
		}
		
		// Order
		if (order != null) {
			switch(order) {
			case "name":
				Collections.sort(result, new ComparatorNamePhone());
				break;
			case "-name":
				Collections.sort(result, new ComparatorNamePhoneReversed());
				break;
			case "releaseDate":
				Collections.sort(result, new ComparatorReleaseDatePhone());
				break;
			case "-releaseDate":
				Collections.sort(result, new ComparatorReleaseDatePhoneReversed());
				break;
			case "price":
				Collections.sort(result, new ComparatorPricePhone());
				break;
			case "-price":
				Collections.sort(result, new ComparatorPricePhoneReversed());
				break;
			default:
				throw new BadRequestException("The order parameter must be in: ['name'], ['-name'], ['releaseDate'], ['-releaseDate'], ['price'], ['-price']");
			}
		}
		
		// Pagination
		int size = repository.getAllPhones().size();
		List<Phone> resultPagination = new ArrayList<Phone>();
		
		if (offset != null && offset > 0 && offset < size) {
			
			if (limit != null && limit > 0 && (offset + limit) <= size) {
				
				for (int i = offset; i < (offset + limit); i++) { resultPagination.add(result.get(i)); }
				
			} else if (limit == null || (offset + limit) > size) {
				
				for (int i = offset; i < size; i++) { resultPagination.add(result.get(i)); }
				
			} else {
				
				throw new BadRequestException("The limit parameter must be greater than 0 and lower than " + result.size() + ".");
			
			}
			
		} else if (offset == null) {
			if (limit != null && limit < size) {
				
				for (int i = 0; i < limit; i++) { resultPagination.add(result.get(i)); }
				
			} else {
				
				for (Phone phone : result) { resultPagination.add(phone); }
				
			}
		} else {
			throw new BadRequestException("The offset parameter must be greater than 0 and lower than " + result.size() + ".");
		}
		
		// Result
		if (offset != null || limit != null) {
			return resultPagination;
		} else {
			return result;
		}
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Phone get(@PathParam("id") String phoneId)
	{
		Phone phone = repository.getPhone(phoneId);
		
		if (phone == null) {
			throw new NotFoundException("The phone with id=" + phoneId + " was not found");
		}
		
		return phone;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addPhone(@Context UriInfo uriInfo, Phone phone) {
		if (phone.getName() == null || "".equals(phone.getName()))
			throw new BadRequestException("The name of the phone must not be null");
		
		repository.addPhone(phone);

		// Builds the response. Returns the phone the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(phone.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(phone);			
		return resp.build();
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updateSong(Phone phone) {
		Phone oldPhone = repository.getPhone(phone.getId());
		
		if (oldPhone == null) {
			throw new NotFoundException("The phone with id="+ phone.getId() +" was not found");			
		}
		
		repository.updatePhone(phone);
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeSong(@PathParam("id") String phoneId) {
		Phone toberemoved=repository.getPhone(phoneId);
		if (toberemoved == null)
			throw new NotFoundException("The phone with id="+ phoneId +" was not found");
		else
			repository.deletePhone(phoneId);
		
		return Response.noContent().build();
	}
	
}
