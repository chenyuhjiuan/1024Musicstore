package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;



import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;



@Controller
public class AdminController {

    private Path path;

    @Autowired
    ProductRepository productRepository;

    @RequestMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @RequestMapping("/admin/productInventory")
    public String productInventory(Model model) {

        model.addAttribute("products", productRepository.findAll());

        return "productInventory";
    }


    @RequestMapping("/admin/productInventory/addProduct")
    public String addProduct(Model model) {
        Product product = new Product();
        product.setProductCategory("instrument");
        product.setProductCondition("new");
        product.setProductStatus("active");
        productRepository.save(product);
        model.addAttribute("product", product);

        return "addProduct";
    }

    @RequestMapping("/admin/productInventory/addProduct")
    public String addProductPost(@Valid Product product, BindingResult result,
                                 Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "addProduct";
        }
        productRepository.save(product);
        //productDao.addProduct(product);

        MultipartFile productImage = product.getProductImage();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        path = Paths.get(rootDirectory + "\\images\\" + product.getProductId() + ".png");

        if (productImage != null && !productImage.isEmpty()) {
            try {
                productImage.transferTo(new File(path.toString()));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Product image saving failed", e);
            }
        }

        return "redirect:/admin/productInventory";
    }


    @RequestMapping("/admin/productInventory/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {

        productRepository.deleteById(id);

        return "redirect:/admin/productInventory";
    }


    @RequestMapping("/admin/productInventory/editProduct/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        //Product product = productDao.getProductById(id);

        model.addAttribute("product", productRepository.findById(id).get());

        return "editProduct";
    }

    @RequestMapping("/admin/productInventory/editProduct/{id}")
    public String editProduct(@PathVariable("id") Long id, Product product, BindingResult result, Model model,
                              HttpServletRequest request) {

        if (result.hasErrors()) {
            return "editProduct";
        }

        MultipartFile productImage = product.getProductImage();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        path = Paths.get(rootDirectory + "\\images\\"+product.getProductId()+".png");

        if (productImage != null && !productImage.isEmpty()) {
            try {
                productImage.transferTo(new File(path.toString()));
            } catch (Exception e) {
                throw new RuntimeException("Product image saving failed" , e);
            }
        }

        model.addAttribute("product", productRepository.findById(id).get());

        return "redirect:/admin/productInventory";
    }
}
