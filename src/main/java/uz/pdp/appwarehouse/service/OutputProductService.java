package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.entity.Output;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class OutputProductService {

    @Autowired
    OutputProductRepository outputProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    OutputRepository outputRepository;


    public Result add(OutputProductDto outputProductDto) {

        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (optionalProduct.isEmpty())
            return new Result(false, "product not found!");

        Optional<Currency> optionalCurrency = currencyRepository.findById(outputProductDto.getCurrencyId());
        if (optionalCurrency.isEmpty())
            return new Result(false, "currency not found!");

        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (optionalOutput.isEmpty())
            return new Result(false, "output not found!");

        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setProduct(optionalProduct.get());
        outputProduct.setOutput(optionalOutput.get());
        outputProduct.setCurrency(optionalCurrency.get());
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());

        outputProductRepository.save(outputProduct);
        return new Result(true, "output_product saved!");

    }

    public List<OutputProduct> getAll() {
        return outputProductRepository.findAll();
    }

    public OutputProduct getById(Integer id) {
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        return optionalOutputProduct.isEmpty() ?  new OutputProduct() : optionalOutputProduct.get();
    }

    public Result edite(Integer id, OutputProductDto outputProductDto) {

        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        if (optionalOutputProduct.isEmpty())
            return new Result(false, "output_product not found!");

        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (optionalProduct.isEmpty())
            return new Result(false, "product not found!");

        Optional<Currency> optionalCurrency = currencyRepository.findById(outputProductDto.getCurrencyId());
        if (optionalCurrency.isEmpty())
            return new Result(false, "currency not found!");

        Optional<Output> optionaloutput = outputRepository.findById(outputProductDto.getOutputId());
        if (optionaloutput.isEmpty())
            return new Result(false, "output not found!");

        OutputProduct outputProduct = optionalOutputProduct.get();
        outputProduct.setProduct(optionalProduct.get());
        outputProduct.setOutput(optionaloutput.get());
        outputProduct.setCurrency(optionalCurrency.get());
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());

        outputProductRepository.save(outputProduct);
        return new Result(true, "output_product edited!");
    }

    public Result delete(Integer id) {

        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        if (optionalOutputProduct.isEmpty())
            return  new Result(false, "output_product not found!");
        outputProductRepository.deleteById(id);
        return new Result(true, "output product deleted!");
    }
}
