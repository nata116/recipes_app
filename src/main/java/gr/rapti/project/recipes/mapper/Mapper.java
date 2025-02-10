package gr.rapti.project.recipes.mapper;

import gr.rapti.project.recipes.dto.RecipeInsertDTO;
import gr.rapti.project.recipes.dto.UserInsertDTO;
import gr.rapti.project.recipes.model.Recipe;
import gr.rapti.project.recipes.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapper {

    public User mapToUserEntity(UserInsertDTO userInsertDTO){
        return new User(null,userInsertDTO.getUsername(), userInsertDTO.getPassword());
    }

    public Recipe mapToRecipeEntity(RecipeInsertDTO recipeInsertDTO){

        return new Recipe(null, recipeInsertDTO.getTitle(), recipeInsertDTO.getIngredients(),
                recipeInsertDTO.getPrepTime(),recipeInsertDTO.getCookTime(),
                recipeInsertDTO.getTotalTime(),recipeInsertDTO.getServings(),
                recipeInsertDTO.getDirection(),null);
    }

}
