package com.ecommerce.main.book.entity;


import com.ecommerce.main.book.enums.Languages;
import com.ecommerce.main.category.entity.Category;
import com.ecommerce.main.subcategory.entity.SubCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@DynamicUpdate
@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Builder.Default
    private boolean status = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Languages language;

    @Column(nullable = false)
    private Integer pages;

    @Column(nullable = false, precision = 12)
    private BigDecimal price;

    @Column(nullable = false, precision = 12)
    private BigDecimal costPrice;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private Integer publicationYear;


    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 200)
    private String publisher;

    @Column(length = 40)
    private String translator;


    @Column(length = 500)
    private String coverImageUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subCategory_id")
    private SubCategory subCategory;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

}
