package guru.spring.recipe.controllers;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import guru.spring.recipe.commands.RecipeCommand;
import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.services.ImageService;
import guru.spring.recipe.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private ImageService imageService;
    private RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String showUploadForm(@PathVariable Long recipeId, Model model){

        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable Long recipeId, @RequestParam("imagefile") MultipartFile file){

        imageService.saveImageFile(recipeId, file);

        return "redirect:/recipe/"+recipeId+"/show";
    }

    @GetMapping("/recipe/{recipeId}/recipeimage")
    public void renderImageFromDb(@PathVariable Long recipeId, HttpServletResponse response) throws IOException {

        Recipe recipe = recipeService.getRecipeById(recipeId);
        response.setContentType("image/jpeg");

        InputStream is = (recipe.getImage() != null) ?
                new ByteArrayInputStream(recipe.getImage()) :
                new FileInputStream(new ClassPathResource("/static/images/default.jpg").getFile());


        IOUtils.copy(is, response.getOutputStream());

    }


}
