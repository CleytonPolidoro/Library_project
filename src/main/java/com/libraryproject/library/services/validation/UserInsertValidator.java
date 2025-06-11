package com.libraryproject.library.services.validation;

import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.dto.UserInsertDTO;
import com.libraryproject.library.repositories.UserRepository;
import com.libraryproject.library.resources.exceptions.FieldMessageDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public void initialize(UserInsertValid ann) {
	}

	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessageDTO> list = new ArrayList<>();
		
		Optional<User> user = repository.findByEmail(dto.getEmail());
		if (user.isPresent()) {
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
