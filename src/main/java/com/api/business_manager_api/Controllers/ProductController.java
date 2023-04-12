package com.api.business_manager_api.Controllers;

import com.api.business_manager_api.Dtos.ProductDto;
import com.api.business_manager_api.Mappers.ProductMapper;
import com.api.business_manager_api.Models.CategoryModel;
import com.api.business_manager_api.Models.ProductModel;
import com.api.business_manager_api.Services.CategoryService;
import com.api.business_manager_api.Services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/product")
public class ProductController {
    final ProductService productService;
    final CategoryService categoryService;
    final ProductMapper productMapper;
    final S3Client s3Client;

    private final String BUCKET_NAME = "awstockproducts";
    private final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    public ProductController(ProductService productService, ProductMapper productMapper, CategoryService categoryService, S3Client s3Client) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
        this.s3Client = s3Client;
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> saveProduct(@RequestParam("productDto") String jsonString, @RequestParam("file") MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductDto productDto = objectMapper.readValue(jsonString, ProductDto.class);

            if (productService.existsByProduct(productDto.getProduct())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already exists!");
            }

            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image file is empty");
            }

            if (file.getSize() > MAX_FILE_SIZE) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image file size is too large. Maximum allowed size is 5MB");
            }

            ProductModel productModel = productMapper.toProductModel(productDto);
            CategoryModel categoryModel = categoryService.findById(productDto.getProductCategory().getCategory_id())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            productModel.setProductCategory(categoryModel);

            String fileName = "/products/images/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();


            s3Client.putObject(PutObjectRequest
                            .builder()
                            .bucket(BUCKET_NAME)
                            .key(fileName)
                            .build(),
                    software.amazon.awssdk.core.sync.RequestBody.fromString("Testing java sdk"));

            productModel.setImage_url(fileName);

            return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productModelOptional = productService.findById(id);
        if (!productModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestParam(name = "file", required = false) MultipartFile file,
                                                @RequestParam(name = "productDto") String jsonString) {
        Optional<ProductModel> productModelOptional = productService.findById(id);
        if (!productModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }

        try {
            ProductDto productDto = new ObjectMapper().readValue(jsonString, ProductDto.class);
            ProductModel productModel = productModelOptional.get();
            productModel.setProduct(productDto.getProduct());
            productModel.setDescription(productDto.getDescription());
            productModel.setPrice(productDto.getPrice());
            productModel.setExtraPrice(productDto.getExtraPrice());
            productModel.setStock(productDto.getStock());
            productModel.setProductCategory(productDto.getProductCategory());

            if (file != null && !file.isEmpty()) {
                String oldImageUrl = productModel.getImage_url();
                if (oldImageUrl != null) {
                    String oldFileName = oldImageUrl.substring(oldImageUrl.lastIndexOf('/') + 1);
                    s3Client.deleteObject(builder -> builder.bucket(BUCKET_NAME).key("/products/images/" + oldFileName));
                }

                if (file.getSize() > MAX_FILE_SIZE) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image file size is too large. Maximum allowed size is 5MB");
                }

                String fileName = "/products/images/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

                s3Client.putObject(PutObjectRequest
                                .builder()
                                .bucket(BUCKET_NAME)
                                .key(fileName)
                                .build(),
                        software.amazon.awssdk.core.sync.RequestBody.fromString("Testing java sdk"));

                productModel.setImage_url("https://" + BUCKET_NAME + ".s3.amazonaws.com" + fileName);
            }

            return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productModelOptional = productService.findById(id);
        if (!productModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
        ProductModel productModel = productModelOptional.get();

        String imageUrl = productModel.getImage_url();

        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(imageUrl)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
        productService.delete(productModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted succesfully!");
    }
}


