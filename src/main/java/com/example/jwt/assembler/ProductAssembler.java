package com.example.jwt.assembler;

import com.example.jwt.controller.ProductApi;
import com.example.jwt.entity.Product;
import com.example.jwt.model.ProductModel;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    @Override
    public EntityModel<Product> toModel(Product entity) {
        EntityModel<Product> productModel = EntityModel.of(entity);

        productModel.add(linkTo(methodOn(ProductApi.class).getOne(entity.getId())).withSelfRel());
        productModel.add(linkTo(methodOn(ProductApi.class).listAll()).withRel(IanaLinkRelations.COLLECTION));

        return productModel;
    }

//    @Override
//    public ProductModel toModel(Product entity) {
//        ProductModel model = new ProductModel();
//
//        BeanUtils.copyProperties(entity, model);
//        return model;
//    }
}
