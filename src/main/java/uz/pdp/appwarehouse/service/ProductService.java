package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.ProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentRepository;
import uz.pdp.appwarehouse.repository.CategoryRepository;
import uz.pdp.appwarehouse.repository.MeasurementRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    MeasurementRepository measurementRepository;

    public Result addProduct(ProductDto productDto) {

        boolean exists = productRepository.existsByNameAndCategoryId(
                productDto.getName(),
                productDto.getCategoryId()
        );
        if (exists)
            return new Result(false, "This product already exist in this category!");

        Product product = new Product();
        product.setName(productDto.getName());
        //product.setCode("1");// generatsiya qilsish kerak

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalCategory.isEmpty())
            return new Result(false, "Category not found");
        product.setCategory(optionalCategory.get());

        Optional<Attachment> attachmentOptional = attachmentRepository.findById(productDto.getAttachmentId());
        if (attachmentOptional.isEmpty())
            return new Result(false, "photo not found!");
        product.setPhoto(attachmentOptional.get());

        Optional<Measurement> measurementOptional = measurementRepository.findById(productDto.getMeasurementId());
        if (measurementOptional.isEmpty())
            return new Result(false, "measurement not found!");
        product.setMeasurement(measurementOptional.get());

        Product saveProduct = productRepository.save(product);

        //code takrorlanmaydi va unga qarab id, measurementId, attachmentId va categoryId sini aniqlash mumkin bo'ladi
        product.setCode(saveProduct.getId() + "0" + productDto.getMeasurementId() + "0" + productDto.getAttachmentId() + "0" + productDto.getCategoryId());
        productRepository.save(saveProduct);

        return new Result(true, "product saved successfully!");

    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Integer id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.isEmpty() ? new Product() : optionalProduct.get();
    }

    public Result edite(Integer id, ProductDto productDto) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty())
            return new Result(false, "product not found!");

        boolean exists = productRepository.existsByNameAndCategoryId(
                productDto.getName(),
                productDto.getCategoryId()
        );
        if (exists)
            return new Result(false, "This product already exist in this category!");


        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalCategory.isEmpty())
            return new Result(false, "Category not found");

        Optional<Attachment> attachmentOptional = attachmentRepository.findById(productDto.getAttachmentId());
        if (attachmentOptional.isEmpty())
            return new Result(false, "photo not found!");

        Optional<Measurement> measurementOptional = measurementRepository.findById(productDto.getMeasurementId());
        if (measurementOptional.isEmpty())
            return new Result(false, "measurement not found!");

        Product product = optionalProduct.get();
        product.setName(productDto.getName());

        //code takrorlanmaydi va unga qarab id, measurementId, attachmentId va categoryId sini aniqlash mumkin bo'ladi
        product.setCode(id + "0" + productDto.getMeasurementId() + "0" + productDto.getAttachmentId() + "0" + productDto.getCategoryId());
        product.setCategory(optionalCategory.get());
        product.setPhoto(attachmentOptional.get());
        product.setMeasurement(measurementOptional.get());

        productRepository.save(product);
        return new Result(true, "product saved successfully!");
    }

    public Result delete(Integer id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty())
            return new Result(false, "product not found!");
        productRepository.deleteById(id);
        return new Result(true, "product deleted");
    }

}
