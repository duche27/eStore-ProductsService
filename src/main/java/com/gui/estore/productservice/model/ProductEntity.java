package com.gui.estore.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products", schema = "public")
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = -4917718755987410188L;

    @Id
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
