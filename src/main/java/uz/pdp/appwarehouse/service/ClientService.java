package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Client;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.ClientRepository;
import uz.pdp.appwarehouse.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Result add(Client client) {

        if (clientRepository.existsByPhoneNumber(client.getPhoneNumber()))
            return new Result(false, "client already exist!");

        clientRepository.save(client);
        return new Result(true, "client saved!");

    }

    public List<Client> getAll(){
        return clientRepository.findAll();
    }

    public Client getById(Integer id){
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.isEmpty() ? new Client() : optionalClient.get();
    }

    public Result edite(Integer id, Client client){

        if (clientRepository.existsByPhoneNumber(client.getPhoneNumber()))
            return new Result(false, "client already exist!");

        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty())
            return new Result(false, "client not found!");
        Client clientEdite = optionalClient.get();

        clientEdite.setName(client.getName());
        clientEdite.setPhoneNumber(client.getPhoneNumber());

        clientRepository.save(clientEdite);
        return new Result(true, "client edited!");
    }

    public Result delete(Integer id){
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty())
            return new Result(false, "client not found!");
        clientRepository.deleteById(id);
        return new Result(true, "client deleted!");
    }

}
