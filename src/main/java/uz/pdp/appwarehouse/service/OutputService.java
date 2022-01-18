package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class OutputService {

    @Autowired
    OutputRepository outputRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    public Result add(OutputDto outputDto) {

        if (outputRepository.existsByFactureNumber(outputDto.getFactureNumber()))
            return new Result(false, "factureNumber already exist!");

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (optionalWarehouse.isEmpty())
            return new Result(false, "warehouse not found!");

        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (optionalClient.isEmpty())
            return new Result(false, "client not found!");

        Output output = new Output();
        output.setClient(optionalClient.get());
        output.setWarehouse(optionalWarehouse.get());
        output.setFactureNumber(outputDto.getFactureNumber());

        //factureNumber takkorlanmaydi
        output.setCode(outputDto.getFactureNumber());
        outputRepository.save(output);

        return new Result(true, "output saved!");
    }

    public List<Output> getAll() {
        return outputRepository.findAll();
    }

    public Output getById(Integer id) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        return optionalOutput.isEmpty() ? new Output() : optionalOutput.get();
    }

    public Result edite(Integer id, OutputDto outputDto) {

        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (optionalOutput.isEmpty())
            return new Result(false, "output not found!");

        if (outputRepository.existsByFactureNumber(outputDto.getFactureNumber()))
            return new Result(false, "factureNumber already exist!");

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (optionalWarehouse.isEmpty())
            return new Result(false, "warehouse not found!");

        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (optionalClient.isEmpty())
            return new Result(false, "client not found!");

        Output output = optionalOutput.get();
        output.setClient(optionalClient.get());
        output.setWarehouse(optionalWarehouse.get());
        output.setFactureNumber(outputDto.getFactureNumber());

        //factureNumber takkorlanmaydi
        output.setCode(outputDto.getFactureNumber());
        outputRepository.save(output);

        return new Result(true, "output edited!");

    }

    public Result delete(Integer id){
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (optionalOutput.isEmpty())
            return  new Result(false, "output not found!");
        outputRepository.deleteById(id);
        return new Result(true, "output deleted!");
    }
}
