package com.ansbeno.converters;

import java.util.Optional;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

import com.ansbeno.entities.Category;
import com.ansbeno.services.CategoryService;

import lombok.extern.slf4j.Slf4j;

@FacesConverter(value = "categoryConverter")
@Slf4j
public class CategoryConverter implements Converter<Category> {

      // @ManagedProperty(value = "#{categoryService}")
      private CategoryService categoryService = new CategoryService();

      // public void setCategoryService(CategoryService categoryService) {
      // this.categoryService = categoryService;
      // }

      // public CategoryService getCategoryService() {
      // return this.categoryService;
      // }

      @Override
      public Category getAsObject(FacesContext context, UIComponent component, String value) {
            log.info("Converting String to Category: {}", value);

            if (value == null || value.trim().isEmpty()) {
                  throw new IllegalArgumentException("Value is NULL");
            }

            try {
                  Optional<Category> categoryOpt = categoryService.findById(Long.parseLong(value));
                  if (categoryOpt.isPresent()) {
                        Category category = categoryOpt.get();
                        log.info("Category found: {}", category);
                        return category;
                  } else {
                        log.info("Category not found: {}", value);
                        return null;
                  }
            } catch (Exception e) {
                  log.error("Conversion error: unable to convert {} to Category", value, e);
                  return null;
            }
      }

      @Override
      public String getAsString(FacesContext context, UIComponent component, Category value) {
            this.categoryService.findAllNames().stream().forEach(c -> log.debug("Found {}", c));
            if (!(value instanceof Category)) {
                  return "";
            }

            Category category = value;
            log.info("Converting Category to String: {}", category.getName());

            return category.getName();
      }
}