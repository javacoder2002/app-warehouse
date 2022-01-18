package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    public Result add(Supplier supplier) {

        if (supplierRepository.existsByPhoneNumber(supplier.getPhoneNumber()))
            return new Result(false, "supplier already exist!");

        supplierRepository.save(supplier);
        return new Result(true, "supplier saved!");

    }

    public List<Supplier> getAll(){
        return supplierRepository.findAll();
    }

    public Supplier getById(Integer id){
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        return optionalSupplier.isEmpty() ? new Supplier() : optionalSupplier.get();
    }

    public Result edite(Integer id, Supplier supplier){

        if (supplierRepository.existsByPhoneNumber(supplier.getPhoneNumber()))
            return new Result(false, "supplier already exist!");

        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isEmpty())
            return new Result(false, "supplier not found!");
        Supplier editeSupplier = optionalSupplier.get();
        editeSupplier.setName(supplier.getName());
        editeSupplier.setPhoneNumber(supplier.getPhoneNumber());

        supplierRepository.save(editeSupplier);
        return new Result(true, "supplier saved!");
    }

    public Result delete(Integer id){
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isEmpty())
            return new Result(false, "supplier not found!");
        supplierRepository.deleteById(id);
        return new Result(true, "supplier deleted!");
    }

}
