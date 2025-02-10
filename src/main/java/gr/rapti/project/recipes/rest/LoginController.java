package gr.rapti.project.recipes.rest;

import gr.rapti.project.recipes.core.exceptions.EntityAlreadyExistsException;
import gr.rapti.project.recipes.core.exceptions.EntityNotAuthorized;
import gr.rapti.project.recipes.dto.RecipeInsertDTO;
import gr.rapti.project.recipes.dto.UserInsertDTO;
import gr.rapti.project.recipes.dto.UserReadDTO;
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
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);
    private final UserService userService;

    @GetMapping("/login")
    public ModelAndView getLoginForm(Model model){
        model.addAttribute("userReadDTO", new UserInsertDTO());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @PostMapping("/login")
    private ModelAndView loginUser(@Valid @ModelAttribute("userReadDTO") UserReadDTO userReadDTO,
                                    BindingResult bindingResult,
                                    Model model)
            throws EntityNotAuthorized {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            LOGGER.info(bindingResult.toString());
            return modelAndView;
        }

        if (userReadDTO.getPassword() != null &&
                userService.getPasswordByUsername(userReadDTO.getUsername()).equals(userReadDTO.getPassword())) {

            model.addAttribute("recipeInsertDTO", new RecipeInsertDTO());
            modelAndView.setViewName("home.html");
            return modelAndView;
        }

        return modelAndView;
    }

}
