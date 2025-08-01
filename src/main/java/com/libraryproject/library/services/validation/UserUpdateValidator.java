package com.libraryproject.library.services.validation;

import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.dto.UserUpdateDTO;
import com.libraryproject.library.repositories.UserRepository;
import com.libraryproject.library.resources.exceptions.FieldMessageDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public void initialize(UserUpdateValid ann) {
	}

	@Override
	public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		long userId = Long.parseLong(uriVars.get("id"));
		
		List<FieldMessageDTO> list = new ArrayList<>();
		
		Optional<User> user = repository.findByEmail(dto.getEmail());
		if (user.isPresent() && userId != user.get().getId()) {
			list.add(new FieldMessageDTO("email", "Email já existe"));
		}

		for (FieldMessageDTO e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
