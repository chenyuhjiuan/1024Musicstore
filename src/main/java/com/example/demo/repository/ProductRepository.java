package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    /*void addProduct(Product product);*/

    //void editProduct(Product product);

    /*Product getProductById(String id);

    List<Product> getAllProducts();*/

    //void deleteProduct(Long id);
   ProductRepository findById(Long id);
    ProductRepository deleteById(Long id);


    Object get();
}
