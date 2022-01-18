package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.OutputProductService;

import java.util.List;

@RestController
@RequestMapping("/outputProduct")
public class OutputProductController {

    @Autowired
    OutputProductService outputProductService;

    @PostMapping
    public Result add(@RequestBody OutputProductDto outputProductDtoDto) {
        return outputProductService.add(outputProductDtoDto);
    }

    @GetMapping
    public List<OutputProduct> getAll() {
        return outputProductService.getAll();
    }

    @GetMapping("/{id}")
    public OutputProduct getById(@PathVariable Integer id) {
        return outputProductService.getById(id);
    }

    @PutMapping("/{id}")
    public Result edite(@PathVariable Integer id, @RequestBody OutputProductDto outputProductDto){
        return outputProductService.edite(id, outputProductDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return outputProductService.delete(id);
    }

}
