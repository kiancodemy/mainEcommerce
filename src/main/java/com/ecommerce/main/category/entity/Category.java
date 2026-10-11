package com.ecommerce.main.category.entity;

import com.ecommerce.main.book.entity.Book;
import com.ecommerce.main.subcategory.entity.SubCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private Set<SubCategory> subCategories = new HashSet<>();

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private Set<Book> book = new HashSet<>();

}
