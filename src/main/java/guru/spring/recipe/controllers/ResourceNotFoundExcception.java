package guru.spring.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;
import java.util.logging.Logger;


@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundExcception extends RuntimeException {

    private static final String NOT_FOUND = " not found";

    public ResourceNotFoundExcception(String message) {
        super(message);
    }

    public static Supplier<ResourceNotFoundExcception> of (String resourceName){

        StackTraceElement invokedClass = Thread.currentThread().getStackTrace()[2];
        org.slf4j.Logger log = LoggerFactory.getLogger(invokedClass.getClassName());

        return () -> {
            String message = resourceName + NOT_FOUND;
            log.info(message);
            return new ResourceNotFoundExcception(message);
        };
    }
}
