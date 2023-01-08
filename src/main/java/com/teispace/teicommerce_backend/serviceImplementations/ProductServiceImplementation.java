package com.teispace.teicommerce_backend.serviceImplementations;

import com.teispace.teicommerce_backend.dtos.PaginationResponseDto;
import com.teispace.teicommerce_backend.dtos.ProductDto;
import com.teispace.teicommerce_backend.models.Product;
import com.teispace.teicommerce_backend.repos.ProductRepository;
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

    public ProductServiceImplementation(
            ProductRepository productRepository,
            ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<ProductDto> getAvailableProducts(
            int pageNumber,
            int pageSize,
            String sortBy
    ) {
        return null;
    }

    @Override
    public PaginationResponseDto getProducts(
            int pageNumber,
            int pageSize,
            String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Product> productPage = productRepository.findAll(pageable);
        List<Product> products = productPage.getContent();

        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();

        PaginationResponseDto paginationResponseDto = new PaginationResponseDto();

        paginationResponseDto.setCurrentPage(productPage.getNumber());
        paginationResponseDto.setCurrentPageSize(productPage.getSize());
        paginationResponseDto.setTotalPages(productPage.getTotalPages());
        paginationResponseDto.setTotalCount(productPage.getTotalElements());
        paginationResponseDto.setData(productDtos);

        return paginationResponseDto;
    }


    @Override
    public List<ProductDto> getTrendingProduct(
            int pageNumber,
            int pageSize,
            String sortBy
    ) {
        return null;
    }

    @Override
    public List<ProductDto> getProductByCategory(
            int pageNumber,
            int pageSize,
            String sortBy,
            String category
    ) {
        return null;
    }
}
