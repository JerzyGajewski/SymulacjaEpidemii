package pl.jerzygajewski.symulator.controler;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.jerzygajewski.symulator.entity.RecordInfo;
import pl.jerzygajewski.symulator.entity.StartData;
import pl.jerzygajewski.symulator.service.StartDataEntityService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class HomeController {

    private StartDataEntityService startDataEntityService;

    public HomeController(StartDataEntityService startDataEntityService) {
        this.startDataEntityService = startDataEntityService;
    }

    @PostMapping("/users")
    public StartData addUser(@Valid @RequestBody StartData startData) {
        return startDataEntityService.addParameters(startData);
    }

    @GetMapping("/users")
    public List<StartData> userList() {
        return startDataEntityService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public StartData userById(@PathVariable long id) {
        return startDataEntityService.getUserById(id);
    }

    @PutMapping("/users")
    public StartData edit(@Valid @RequestBody StartData startData) {
        return startDataEntityService.editUser(startData);
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable long id) {
        return startDataEntityService.deleteUser(id);
    }

    @GetMapping("/results/{id}")
    public List<RecordInfo> getRecordsByUser_Id(@PathVariable long id) {
        return startDataEntityService.getRecords(id);
    }
}
