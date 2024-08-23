package com.product.service.service;


import com.product.service.entities.Product;
import com.product.service.jpa.ProductRepo;
import com.retail.api.gateway.dto.ProductDTO;
import com.retail.api.gateway.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepo productRepo;


    @Cacheable(value = "products", key = "#ids")
    public List<ProductDTO> getProducts(List<String> ids) {
        List<Product> products = productRepo.findAllByProductIdIn(ids);


        List<ProductDTO> productDTOList = products.stream()
                .map(this::getProductDTO)
                .toList();

        if(CollectionUtils.isEmpty(productDTOList)) {
            return Collections.EMPTY_LIST;
        }

        return productDTOList;
    }

    public void createProducts(ProductRequest productRequest) {

        List<ProductDTO> productDTOS = productRequest.getProductDTOList();

        List<Product> products =
        productDTOS.stream()
                .map(this::getProduct)
                .toList();

        productRepo.saveAll(products);

    }

    private ProductDTO getProductDTO(Product product) {

        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStockQuantity(product.getStockQuantity());

        return productDTO;
    }

    private Product getProduct(ProductDTO productDTO) {

        if (productDTO == null) {
            return null;
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());

        return product;
    }



}
