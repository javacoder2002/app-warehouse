package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.InputService;

import java.util.List;

@RestController
@RequestMapping("/input")
public class InputController {

    @Autowired
    InputService inputService;

    @PostMapping
    public Result add(@RequestBody InputDto inputDto) {
        return inputService.add(inputDto);
    }

    @GetMapping
    public List<Input> getAll() {
        return inputService.getAll();
    }

    @GetMapping("/{id}")
    public Input getById(@PathVariable Integer id) {
        return inputService.getById(id);
    }

    @PutMapping("/{id}")
    public Result edite(@PathVariable Integer id, @RequestBody InputDto inputDto){
        return inputService.edite(id, inputDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return inputService.delete(id);
    }

}
