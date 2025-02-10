package gr.rapti.project.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecipeReadOnlyDTO {

    private String title;

    private String ingredients;
    private String prepTime;
    private String cookTime;
    private String totalTime;
    private String servings;
    private String direction;
    private String filePath;
    private String filename;
    private String extension;
}
