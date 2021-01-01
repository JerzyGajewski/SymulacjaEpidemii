package pl.jerzygajewski.symulator.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jerzygajewski.symulator.entity.UserEntity;
import pl.jerzygajewski.symulator.repository.UserRepository;
import pl.jerzygajewski.symulator.service.UserEntityService;

@Controller
public class HomeController {

    private UserRepository userRepository;
    private UserEntityService userEntityService;

    public HomeController(UserRepository userRepository, UserEntityService userEntityService) {
        this.userRepository = userRepository;
        this.userEntityService = userEntityService;
    }

    @GetMapping()
    public String home(Model model) {
        model.addAttribute("userE", new UserEntity());
        return "test";
    }

    @PostMapping()
    public String filled(@ModelAttribute("userE") UserEntity userEntity){
    userRepository.save(userEntity);
    return "compleet";
    }


}
