package com.septech.centauri.data.model.category.mapper;

import com.septech.centauri.data.model.category.CategoryEntity;
import com.septech.centauri.domain.models.Category;

public class CategoryDataMapper {
    public static Category transform(CategoryEntity categoryEntity) {
        Category category = new Category();

        category.setCategory1(categoryEntity.getCategory1());
        category.setCategory2(categoryEntity.getCategory2());
        category.setCategory3(categoryEntity.getCategory3());
        category.setCategory4(categoryEntity.getCategory4());
        category.setCategory5(categoryEntity.getCategory5());

        return category;
    }

    public static CategoryEntity transform(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setCategory1(category.getCategory1());
        categoryEntity.setCategory2(category.getCategory2());
        categoryEntity.setCategory3(category.getCategory3());
        categoryEntity.setCategory4(category.getCategory4());
        categoryEntity.setCategory5(category.getCategory5());
        return categoryEntity;
    }
}
