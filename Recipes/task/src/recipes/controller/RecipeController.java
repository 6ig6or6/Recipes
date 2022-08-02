package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import recipes.dto.RecipeDto;

import recipes.recipe.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("api/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @PostMapping("/new")
    public ResponseEntity<Map<String, Long>> createRecipe(@RequestBody @Valid Recipe recipeDto,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        Recipe recipe = recipeService.registerRecipe(recipeDto, userDetails);
        return new ResponseEntity<>(Map.of("id", recipe.getId()), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        RecipeDto recipeDto = new RecipeDto(recipe.getName(),  recipe.getCategory(),
                recipe.getDate(), recipe.getDescription(), recipe.getIngredients(), recipe.getDirections());
        return new ResponseEntity<>(recipeDto, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable long id,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        recipeService.deleteRecipeById(id, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody @Valid Recipe recipe,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        recipeService.updateRecipe(recipe, id, userDetails);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> getAllRecipesByRequest(@RequestParam Optional<String> category,
                                                               @RequestParam Optional<String> name) {
       List<Recipe> list = recipeService.getRequestedRecipes(category, name);
      return new ResponseEntity<>(list, HttpStatus.OK);
    }



}
