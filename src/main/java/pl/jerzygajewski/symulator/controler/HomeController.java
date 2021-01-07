package pl.jerzygajewski.symulator.controler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jerzygajewski.symulator.entity.RecordInfoEntity;
import pl.jerzygajewski.symulator.entity.User;
import pl.jerzygajewski.symulator.repository.UserRepository;
import pl.jerzygajewski.symulator.service.UserEntityService;

import java.util.List;

@RestController
public class HomeController {

    private UserEntityService userEntityService;

    public HomeController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

   @PostMapping("/users")
    public User addUser(@RequestBody User user){
    return userEntityService.addParameters(user);
   }

   @GetMapping("/users")
    public List<User> userList(){
        return userEntityService.getAllUsers();
   }

   @GetMapping("/users/{id}")
    public User userById(@PathVariable Long id){
        return userEntityService.getUserById(id);
   }

   @PutMapping("/users")
    public User edit(@RequestBody User user){
        return userEntityService.editUser(user);
   }

   @DeleteMapping("/users")
    public String delete(@PathVariable Long id){
        return userEntityService.deleteUser(id);
   }

   @GetMapping("/results/{id}")
    public List<RecordInfoEntity> getRecordsByUser_Id(@PathVariable long id){ return userEntityService.getRecords(id);}
}
