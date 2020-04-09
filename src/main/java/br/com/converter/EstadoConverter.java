package br.com.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.entidades.Estados;

@SuppressWarnings("serial")
@FacesConverter(forClass = Estados.class, value = "estadoConverter")
public class EstadoConverter implements Converter, Serializable{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {
		
		EntityManager entityManager = CDI.current().select(EntityManager.class).get();
		
		Estados estados = (Estados) entityManager.find(Estados.class, Long.parseLong(codigoEstado));
		
		return estados;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object estado) {

		if(estado == null) {
			
			return null;
		}
		
		if(estado instanceof Estados) {
			
			return ((Estados) estado).getId().toString();
		}else {
			
			return estado.toString();
		}
	}	
}