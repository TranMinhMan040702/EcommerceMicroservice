package com.criscode.product.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.criscode.clients.product.dto.ProductDto;
import com.criscode.exceptionutils.AlreadyExistsException;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.product.converter.ProductConverter;
import com.criscode.product.dto.ProductPaging;
import com.criscode.product.entity.Category;
import com.criscode.product.entity.ImageProduct;
import com.criscode.product.entity.Product;
import com.criscode.product.repository.CategoryRepository;
import com.criscode.product.repository.ImageProductRepository;
import com.criscode.product.repository.ProductRepository;
import com.criscode.product.specification.ProductSpecification;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageProductRepository imageProductRepository;
    private final CategoryRepository categoryRepository;
    private final Cloudinary cloudinary;
    private final ProductConverter productConverter;


    /**
     * @param productDto
     * @param files
     * @return
     */
    public ProductDto save(ProductDto productDto, MultipartFile[] files) {
        Optional<Product> pName = productRepository.findByName(productDto.getName());
        if (pName.isPresent()) {
            throw new AlreadyExistsException("Product existed with name: " + productDto.getName());
        }

        List<ImageProduct> images = new ArrayList<>();
        Product product = new Product();
        if (productDto.getId() == null) {
            BeanUtils.copyProperties(productDto, product);
            Product finalProduct = product;
            Arrays.stream(files).forEach(file -> images.add(saveCloudinary(file, finalProduct)));
        } else {
            product = productRepository.findById(productDto.getId())
                    .orElseThrow(() -> new NotFoundException(
                            "Product not existed with id: " + productDto.getId())
                    );
            BeanUtils.copyProperties(productDto, product, "createdAt", "createdBy");
            for (int i = 0; i < files.length; i++) {
                ImageProduct imageOld = product.getImages().get(i);
                MultipartFile imageNew = files[i];
                if (!imageOld.getPath().equals(imageNew.getOriginalFilename())) {
                    images.add(saveCloudinary(imageNew, product));
                } else {
                    images.add(imageOld);
                }
            }
            product.getImages().clear();
            imageProductRepository.deleteByProductId(productDto.getId());
        }
        Category category = categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new NotFoundException(
                        "Category not exist with id: " + productDto.getCategory())
                );
        product.setCategory(category);
        product.setImages(images);
        return productConverter.map(productRepository.save(product));
    }

    /**
     * @param file
     * @param product
     * @return
     */
    private ImageProduct saveCloudinary(MultipartFile file, Product product) {
        ImageProduct image_Product = new ImageProduct();

        try {
            Map<?, ?> r = this.cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            String img = (String) r.get("secure_url");
            image_Product.setProduct(product);
            image_Product.setPath(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image_Product;
    }

    /**
     * @param categoryId
     * @param page
     * @param limit
     * @param sortBy
     * @param priceMin
     * @param priceMax
     * @param search
     * @return
     */
    public ProductPaging findAll(Long categoryId, Integer page, Integer limit, String sortBy,
                                 Double priceMin, Double priceMax, String search) {

        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(sortBy).descending());

        Specification<Product> specification =
                ProductSpecification.getSpecification(categoryId, search, priceMin, priceMax);

        Page<Product> products = productRepository.findAll(specification, pageRequest);

        return productConverter.map(products);
    }

    /**
     * @param id
     * @return
     */
    public ProductDto findById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not found product with id: " + id));
        return productConverter.map(product);
    }

    /**
     * @param categoryId
     * @param search
     * @param priceMin
     * @param priceMax
     * @return
     */
    public List<ProductDto> findByCategory(
            Long categoryId, String search, Double priceMin, Double priceMax) {

        Specification<Product> specification =
                ProductSpecification.getSpecification(categoryId, search, priceMin, priceMax);

        List<Product> products = productRepository.findAll(specification);
        return productConverter.map(products);
    }


    /**
     * @param id
     * @return
     * @throws Exception
     */
    public Map<String, String> delete(Integer id) {
        Map<String, String> resp = new HashMap<>();
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not found product with id: " + id));
        productRepository.delete(product);
        resp.put("deleted", "Success");
        return resp;
    }

}
