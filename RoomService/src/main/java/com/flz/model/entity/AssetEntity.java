package com.flz.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.Expression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rs_asset")
public class AssetEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "is_default")
    private Boolean isDefault;

    public static Specification<AssetEntity> generateSpecification(String name,
                                                                   BigDecimal minPrice,
                                                                   BigDecimal maxPrice,
                                                                   Boolean isDefault) {
        return Specification.where(hasName(name))
                .and(hasPriceBetween(minPrice, maxPrice))
                .and(hasIsDefault(isDefault));
    }

    private static Specification<AssetEntity> hasName(String name) {

        if (name == null) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    private static Specification<AssetEntity> hasPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {

        if (minPrice == null && maxPrice == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> {

            Expression<BigDecimal> expression = root.get("price");

            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(expression, minPrice, maxPrice);
            }

            if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(expression, minPrice);
            }

            return criteriaBuilder.lessThanOrEqualTo(expression, maxPrice);

        };
    }

    private static Specification<AssetEntity> hasIsDefault(Boolean isDefault) {

        if (isDefault == null) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isDefault"), isDefault);
    }
}


