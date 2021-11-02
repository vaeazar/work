package com.test.work.domain;


import lombok.Data;

/**
 * 상품 정보
 */
@Data
public class Product {

  public Product() {
  }

  public Product(int productId, String productCategory, String productCategoryChild) {
    this.productId = productId;
    this.productCategory = productCategory;
    this.productCategoryChild = productCategoryChild;
  }

  public Product(int productId, String productCategory, String productCategoryChild, String productName, String productStatus) {
    this.productId = productId;
    this.productCategory = productCategory;
    this.productCategoryChild = productCategoryChild;
    this.productName = productName;
    this.productStatus = productStatus;
  }

  int productId;
  String productCategory;
  String productCategoryChild;
  String productName;
  String productStatus;

  public boolean invalidValue() {
    boolean check = false;
    check = productCategory.isBlank()
        || productCategoryChild.isBlank()
        || productName.isBlank()
        || productStatus.isBlank();
    return check;
  }

  public boolean invalidId() {
    boolean check = false;
    check = productId == 0;
    return check;
  }
}
