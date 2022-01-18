package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.MeasurementService;

import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    @PostMapping
    public Result addMeasurementController(@RequestBody Measurement measurement) {
        return measurementService.addMeasurementService(measurement);
    }

    @GetMapping
    public List<Measurement> getAll(){
        return measurementService.getAllMeasurement();
    }

    @GetMapping("/{id}")
    public Measurement getById(@PathVariable Integer id){
        return measurementService.getMeasurementById(id);
    }

    @PutMapping("/{id}")
    public Result edite(@PathVariable Integer id, @RequestBody Measurement measurement){
        return measurementService.editeMeasurement(id,measurement);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return measurementService.deleteMeasurement(id);
    }

}
