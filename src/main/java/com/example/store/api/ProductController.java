package com.example.store.api;

import com.example.store.dto.request.ProductRequest;
import com.example.store.dto.response.ProductResponse;
import com.example.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("add")
    public ResponseEntity<?> addProduct(
            @ModelAttribute("name") String name,
            @ModelAttribute("price") Integer price,
            @ModelAttribute("quantity") Integer quantity,
            @ModelAttribute("des") String des,
            @ModelAttribute("manu") Integer manu,
            @ModelAttribute("cate") Integer cate,
            @ModelAttribute("condition") Integer condition,
            @Valid @ModelAttribute("image") MultipartFile image
            ) throws FileNotFoundException {

                return ResponseEntity.status(HttpStatus.OK).body(
                    productService.addProduct(ProductRequest.builder()
                                        .name(name)
                                        .price(price)
                                        .quantity(quantity)
                                        .description(des)
                                        .manufacturer(manu)
                                        .category(cate)
                                        .condition(condition)
                                        .image(image)
                                        .build())
                                        );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteProduct(@Valid @PathVariable Integer id){
        productService.deleteProductById(id)
        return ResponseEntity.status(HttpStatus.OK).body("delete successful");
    }

    @GetMapping
    public ResponseEntity<?> getProductList(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getListProduct());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@Valid @PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductDetail(id));
    }

}
