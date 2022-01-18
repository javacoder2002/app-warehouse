package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    public Result add(Currency currency) {

        if (currencyRepository.existsByName(currency.getName()))
            return new Result(false, "currency already exist!");

        currencyRepository.save(currency);
        return new Result(true, "currency saved!");
    }

    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }

    public Currency getById(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isEmpty())
            return new Currency();
        return optionalCurrency.get();
    }

    public Result edite(Integer id, Currency currency) {

        if (currencyRepository.existsByName(currency.getName()))
            return new Result(false, "currency already exist!");

        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isEmpty())
            return new Result(false, "currency not found!");
        Currency editeCurrency = optionalCurrency.get();
        editeCurrency.setName(currency.getName());
        currencyRepository.save(editeCurrency);
        return new Result(true, "currency edited!");

    }

    public Result delete(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isEmpty())
            return new Result(false, "currency not found!");
        currencyRepository.deleteById(id);
        return new Result(true, "currency deleted!");
    }
}
