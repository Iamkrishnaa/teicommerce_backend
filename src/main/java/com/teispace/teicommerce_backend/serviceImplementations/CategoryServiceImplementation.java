package com.teispace.teicommerce_backend.serviceImplementations;

import com.teispace.teicommerce_backend.dtos.CategoryDto;
import com.teispace.teicommerce_backend.dtos.PaginationResponseDto;
import com.teispace.teicommerce_backend.models.Category;
import com.teispace.teicommerce_backend.repos.CategoryRepository;
import com.teispace.teicommerce_backend.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService {
    final private CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImplementation(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaginationResponseDto getCategories(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return getPaginationResponseDto(categoryPage);
    }


    private PaginationResponseDto getPaginationResponseDto(Page<Category> categoryPage) {
        List<Category> categories = categoryPage.getContent();

        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();

        PaginationResponseDto paginationResponseDto = new PaginationResponseDto();

        paginationResponseDto.setCurrentPage(categoryPage.getNumber());
        paginationResponseDto.setCurrentPageSize(categoryPage.getSize());
        paginationResponseDto.setTotalPages(categoryPage.getTotalPages());
        paginationResponseDto.setTotalCount(categoryPage.getTotalElements());

        paginationResponseDto.setData(categoryDtos);

        return paginationResponseDto;
    }
}
