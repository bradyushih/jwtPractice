package com.example.jwt.controller;

import com.example.jwt.assembler.ProductAssembler;
import com.example.jwt.config.ApiRestController;
import com.example.jwt.entity.Product;
import com.example.jwt.jpa.ProductRepository;
import com.example.jwt.model.ProductModel;
import com.example.jwt.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ApiRestController
@RequestMapping("/products")
//@PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_CUSTOMER')")
public class ProductApi {

    private ProductRepository prodRepo;

    private ProductAssembler prodAssembler;

    private PagedResourcesAssembler<Product> pageProdAssembler;

    private ProductService productService;

    public ProductApi(ProductRepository prodRepo, ProductAssembler prodAssembler, PagedResourcesAssembler<Product> pageProdAssembler, ProductService productService) {
        this.prodRepo = prodRepo;
        this.prodAssembler = prodAssembler;
        this.pageProdAssembler = pageProdAssembler;
        this.productService = productService;
    }

    @PostMapping
    @RolesAllowed("ROLE_EDITOR")
    public ResponseEntity<Product> create(@RequestBody @Valid Product product){
        Product savedProduct = prodRepo.save(product);
        URI productURI = URI.create("/products/" + savedProduct.getId());
        return ResponseEntity.created(productURI).body(savedProduct);
    }

    @GetMapping("/page")
    @RolesAllowed({"ROLE_CUSTOMER", "ROLE_EDITOR"})
    public PagedModel<EntityModel<Product>> list(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "") List<String> sortList,
            @RequestParam(defaultValue = "DESC") String sortOrder) {
        Page<Product> prodPage = productService.fetchProductDataAsPageWithFilteringAndSorting(name, page, size, sortList, sortOrder);
//        System.out.println(prodPage.getContent());
//        PagedModel<EntityModel<Product>> a = pageProdAssembler.toModel(prodPage);
        return pageProdAssembler.toModel(prodPage, prodAssembler);
//        return prodRepo.findAll(pageable);
    }

    @GetMapping("/{id}")
    public HttpEntity<EntityModel<Product>> getOne(@PathVariable Integer id){
        try {
            Product prod = prodRepo.findById(id).orElseThrow();
            EntityModel<Product> model = prodAssembler.toModel(prod);
//            prod.add(linkTo(methodOn(ProductApi.class).getOne(id)).withSelfRel());
//            prod.add(linkTo(methodOn(ProductApi.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
            return new ResponseEntity<>(model, HttpStatus.OK);
        } catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public CollectionModel<EntityModel<Product>> listAll(){
        List<EntityModel<Product>> listProducts = prodRepo.findAll()
                .stream().map(prodAssembler::toModel).collect(Collectors.toList());
//        for (Product product : listProducts) {
//            product.add(linkTo(methodOn(ProductApi.class).getOne(product.getId())).withSelfRel());
//        }

        CollectionModel<EntityModel<Product>> collectionModel = CollectionModel.of(listProducts);

        collectionModel.add(linkTo(methodOn(ProductApi.class).listAll()).withSelfRel());

        return collectionModel;
    }
}
