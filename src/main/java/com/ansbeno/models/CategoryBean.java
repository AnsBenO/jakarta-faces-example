package com.ansbeno.models;

import java.util.List;

import com.ansbeno.entities.Category;
import com.ansbeno.services.CategoryService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CategoryBean {

      @Inject
      private CategoryService categoryService;
      private List<Category> filteredCategories;

      public CategoryService getCategoryService() {
            return categoryService;
      }

      public void setCategoryService(CategoryService categoryService) {
            this.categoryService = categoryService;
      }

      public List<Category> getAllCategories() {
            return categoryService.findAll();
      }

      public List<Category> getFilteredCategories() {
            return filteredCategories;
      }

      public void setFilteredCategories(List<Category> filteredCategories) {
            this.filteredCategories = filteredCategories;
      }
}
