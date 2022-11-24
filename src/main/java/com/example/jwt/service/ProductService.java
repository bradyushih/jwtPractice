package com.example.jwt.service;

import com.example.jwt.entity.Product;
import com.example.jwt.jpa.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository prodRepo;

    public Page<Product> fetchProductDataAsPageWithFilteringAndSorting(String nameFilter, int page, int size, List<String> sortList, String sortOrder){
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(createSortOrder(sortList, sortOrder.toUpperCase())));
        return prodRepo.findByNameLike(nameFilter, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }
}
