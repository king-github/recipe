package guru.spring.recipe.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundExcception extends RuntimeException {

    public ResourceNotFoundExcception(String message) {
        super(message);
    }
}
