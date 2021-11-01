package com.test.work.service;

import com.test.work.domain.Product;
import com.test.work.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  final
  ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> productList(Product selectKeys) {
    List<Product> productList = new ArrayList<>();
    productList = productRepository.selectProductList(selectKeys);

    return productList;
  }

  public Product productInfo(int selectKeys) {
    return productRepository.selectProductInfo(selectKeys);
  }

  public int productRegister(Product insertKeys) {
    return productRepository.insertProduct(insertKeys);
  }

  public int productChanger(Product updateKeys) {
    return productRepository.updateProduct(updateKeys);
  }

  public int productUnregister(Map<String, Object> deleteKeys) {
    return productRepository.deleteProduct(deleteKeys);
  }
}
