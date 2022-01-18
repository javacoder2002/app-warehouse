package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.User;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Result add(User user) {

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber()))
            return new  Result(false, "phone number already exit!");

        User user1 = userRepository.save(user);
        user1.setCode(user1.getId() + "");
        userRepository.save(user1);
        return new Result(true, "user saved!");

    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getById(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.isEmpty() ? new User() : optionalUser.get();
    }

    public Result edite(Integer id, User user) {

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber()))
            return new  Result(false, "phone number already exit!");

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            return new Result(false, "user not found!");

        User editeUser = optionalUser.get();
        editeUser.setFirstName(user.getFirstName());
        editeUser.setLastName(user.getLastName());
        editeUser.setPhoneNumber(user.getPhoneNumber());
        editeUser.setPassword(user.getPassword());

        userRepository.save(editeUser);
        return new Result(true, "user edited!");

    }

    public Result delete(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            return new Result(false, "user not found!");
        userRepository.deleteById(id);
        return new Result(true, "user deleted!");
    }

}
