package com.ecommerce.main.category.dto;

import lombok.Builder;

public record CategoryResponse(Long  id,
                               String name,
                               boolean status) { }
