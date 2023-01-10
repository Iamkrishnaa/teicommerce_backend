package com.teispace.teicommerce_backend.serviceImplementations;

import com.teispace.teicommerce_backend.dtos.PaginationResponseDto;
import com.teispace.teicommerce_backend.dtos.ProductDto;
import com.teispace.teicommerce_backend.exceptions.ResourceNotAvailableException;
import com.teispace.teicommerce_backend.models.Category;
import com.teispace.teicommerce_backend.models.Product;
import com.teispace.teicommerce_backend.repos.CategoryRepository;
import com.teispace.teicommerce_backend.repos.ProductRepository;
import com.teispace.teicommerce_backend.repos.RatingRepository;
import com.teispace.teicommerce_backend.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final RatingRepository ratingRepository;

    private final CategoryRepository categoryRepository;

    public ProductServiceImplementation(
            ProductRepository productRepository,
            ModelMapper modelMapper, RatingRepository ratingRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.ratingRepository = ratingRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public PaginationResponseDto getAvailableProducts(
            int pageNumber,
            int pageSize,
            String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Product> productPage = productRepository.findAllByQuantityGreaterThan(0, pageable);
        return getPaginationResponseDto(productPage);
    }


    @Override
    public PaginationResponseDto getProducts(
            int pageNumber,
            int pageSize,
            String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Product> productPage = productRepository.findAll(pageable);
        return getPaginationResponseDto(productPage);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotAvailableException("Product not available")
        );
        Integer totalRatings = ratingRepository.findTotalRatingByProductId(product.getId());
        Double avgRating = ratingRepository.findAverageRatingByProductId(product.getId());
        product.setTotalRatings(totalRatings != null ? totalRatings : 0);
        product.setAverageRating(avgRating != null ? avgRating : 0.0);
        return modelMapper.map(product, ProductDto.class);
    }


    @Override
    public PaginationResponseDto getTrendingProduct(
            int pageNumber,
            int pageSize,
            String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Product> productPage = productRepository.findTrendingProducts(pageable);
        return getPaginationResponseDto(productPage);
    }

    @Override
    public PaginationResponseDto getProductByCategory(
            int pageNumber,
            int pageSize,
            String sortBy,
            String category
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Category cat = categoryRepository.findCategoryByTitleOrSlug(category, category).orElse(null);
        Page<Product> productPage = productRepository.findAllByCategory(cat, pageable);
        return getPaginationResponseDto(productPage);
    }


    private PaginationResponseDto getPaginationResponseDto(Page<Product> productPage) {
        List<Product> products = productPage.getContent();

        List<ProductDto> productDtos = products.stream()
                .map(product -> {
                    Integer totalRatings = ratingRepository.findTotalRatingByProductId(product.getId());
                    Double avgRating = ratingRepository.findAverageRatingByProductId(product.getId());
                    product.setTotalRatings(totalRatings != null ? totalRatings : 0);
                    product.setAverageRating(avgRating != null ? avgRating : 0.0);
                    return modelMapper.map(product, ProductDto.class);
                })
                .toList();

        PaginationResponseDto paginationResponseDto = new PaginationResponseDto();

        paginationResponseDto.setCurrentPage(productPage.getNumber());
        paginationResponseDto.setCurrentPageSize(productPage.getSize());
        paginationResponseDto.setTotalPages(productPage.getTotalPages());
        paginationResponseDto.setTotalCount(productPage.getTotalElements());
        paginationResponseDto.setData(productDtos);

        return paginationResponseDto;
    }

}
