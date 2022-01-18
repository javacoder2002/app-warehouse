package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;


@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    public Result add(Warehouse warehouse) {

        if (warehouseRepository.existsByName(warehouse.getName()))
            return new Result(false, "warehouse already exist!");

        warehouseRepository.save(warehouse);
        return new Result(true, "warehouse saved!");
    }

    public List<Warehouse> getAll() {
        return warehouseRepository.findAll();
    }

    public Warehouse getById(Integer id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (optionalWarehouse.isEmpty())
            return new Warehouse();
        return optionalWarehouse.get();
    }

    public Result edite(Integer id, Warehouse warehouse) {

        if (warehouseRepository.existsByName(warehouse.getName()))
            return new Result(false, "warehouse already exist!");

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (optionalWarehouse.isEmpty())
            return new Result(false, "warehouse not found!");
        Warehouse editeWarehouse = optionalWarehouse.get();
        editeWarehouse.setName(warehouse.getName());

        warehouseRepository.save(editeWarehouse);
        return new Result(true, "warehouse edited!");

    }

    public Result delete(Integer id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (optionalWarehouse.isEmpty())
            return new Result(false, "warehouse not found!");
        warehouseRepository.deleteById(id);
        return new Result(true, "warehouse deleted!");
    }
}
