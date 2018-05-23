package guru.spring.recipe.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
public class IndexController {

    @GetMapping({"", "index"})
    public String getIndexPage() {

        log.info("### indexController");
        return "redirect:/recipe";
    }


}
