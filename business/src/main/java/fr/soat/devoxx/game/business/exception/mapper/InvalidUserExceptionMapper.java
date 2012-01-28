package fr.soat.devoxx.game.business.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import fr.soat.devoxx.game.business.exception.InvalidUserException;

/**
 * User: aurelien
 * Date: 24/01/12
 * Time: 16:59
 */
public class InvalidUserExceptionMapper implements ExceptionMapper<InvalidUserException> {

	@Override
	public Response toResponse(InvalidUserException exception) {
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}
}
