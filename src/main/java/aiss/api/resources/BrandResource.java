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

import aiss.api.resources.comparators.ComparatorNameBrand;
import aiss.api.resources.comparators.ComparatorNameBrandReversed;
import aiss.model.Brand;
import aiss.model.Phone;
import aiss.model.repository.BrandRepository;
import aiss.model.repository.MapBrandRepository;


@Path("/brands")
public class BrandResource {
	
	/* Singleton */
	private static BrandResource _instance=null;
	BrandRepository repository;
	
	private BrandResource() {
		repository=MapBrandRepository.getInstance();

	}
	
	public static BrandResource getInstance()
	{
		if(_instance==null)
				_instance=new BrandResource();
		return _instance;
	}
	

//	@GET
//	@Produces("application/json")
//	public Collection<Brand> getAll()
//	{
//		return repository.getAllBrands();
//	}
	
	@GET
	@Produces("application/json")
	public Collection<Brand> getAll(@QueryParam("order") String order, @QueryParam("isEmpty") Boolean isEmpty, @QueryParam("name") String name)
	{
		List<Brand> result = new ArrayList<Brand>();
		
		for (Brand brand : repository.getAllBrands()) {
			if (name == null || brand.getName().equals(name)) {
				if (isEmpty == null
						|| (isEmpty && (brand.getPhones() == null))
						|| (!isEmpty && (brand.getPhones() != null))) {
					result.add(brand);
				}
			}
		}
		
		if (order != null) {
			if (order.equals("name")) {
				Collections.sort(result, new ComparatorNameBrand());
			} else if (order.equals("-name")) {
				Collections.sort(result, new ComparatorNameBrandReversed());
			} else {
				throw new BadRequestException("The order parameter must be 'name' or '-name'");
			}
		}
		
		return result;
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Brand get(@PathParam("id") String id)
	{
		Brand brand = repository.getBrand(id);
		
		if (brand == null) {
			throw new NotFoundException("The brand with id="+ id +" was not found");			
		}
		
		return brand;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addBrand(@Context UriInfo uriInfo, Brand brand) {
		if (brand.getName() == null || "".equals(brand.getName()))
			throw new BadRequestException("The name of the brand must not be null");
		
		if (brand.getPhones()!=null)
			throw new BadRequestException("The phones property is not editable.");

		repository.addBrand(brand);

		// Builds the response. Returns the brand the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(brand.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(brand);			
		return resp.build();
	}

	
	@PUT
	@Consumes("application/json")
	public Response updateBrand(Brand brand) {
		Brand oldbrand = repository.getBrand(brand.getId());
		if (oldbrand == null) {
			throw new NotFoundException("The brand with id="+ brand.getId() +" was not found");			
		}
		
		if (brand.getPhones()!=null)
			throw new BadRequestException("The phones property is not editable.");
		
		// Update name
		if (brand.getName()!=null)
			oldbrand.setName(brand.getName());
		
		// Update foundationDate
		if (brand.getFoundationDate()!=null)
			oldbrand.setFoundationDate(brand.getFoundationDate());
		
		// Update foundationDate
			if (brand.getPhonesSold()!=null)
				oldbrand.setPhonesSold(brand.getPhonesSold());
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeBrand(@PathParam("id") String id) {
		Brand toberemoved=repository.getBrand(id);
		if (toberemoved == null)
			throw new NotFoundException("The brand with id="+ id +" was not found");
		else
			repository.deleteBrand(id);
		
		return Response.noContent().build();
	}
	
	
	@POST	
	@Path("/{brandId}/{phoneId}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addPhone(@Context UriInfo uriInfo,@PathParam("brandId") String brandId, @PathParam("phoneId") String phoneId)
	{				
		
		Brand brand = repository.getBrand(brandId);
		Phone phone = repository.getPhone(phoneId);
		
		if (brand==null)
			throw new NotFoundException("The brand with id=" + brandId + " was not found");
		
		if (phone == null)
			throw new NotFoundException("The phone with id=" + phoneId + " was not found");
		
		if (brand.getPhone(phoneId)!=null)
			throw new BadRequestException("The phone is already included in the brand.");
			
		repository.addPhone(brandId, phoneId);		

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(brandId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(brand);			
		return resp.build();
	}
	
	
	@DELETE
	@Path("/{brandId}/{phoneId}")
	public Response removePhone(@PathParam("brandId") String brandId, @PathParam("phoneId") String phoneId) {
		Brand brand = repository.getBrand(brandId);
		Phone phone = repository.getPhone(phoneId);
		
		if (brand==null)
			throw new NotFoundException("The brand with id=" + brandId + " was not found");
		
		if (phone == null)
			throw new NotFoundException("The phone with id=" + phoneId + " was not found");
		
		
		repository.removePhone(brandId, phoneId);		
		
		return Response.noContent().build();
	}
}