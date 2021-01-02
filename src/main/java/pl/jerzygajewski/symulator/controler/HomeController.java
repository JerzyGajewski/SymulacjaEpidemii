package pl.jerzygajewski.symulator.controler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jerzygajewski.symulator.entity.User;
import pl.jerzygajewski.symulator.repository.UserRepository;
import pl.jerzygajewski.symulator.service.UserEntityService;

import java.util.List;

@RestController
public class HomeController {

    private UserRepository userRepository;
    private UserEntityService userEntityService;

    public HomeController(UserRepository userRepository, UserEntityService userEntityService) {
        this.userRepository = userRepository;
        this.userEntityService = userEntityService;
    }

   @PostMapping("/add")
    public User addUser(@RequestBody User user){
    return userEntityService.addParameters(user);
   }

   @GetMapping("/read")
    public List<User> userList(){
        return userEntityService.getAllUsers();
   }

   @GetMapping("/read/{id}")
    public User userById(@PathVariable Long id){
        return userEntityService.getUserById(id);
   }

   @PutMapping("/edit")
    public User edit(@PathVariable User user){
        return userEntityService.editUser(user);
   }

   @DeleteMapping("/delete")
    public String delete(@PathVariable Long id){
        return userEntityService.deleteUser(id);
   }


}
