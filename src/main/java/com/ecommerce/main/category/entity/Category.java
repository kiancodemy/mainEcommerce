package com.ecommerce.main.category.entity;

import com.ecommerce.main.book.entity.Book;
import com.ecommerce.main.subcategory.entity.SubCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Builder
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long  id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Builder.Default
    private boolean status = true;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private Set<SubCategory> subCategories = new HashSet<>();

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private Set<Book> book = new HashSet<>();


}
