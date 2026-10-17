package com.ecommerce.main.category.dto;

import lombok.Builder;

public record CategoryResponse(Integer id,
                               String name,
                               boolean status) { }
