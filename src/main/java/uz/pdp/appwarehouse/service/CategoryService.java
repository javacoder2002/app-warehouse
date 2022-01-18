package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.payload.CategoryDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Result addCategory(CategoryDto categoryDto) {

        Category category = new Category();
        category.setName(categoryDto.getName());

        if (categoryDto.getParentCategoryId() != null) {

            Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (optionalCategory.isEmpty())
                return new Result(false, "parent category not found!");

            //biror(parent) categoriyaning ichida ikkita bir xil kategoriya bolmasligi kerak
            if (categoryRepository.existsByNameAndParentCategoryId(categoryDto.getName(), categoryDto.getParentCategoryId()))
                return new Result(false, "this category already exist!");

            Category parentCategory = optionalCategory.get();
            category.setParentCategory(parentCategory);

        } else {
            //agar categoriya null bo'lsa demak uning parent categoriyasi yo'q

            if (categoryRepository.existsByName(categoryDto.getName()))
                return new Result(false, "category already exist!");

        }

        categoryRepository.save(category);
        return new Result(true, "category saved!");

    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty())
            return new Category();
        return optionalCategory.get();
    }

    public Result edite(Integer id, CategoryDto categoryDto) {

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty())
            return new Result(false, "categoriya not found!");

        Category category = optionalCategory.get();
        category.setName(categoryDto.getName());

        if (categoryDto.getParentCategoryId() != null) {

            Optional<Category> optionalParentCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (optionalParentCategory.isEmpty())
                return new Result(false, "parent category not found!");

            //biror(parent) categoriyaning ichida ikkita bir xil kategoriya bolmasligi kerak
            if (categoryRepository.existsByNameAndParentCategoryId(categoryDto.getName(), categoryDto.getParentCategoryId()))
                return new Result(false, "this category already exist!");

            Category parentCategory = optionalCategory.get();
            category.setParentCategory(parentCategory);

        } else {
            //agar categoriya null bo'lsa demak uning parent categoriyasi yo'q

            if (categoryRepository.existsByName(categoryDto.getName()))
                return new Result(false, "category already exist!");

        }

        categoryRepository.save(category);
        return new Result(true, "category saved!");

    }

    public Result delete(Integer id) {
        try {
            if (categoryRepository.findById(id).isEmpty())
                return new Result(false, "category not found!");
            categoryRepository.deleteById(id);
            return new Result(true, "category deleted!");
        }catch (Exception exception){
            //agar ichida kateforiyasi bor parent categoriyani o'chirmoqchi bolsa shu yerga keladi
            return new Result(false, "category not deleted!");
        }
    }

}
