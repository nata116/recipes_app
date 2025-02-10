package gr.rapti.project.recipes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "recipes")
public class Recipe extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String ingredients;
    private String prepTime;
    private String cookTime;
    private String totalTime;
    private String servings;
    private String direction;

    @OneToOne
    @JoinColumn(name = "recipe_img_id")
    private Attachment attachment;
}
