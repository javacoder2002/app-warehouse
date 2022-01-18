package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping
    public Result add(@RequestBody Supplier supplier){
        return supplierService.add(supplier);
    }

    @GetMapping
    public List<Supplier> getAll(){
        return supplierService.getAll();
    }

    @GetMapping("/{id}")
    public Supplier getById(@PathVariable Integer id){
        return supplierService.getById(id);
    }

    @PutMapping("/{id}")
    public Result edite(@PathVariable Integer id, @RequestBody Supplier supplier){
        return supplierService.edite(id, supplier);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return supplierService.delete(id);
    }

}
