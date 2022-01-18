package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.InputProductService;

import java.util.List;

@RestController
@RequestMapping("/inputProduct")
public class InputProductController {

    @Autowired
    InputProductService inputProductService;

    @PostMapping
    public Result add(@RequestBody InputProductDto inputProductDtoDto) {
        return inputProductService.add(inputProductDtoDto);
    }

    @GetMapping
    public List<InputProduct> getAll() {
        return inputProductService.getAll();
    }

    @GetMapping("/{id}")
    public InputProduct getById(@PathVariable Integer id) {
        return inputProductService.getById(id);
    }

    @PutMapping("/{id}")
    public Result edite(@PathVariable Integer id, @RequestBody InputProductDto inputProductDto){
        return inputProductService.edite(id, inputProductDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return inputProductService.delete(id);
    }

}
