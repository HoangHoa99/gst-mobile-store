package com.example.store.map;

import com.example.store.dao.entity.Category;
import com.example.store.dao.entity.Manufacturer;
import com.example.store.dao.entity.Product;
import com.example.store.dto.response.ProductResponse;
import com.example.store.repository.CategoryRepository;
import com.example.store.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class ProductMap {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ManufacturerRepository manufacturerRepository;

    public ProductResponse mapResult(Product product){

        String imageLink = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/product-images/")
                .path(product.getId().toString())
                .toUriString();

        Category category = categoryRepository.getOne(product.getCategory());
        Manufacturer manufacturer = manufacturerRepository.getOne(product.getManufacturer());

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getProductName())
                .price(product.getUnitPrice())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .manufacturer(manufacturer.getName())
                .category(category.getName())
                .condition(product.getCondition().toString())
                .image(imageLink)
                .build();
    }
}
