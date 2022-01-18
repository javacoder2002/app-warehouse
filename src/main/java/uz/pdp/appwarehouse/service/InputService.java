package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class InputService {

    @Autowired
    InputRepository inputRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    public Result add(InputDto inputDto) {

        if (inputRepository.existsByFactureNumber(inputDto.getFactureNumber()))
            return new Result(false, "factureNumber already exist!");

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (optionalWarehouse.isEmpty())
            return new Result(false, "warehouse not found!");

        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (optionalSupplier.isEmpty())
            return new Result(false, "supplier not found!");

        Input input = new Input();
        input.setSupplier(optionalSupplier.get());
        input.setWarehouse(optionalWarehouse.get());
        input.setFactureNumber(inputDto.getFactureNumber());

        //factureNumber takkorlanmaydi
        input.setCode(inputDto.getFactureNumber());
        inputRepository.save(input);

        return new Result(true, "input saved!");
    }

    public List<Input> getAll() {
        return inputRepository.findAll();
    }

    public Input getById(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        return optionalInput.isEmpty() ? new Input() : optionalInput.get();
    }

    public Result edite(Integer id, InputDto inputDto) {

        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isEmpty())
            return new Result(false, "input already exist!");

        if (inputRepository.existsByFactureNumber(inputDto.getFactureNumber()))
            return new Result(false, "facture number already exist!");

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (optionalWarehouse.isEmpty())
            return new Result(false, "this warehouse not found!");

        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (optionalSupplier.isEmpty())
            return new Result(false, "supplier not found!");

        Input input = optionalInput.get();
        input.setSupplier(optionalSupplier.get());
        input.setWarehouse(optionalWarehouse.get());
        input.setFactureNumber(inputDto.getFactureNumber());

        //factureNumber takkorlanmaydi
        input.setCode(inputDto.getFactureNumber());
        inputRepository.save(input);

        return new Result(true, "input edited!");
    }

    public Result delete(Integer id){
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isEmpty())
            return  new Result(false, "input not found!");
        inputRepository.deleteById(id);
        return new Result(true, "input deleted!");
    }
}
