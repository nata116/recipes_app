package gr.rapti.project.recipes.rest;

import gr.rapti.project.recipes.core.exceptions.EntityAlreadyExistsException;
import gr.rapti.project.recipes.dto.RecipeInsertDTO;
import gr.rapti.project.recipes.dto.UserInsertDTO;
import gr.rapti.project.recipes.mapper.Mapper;
import gr.rapti.project.recipes.model.User;
import gr.rapti.project.recipes.repository.UserRepository;
import gr.rapti.project.recipes.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class HomeController {

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView homePage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("recipeInsertDTO", new RecipeInsertDTO());
        modelAndView.setViewName("home.html");
        return modelAndView;
    }
}
