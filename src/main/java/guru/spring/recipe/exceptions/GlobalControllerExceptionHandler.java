package guru.spring.recipe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleNotFoundException(Exception exception){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/resource404");
        modelAndView.getModel().put("exception", exception);

        modelAndView.getModel().put("message", exception.getMessage());

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(Exception exception){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/resource404");
        modelAndView.getModel().put("exception", exception);

        modelAndView.getModel().put("message", "Invalid number format");

        return modelAndView;
    }
}
