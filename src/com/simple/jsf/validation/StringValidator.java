package com.simple.jsf.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


public class StringValidator implements Validator{
	
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		// TODO Auto-generated method stub
		
		String name = (String) value;
		
		if(name.length() < 3) {
	        	FacesMessage message = new FacesMessage();
	            message.setSeverity(FacesMessage.SEVERITY_ERROR);
	            message.setSummary("Name is too short.");
	            message.setDetail("Name is too short.");
	            context.addMessage("Name should be atleast three characters", message);
	            throw new ValidatorException(message);
	        }		
		}

}
