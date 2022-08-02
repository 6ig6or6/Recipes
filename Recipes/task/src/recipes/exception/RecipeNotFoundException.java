package recipes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Recipe doesn't exist")
public class RecipeNotFoundException extends RuntimeException{
}
