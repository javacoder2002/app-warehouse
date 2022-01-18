package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    @Autowired
    MeasurementRepository measurementRepository;

    public Result addMeasurementService(Measurement measurement) {

        String name = measurement.getName();
        boolean existsByName = measurementRepository.existsByName(name);
        if (existsByName)
            return new Result(
                    false,
                    "this measurement already exist!"
            );

        measurementRepository.save(measurement);
        return new Result(
                true,
                "measurement saved successfully!"
        );

    }

    public List<Measurement> getAllMeasurement() {
        return measurementRepository.findAll();
    }

    public Measurement getMeasurementById(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (optionalMeasurement.isEmpty())
            return new Measurement();
        return optionalMeasurement.get();
    }

    public Result editeMeasurement(Integer id, Measurement measurement) {

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (optionalMeasurement.isEmpty())
            return new Result(false, "measurement not found!");

        String name = measurement.getName();
        boolean existsByName = measurementRepository.existsByName(name);
        if (existsByName)
            return new Result(false, "this measurement already exist!");

        Measurement editeMeasurement = optionalMeasurement.get();
        editeMeasurement.setName(name);
        editeMeasurement.setActive(measurement.isActive());
        measurementRepository.save(measurement);

        return result(true,"measurement edited!");
    }

    public Result deleteMeasurement(Integer id){
        if (measurementRepository.findById(id).isEmpty())
            return result(false,"measurement not found!");
        measurementRepository.deleteById(id);
        return result(true,"measurement deleted!");
    }

    // my method
    private Result result(boolean suc, String mes){
        return new Result(suc,mes);
    }

}
