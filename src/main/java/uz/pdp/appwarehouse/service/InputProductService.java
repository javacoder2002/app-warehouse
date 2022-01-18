package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;
import uz.pdp.appwarehouse.repository.InputProductRepository;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InputProductService {

    @Autowired
    InputProductRepository inputProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    InputRepository inputRepository;


    public Result add(InputProductDto inputProductDto) {

        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (optionalProduct.isEmpty())
            return new Result(false, "product not found!");

        Optional<Currency> optionalCurrency = currencyRepository.findById(inputProductDto.getCurrencyId());
        if (optionalCurrency.isEmpty())
            return new Result(false, "currency not found!");

        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (optionalInput.isEmpty())
            return new Result(false, "input not found!");

        InputProduct inputProduct = new InputProduct();
        inputProduct.setProduct(optionalProduct.get());
        inputProduct.setInput(optionalInput.get());
        inputProduct.setCurrency(optionalCurrency.get());
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setPrice(inputProductDto.getPrice());

        inputProductRepository.save(inputProduct);
        return new Result(true, "input_product saved!");

    }

    public List<InputProduct> getAll() {
        return inputProductRepository.findAll();
    }

    public InputProduct getById(Integer id) {
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        return optionalInputProduct.isEmpty() ?  new InputProduct() : optionalInputProduct.get();
    }

    public Result edite(Integer id, InputProductDto inputProductDto) {

        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        if (optionalInputProduct.isEmpty())
            return new Result(false, "input_product not found!");

        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (optionalProduct.isEmpty())
            return new Result(false, "product not found!");

        Optional<Currency> optionalCurrency = currencyRepository.findById(inputProductDto.getCurrencyId());
        if (optionalCurrency.isEmpty())
            return new Result(false, "currency not found!");

        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (optionalInput.isEmpty())
            return new Result(false, "input not found!");

        InputProduct inputProduct = optionalInputProduct.get();
        inputProduct.setProduct(optionalProduct.get());
        inputProduct.setInput(optionalInput.get());
        inputProduct.setCurrency(optionalCurrency.get());
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setPrice(inputProductDto.getPrice());

        inputProductRepository.save(inputProduct);
        return new Result(true, "input_product edited!");
    }

    public Result delete(Integer id) {

        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        if (optionalInputProduct.isEmpty())
            return  new Result(false, "input_product not found!");
        inputProductRepository.deleteById(id);
        return new Result(true, "input product deleted!");
    }
}
