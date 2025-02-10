package gr.rapti.project.recipes.service;

import gr.rapti.project.recipes.dto.RecipeInsertDTO;
import gr.rapti.project.recipes.dto.RecipeReadOnlyDTO;
import gr.rapti.project.recipes.mapper.Mapper;
import gr.rapti.project.recipes.model.Attachment;
import gr.rapti.project.recipes.model.Recipe;
import gr.rapti.project.recipes.repository.AttachmentRepository;
import gr.rapti.project.recipes.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final AttachmentRepository attachmentRepository;
    private final Mapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(Exception.class);

    @Transactional
    public void saveRecipe(RecipeInsertDTO recipeInsertDTO, MultipartFile recipeImage) throws Exception,
            IOException {

        LOGGER.info("In saveRecipe");
        Recipe recipe = mapper.mapToRecipeEntity(recipeInsertDTO);
        if (!recipeImage.isEmpty()){
            recipe.setAttachment(saveRecipeImage(recipe,recipeImage));
        }
        recipeRepository.save(recipe);

    }

    @Transactional(rollbackFor = Exception.class)
    public Attachment saveRecipeImage(Recipe recipe, MultipartFile recipeImage)throws IOException{

        if (recipeImage != null && !recipeImage.isEmpty()){
            //Get the original file name
            String originalFilename = recipeImage.getOriginalFilename();
            // Generate a unique saved name for the file to avoid conflicts
            String savedName = UUID.randomUUID().toString() + getFileExtension(originalFilename);
            // Define the file path where the file will be stored
            String uploadDirectory = "uploads/";
            Path filePath = Paths.get(uploadDirectory + savedName);
            // Create directories if they don't exist
            Files.createDirectories(filePath.getParent());

            // Write the file to the file system
            Files.write(filePath, recipeImage.getBytes());

            // Create an Attachment object to store file metadata
            Attachment attachment = new Attachment();
            attachment.setFilename(originalFilename);           // Set the original file name
            attachment.setSavedName(savedName);                 // Set the saved (unique) file name
            attachment.setFilePath(filePath.toString());        // Full path to the file
            attachment.setContentType(recipeImage.getContentType()); // Set the content type (MIME type)
            attachment.setExtension(getFileExtension(originalFilename)); // Set the file extension

            attachmentRepository.save(attachment);             // Link the Attachment to the Recipe entity

            return attachment;
        }
        return null;
    }

    // Helper method to extract the file extension
    private String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf("."));
        }
        return "";
    }

    @Transactional
    public void editRecipe() {
    }

    public List<RecipeReadOnlyDTO> getRecipeByInput(String recipeTitle) {

        List<Recipe> recipeList = recipeRepository.findByTitleStartingWithIgnoreCase(recipeTitle);
        LOGGER.info("size of list :" + String.valueOf(recipeList.size()));
        List<RecipeReadOnlyDTO> recipeReadOnlyList = new ArrayList<RecipeReadOnlyDTO>();
        for (Recipe r : recipeList) {
            RecipeReadOnlyDTO recipeReadOnly = new RecipeReadOnlyDTO();
            LOGGER.info("In Loop: attachment :"+r.getAttachment());
            recipeReadOnly.setTitle(r.getTitle());
            recipeReadOnly.setIngredients(r.getIngredients());
            recipeReadOnly.setPrepTime(r.getPrepTime());
            recipeReadOnly.setCookTime(r.getCookTime());
            recipeReadOnly.setTotalTime(r.getTotalTime());
            recipeReadOnly.setServings(r.getServings());
            recipeReadOnly.setDirection(r.getDirection());
            if (r.getAttachment() != null) {
                recipeReadOnly.setFilePath(r.getAttachment().getFilePath());
                recipeReadOnly.setFilename(r.getAttachment().getFilename());
                recipeReadOnly.setExtension(r.getAttachment().getExtension());
            }
            else {
            }
            recipeReadOnlyList.add(recipeReadOnly);
        }
        return recipeReadOnlyList;
    }
}
