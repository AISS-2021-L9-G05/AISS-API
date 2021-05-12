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

import aiss.api.resources.comparators.ComparatorAlbumSong;
import aiss.api.resources.comparators.ComparatorAlbumSongReversed;
import aiss.api.resources.comparators.ComparatorArtistSong;
import aiss.api.resources.comparators.ComparatorArtistSongReversed;
import aiss.api.resources.comparators.ComparatorYearSong;
import aiss.api.resources.comparators.ComparatorYearSongReversed;
import aiss.model.Song;
import aiss.model.repository.MapPlaylistRepository;
import aiss.model.repository.PlaylistRepository;



@Path("/songs")
public class SongResource {

	public static SongResource _instance=null;
	PlaylistRepository repository;
	
	private SongResource(){
		repository=MapPlaylistRepository.getInstance();
	}
	
	public static SongResource getInstance()
	{
		if(_instance==null)
			_instance=new SongResource();
		return _instance; 
	}
	
	@GET
	@Produces("application/json")
	public Collection<Song> getAll(@QueryParam("order") String order, @QueryParam("q") String q, @QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset)
	{
		List<Song> result = new ArrayList<Song>();
		
		for (Song song : repository.getAllSongs()) {
			if (q == null
					|| song.getTitle().toLowerCase().contains(q.toLowerCase())
					|| song.getAlbum().toLowerCase().contains(q.toLowerCase())
					|| song.getArtist().toLowerCase().contains(q.toLowerCase())
					) {
				result.add(song);
			}
		}
		
		// Order
		if (order != null) {
			switch(order) {
			case "album":
				Collections.sort(result, new ComparatorAlbumSong());
				break;
			case "-album":
				Collections.sort(result, new ComparatorAlbumSongReversed());
				break;
			case "artist":
				Collections.sort(result, new ComparatorArtistSong());
				break;
			case "-artist":
				Collections.sort(result, new ComparatorArtistSongReversed());
				break;
			case "year":
				Collections.sort(result, new ComparatorYearSong());
				break;
			case "-year":
				Collections.sort(result, new ComparatorYearSongReversed());
				break;
			default:
				throw new BadRequestException("The order parameter must be in: ['album'], ['-album'], ['artist'], ['-artist'], ['year'], ['-year']");
			}
		}
		
		// Pagination
		int size = repository.getAllSongs().size();
		List<Song> resultPagination = new ArrayList<Song>();
		
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
				
				for (Song song : result) { resultPagination.add(song); }
				
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
	public Song get(@PathParam("id") String songId)
	{
		Song song = repository.getSong(songId);
		
		if (song == null) {
			throw new NotFoundException("The song with id=" + songId + " was not found");
		}
		
		return song;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addSong(@Context UriInfo uriInfo, Song song) {
		if (song.getTitle() == null || "".equals(song.getTitle()))
			throw new BadRequestException("The title of the song must not be null");
		
		repository.addSong(song);

		// Builds the response. Returns the song the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(song.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(song);			
		return resp.build();
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updateSong(Song song) {
		Song oldSong = repository.getSong(song.getId());
		
		if (oldSong == null) {
			throw new NotFoundException("The song with id="+ song.getId() +" was not found");			
		}
		
		repository.updateSong(song);
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeSong(@PathParam("id") String songId) {
		Song toberemoved=repository.getSong(songId);
		if (toberemoved == null)
			throw new NotFoundException("The song with id="+ songId +" was not found");
		else
			repository.deleteSong(songId);
		
		return Response.noContent().build();
	}
	
}
