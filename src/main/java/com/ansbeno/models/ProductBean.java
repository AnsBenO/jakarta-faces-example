package com.ansbeno.models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.primefaces.model.file.UploadedFile;

import com.ansbeno.entities.Category;
import com.ansbeno.entities.Product;
import com.ansbeno.services.CategoryService;
import com.ansbeno.services.ProductService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Named
@RequestScoped
public class ProductBean {

      @Inject
      private ProductService productService;

      @Inject
      private CategoryService categoryService;

      private String selectedCategoryName;

      private UploadedFile fileToSave;

      public UploadedFile getFileToSave() {
            return fileToSave;
      }

      public void setFileToSave(UploadedFile fileToSave) {
            this.fileToSave = fileToSave;
      }

      public String getSelectedCategoryName() {
            return selectedCategoryName;
      }

      public void setSelectedCategoryName(String selectedCategoryName) {
            this.selectedCategoryName = selectedCategoryName;
      }

      private List<Product> filteredProducts;
      private Product productToAdd = new Product();

      public List<Product> getAllProducts() {
            return productService.findAll();
      }

      public List<Product> getFilteredProducts() {
            return filteredProducts;
      }

      public void setFilteredProducts(List<Product> filteredProducts) {
            this.filteredProducts = filteredProducts;
      }

      public List<Category> getCategoryFilterOptions() {
            return this.categoryService.findAll();
      }

      public List<String> getCategoryStringFilterOptions() {
            return this.categoryService.findAllNames();
      }

      public Product getProductToAdd() {
            return productToAdd;
      }

      public void setProductToAdd(Product productToAdd) {
            log.info("Added product {}", productToAdd.getCategory());
            this.productToAdd = productToAdd;
      }

      public void saveProduct() {
            Category selectedCategory = categoryService.findByName(selectedCategoryName)
                        .orElseThrow(IllegalArgumentException::new);

            if (selectedCategory != null) {
                  productToAdd.setCategory(selectedCategory);

            }
            log.info("Saving Image {}", fileToSave.getFileName());

            if (fileToSave != null && fileToSave.getSize() > 0) {
                  String imagePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("")
                              + File.separator + "image" + File.separator + "products";

                  try {
                        Files.createDirectories(Paths.get(imagePath));
                        String fileName = Paths.get(fileToSave.getFileName()).getFileName().toString();
                        String fullImagePath = imagePath + File.separator + fileName;

                        try (InputStream inputStream = fileToSave.getInputStream()) {
                              saveUploadedImage(fullImagePath, inputStream);
                              productToAdd.setImage("image/products/" + fileName);
                        }
                  } catch (IOException e) {
                        log.error("Failed to save image", e);
                  }
            }

            this.productService.save(productToAdd);
            System.out.println("Product saved: " + productToAdd);
            this.productToAdd = new Product();
      }

      private void saveUploadedImage(String imagePath, InputStream inputStream) throws IOException {
            try (inputStream;
                        FileOutputStream outputStream = new FileOutputStream(imagePath)) {

                  byte[] buffer = new byte[1024];
                  int bytesRead;
                  while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                  }

                  log.debug("Successfully saved image file at: {}", imagePath);
            } catch (IOException e) {
                  log.error("Failed to save image file at: {}", imagePath, e);
                  throw e;
            }
      }

}
