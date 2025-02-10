package gr.rapti.project.recipes.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecipeInsertDTO {

    @NotNull(message = "Recipe title must not be null")
    private String title;

    private String ingredients;
    private String prepTime;
    private String cookTime;
    private String totalTime;
    private String servings;

    private String direction;

    private MultipartFile imgAttach;

}
