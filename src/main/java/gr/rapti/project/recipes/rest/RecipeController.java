package gr.rapti.project.recipes.rest;

import gr.rapti.project.recipes.dto.RecipeInsertDTO;
import gr.rapti.project.recipes.dto.RecipeReadOnlyDTO;
import gr.rapti.project.recipes.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private static final Logger LOGGER = LoggerFactory.getLogger(Exception.class);

    @GetMapping("/recipes/save")
    public ModelAndView getRecipeSaveForm(Model model) {
        model.addAttribute("recipeInsertDTO", new RecipeInsertDTO());
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("add_recipe.html");
        return modelAndView;
    }

    @PostMapping("/recipes/save")
    public ModelAndView saveRecipe(
            @Valid @ModelAttribute("recipeInsertDTO") RecipeInsertDTO recipeInsertDTO,
            BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            LOGGER.info("binding has errors");
        }

        LOGGER.info("in save recipe");
        LOGGER.info(recipeInsertDTO.getTitle());

        recipeService.saveRecipe(recipeInsertDTO,recipeInsertDTO.getImgAttach());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/home");
        return modelAndView;

    }

    @GetMapping("/recipes/search")
    public List<RecipeReadOnlyDTO> getRecipeSearch(@RequestParam String title) {
        LOGGER.info("search title: " + title);
        return recipeService.getRecipeByInput(title);
    }

    @PutMapping("/recipes/edit")
    public void editRecipe() {

    }
}
