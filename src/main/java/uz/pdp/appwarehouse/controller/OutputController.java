package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Output;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.OutputService;

import java.util.List;

@RestController
@RequestMapping("/output")
public class OutputController {

    @Autowired
    OutputService outputService;

    @PostMapping
    public Result add(@RequestBody OutputDto outputDto) {
        return outputService.add(outputDto);
    }

    @GetMapping
    public List<Output> getAll() {
        return outputService.getAll();
    }

    @GetMapping("/{id}")
    public Output getById(@PathVariable Integer id) {
        return outputService.getById(id);
    }

    @PutMapping("/{id}")
    public Result edite(@PathVariable Integer id, @RequestBody OutputDto outputDto){
        return outputService.edite(id, outputDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return outputService.delete(id);
    }

}
