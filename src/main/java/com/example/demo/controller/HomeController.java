package com.example.demo.controller;


import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    ProductRepository productRepository;

    @RequestMapping("/")
    public String home() {
        return "home";
    }


    @RequestMapping("/productList")
    public String getProducts(Model model) {
        //List<Product> products = productDao.getAllProducts();
        model.addAttribute("products", productRepository.findAll());

        return "productList";
    }

    @RequestMapping("/productList/viewProduct/{id}")
    public String viewProduct(@PathVariable("id") Long id, Model model) /*throws IOException */{

        //Product product = productDao.getProductById(productId);
        model.addAttribute("product", productRepository.findById(id).get());

        return "viewProduct";
    }
}
