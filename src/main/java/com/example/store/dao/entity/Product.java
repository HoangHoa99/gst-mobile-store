package com.example.store.dao.entity;

import com.example.store.constant.ProductCondition;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "product_name")
    private String productName;

    @NotNull
    @Column(name = "unit_price")
    private Integer unitPrice;

    @NotNull
    @Column
    private Integer quantity;

    @Column
    private String description;

    @NotNull
    @Column
    private Integer manufacturer;

    @NotNull
    @Column
    private Integer category;

    @NotNull
    @Column
    private ProductCondition condition;
}
