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
public class RegisterController {

    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);
    private final Mapper mapper;
    private final UserRepository userRepository;

    @GetMapping("/register")
    public ModelAndView getRegisterForm(Model model){
        model.addAttribute("userInsertDTO", new UserInsertDTO());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register.html");
        LOGGER.info(model.toString());
        return modelAndView;
    }

    @PostMapping("/register")
    private ModelAndView insertUser(@Valid @ModelAttribute("userInsertDTO")UserInsertDTO userInsertDTO,
                                    BindingResult bindingResult,
                                    Model model)
            throws EntityAlreadyExistsException {

        LOGGER.info("in Post register");
        LOGGER.info(userInsertDTO.getUsername());
        LOGGER.info(userRepository.findByUsername(userInsertDTO.getUsername()).toString());
        if (userRepository.findByUsername(userInsertDTO.getUsername()).isPresent()){
            LOGGER.info("Before throw exception");
            throw new EntityAlreadyExistsException("User: ", "User with username: "+ userInsertDTO.getUsername() + " already exists.");
        }

        ModelAndView modelAndView = new ModelAndView();

        if(bindingResult.hasErrors()){

            LOGGER.info(bindingResult.toString());
            return modelAndView;
        }

        User user = mapper.mapToUserEntity(userInsertDTO);
        userService.saveUser(user);
        model.addAttribute("recipeInsertDTO", new RecipeInsertDTO());
        modelAndView.setViewName("home.html");
        return modelAndView;
    }

}
