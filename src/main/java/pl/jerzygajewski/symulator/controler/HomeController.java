package pl.jerzygajewski.symulator.controler;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.jerzygajewski.symulator.entity.RecordInfo;
import pl.jerzygajewski.symulator.entity.User;
import pl.jerzygajewski.symulator.service.UserEntityService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class HomeController {

    private UserEntityService userEntityService;

    public HomeController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        return userEntityService.addParameters(user);
    }

    @GetMapping("/users")
    public List<User> userList() {
        return userEntityService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User userById(@PathVariable long id) {
        return userEntityService.getUserById(id);
    }

    @PutMapping("/users")
    public User edit(@Valid @RequestBody User user) {
        return userEntityService.editUser(user);
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable long id) {
        return userEntityService.deleteUser(id);
    }

    @GetMapping("/results/{id}")
    public List<RecordInfo> getRecordsByUser_Id(@PathVariable long id) {
        return userEntityService.getRecords(id);
    }
}
