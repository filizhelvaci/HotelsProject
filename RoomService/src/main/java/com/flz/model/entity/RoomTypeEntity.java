package com.flz.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.Expression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rs_room_type")
public class RoomTypeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "person_count")
    private Integer personCount;

    @Column(name = "size")
    private Integer size;

    @Column(name = "description")
    private String description;

    /**
     * ----------------------------------------------------------------
     * RoomTypeEntity   AssetEntity
     * M              M
     */
    @ManyToMany
    @JoinTable(
            name = "rs_room_type_asset",
            joinColumns = @JoinColumn(name = "room_type_id"),
            inverseJoinColumns = @JoinColumn(name = "asset_id")
    )
    private List<AssetEntity> assets;


    public static Specification<RoomTypeEntity> generateSpecification(String name,
                                                                      BigDecimal minPrice,
                                                                      BigDecimal maxPrice,
                                                                      Integer personCount,
                                                                      Integer size) {

        return Specification.where(hasName(name))
                .and(hasRoomTypePriceBetween(minPrice, maxPrice))
                .and(hasPersonCount(personCount))
                .and(hasSize(size));
    }

    private static Specification<RoomTypeEntity> hasName(String name) {

        if (name == null) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    private static Specification<RoomTypeEntity> hasRoomTypePriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {

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

    private static Specification<RoomTypeEntity> hasPersonCount(Integer personCount) {

        if (personCount == null) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("personCount"), personCount);
    }

    private static Specification<RoomTypeEntity> hasSize(Integer size) {

        if (size == null) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("size"), size);
    }

}
