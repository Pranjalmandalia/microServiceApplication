package com.example.productservice.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product {} is generated", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

	@Scheduled(cron="0/5 * * * * *")
	public void randomAddToDb(){
////		String[] randomNames={"Galaxy","Apple","Samsung","Modicare","abcxyz","sajfnf","askjfnjkf","jaskndasf","askfnjsdf","eionjvsn","dsjknsdnfvn","dvneiojw"};
////		Product product = Product.builder()
////				.name(randomNames[new Random() .nextInt(randomNames.length)])
////                .description("A random generated product")
////                .price(new BigDecimal(Math.random()))
////                .build();
//
//        productRepository.save(product);
//        log.info("Product {} with name {} is generated on {}",product.getId(),product.getName(), new Date().toString());
//        log.info("Database Size {}",productRepository.count());

        Product product = Product.builder()
                .id("6329594c74ecb36be7bc957e")
                .name("123abc")
                .description("Try saving and updating")
                .price(new BigDecimal(25))
                .build();

        productRepository.save(product);
	}

    @Scheduled(cron = "0/20 * * * * *")
    public void scrapDb(){
//        String[] allowedNames={"Galaxy","Apple","Samsung","Modicare","123abc"};
        String[] allowedNames={"123abc"};
        List<Product> products = productRepository.findAll();
        List<Product> deletedProducts = new ArrayList<>();
        //AtomicReference<Product> atomicProduct = new AtomicReference<>();
        products.forEach(product -> {
           // atomicProduct.getAndSet(product);
            if(!Stream.of(allowedNames).anyMatch(e -> e.equalsIgnoreCase(product.getName()))){
                deletedProducts.add(product);
                log.info("Product {} with name {} is deleted on {}",product.getId(),product.getName(), new Date().toString());
                log.info("Database Size after scraping {}",products.size());
                productRepository.delete(product);
            }
        });

        Product product = Product.builder()
                .id("6329594c74ecb36be7bc957e")
                .name("123abc")
                .description("Try saving and updating")
                .price(new BigDecimal(55))
                .build();

        productRepository.save(product);
    }

    public void trydb(){
        Product product = Product.builder()
                .name("123abc")
                .description("Try saving and updating")
                .price(new BigDecimal(25))
                .build();

        productRepository.save(product);
    }

}