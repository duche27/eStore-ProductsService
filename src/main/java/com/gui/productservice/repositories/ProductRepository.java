package com.gui.productservice.repositories;

import com.gui.productservice.model.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends CrudRepository<ProductEntity, String> {

    ProductEntity findByProductId(String productId);
    ProductEntity findByProductIdOrTitle(String productId, String title);
}
