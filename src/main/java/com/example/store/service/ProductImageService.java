package com.example.store.service;

import com.example.store.dao.entity.ProductImage;
import com.example.store.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
public class ProductImageService {
    @Autowired
    ProductImageRepository productImageRepository;

    @Transactional
    public void storeImage(MultipartFile file, Integer id) {
        try {
            productImageRepository.save(ProductImage.builder()
                    .productId(id)
                    .image(file.getOriginalFilename())
                    .imageType(file.getContentType())
                    .imageData(file.getBytes())
                    .build());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public ProductImage getFileByProductId(Integer productId){
        try {
            return productImageRepository.getByProductId(productId);
        }catch (Exception e){
            return null;
        }
    }

    @Transactional
    public void deleteProductImage(Integer productId){
        try {
            productImageRepository.deleteByProductId(productId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
