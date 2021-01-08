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

    @PostMapping("/datas")
    public StartData addUser(@Valid @RequestBody StartData startData) {
        return startDataEntityService.addParameters(startData);
    }

    @GetMapping("/datas")
    public List<StartData> userList() {
        return startDataEntityService.getAllDatas();
    }

    @GetMapping("/datas/{id}")
    public StartData userById(@PathVariable long id) {
        return startDataEntityService.getDataById(id);
    }

    @PutMapping("/datas")
    public StartData edit(@Valid @RequestBody StartData startData) {
        return startDataEntityService.editStartData(startData);
    }

    @DeleteMapping("/datas/{id}")
    public String delete(@PathVariable long id) {
        return startDataEntityService.deleteStartData(id);
    }

    @GetMapping("/results/{id}")
    public List<RecordInfo> getRecordsByUser_Id(@PathVariable long id) {
        return startDataEntityService.getRecords(id);
    }
}
