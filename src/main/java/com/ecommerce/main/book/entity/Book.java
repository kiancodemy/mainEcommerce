package com.ecommerce.main.book.entity;


import com.ecommerce.main.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

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


    @Column(length = 4000)
    private String description;

    @Column(length = 200)
    private String publisher;

    @Column(length = 200)
    private String translator;

    @Column(nullable = false, length = 10)
    private String language;

    private Integer publicationYear;

    private Integer pages;

    @Column(nullable = false, precision = 12)
    private BigDecimal price;

    @Column(nullable = false, precision = 12)
    private BigDecimal costPrice;

    @Column(nullable = false)
    private int stock;

    @Column(length = 500)
    private String coverImageUrl;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)

    private Category category;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

}
