package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import recipes.exception.RecipeAuthException;
import recipes.exception.RecipeNotFoundException;
import recipes.exception.WrongParamException;
import recipes.recipe.Recipe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserService userService;
    public Recipe registerRecipe(Recipe recipe, UserDetails userDetails) {
     recipe.setDate(LocalDateTime.now());
     recipe.setUser(userService.findUserByEmail(userDetails.getUsername()));
     return recipeRepository.save(recipe);
    }
    public void deleteRecipeById(long id, UserDetails userDetails) {
       Recipe recipe = getRecipeById(id);
       if (recipe.getUser() == null) {
           recipeRepository.delete(recipe);
           return;
       }
        if (!recipe.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new RecipeAuthException();
        }
       recipeRepository.delete(recipe);
    }
    public Recipe getRecipeById(long id) {
        return recipeRepository.findRecipeById(id).orElseThrow(RecipeNotFoundException::new);
    }
    public Recipe getByName(String name) {
        return recipeRepository.findRecipeByName(name).orElse(null);
    }

    public Recipe updateRecipe(Recipe recipe, long id, UserDetails userDetails) {
        if (recipeRepository.findRecipeById(id).isEmpty()) {
         throw new RecipeNotFoundException();
        }
        if (!recipeRepository.findRecipeById(id).get().getUser().getEmail().equals(userDetails.getUsername())) {
            throw new RecipeAuthException();
        }
        recipe.setDate(LocalDateTime.now());
        recipe.setId(id);
        return recipeRepository.save(recipe);
    }
    public List<Recipe> getRequestedRecipes(Optional<String> category, Optional<String> name) {
        if ((category.isEmpty() && name.isEmpty()) || (category.isPresent() && name.isPresent())) {
            throw new WrongParamException();
        }
        List<Recipe> list = new ArrayList<>();
        if (category.isPresent()) {
            list.addAll(recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category.get()));
        }
        else {
            list.addAll(recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name.get()));
        }
        return list;
    }

}
