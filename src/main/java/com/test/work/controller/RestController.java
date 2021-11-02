package com.test.work.controller;

import com.test.work.domain.Product;
import com.test.work.repository.ProductRepository;
import com.test.work.service.ProductService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class RestController {

  private ProductService productService;

  public RestController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping(value = {"/product-list/{productCategory}/{productCategoryChild}/{productId}"
      , "/product-list/{productCategory}/{productCategoryChild}"
      , "/product-list/{productCategory}"
      , "/product-list"
  })
  @ResponseBody
  public ResponseEntity productList(
      @PathVariable Optional<String> productCategory
      , @PathVariable Optional<String> productCategoryChild
      , @PathVariable Optional<Integer> productId) {
    Map<String, Object> responseData = new HashMap<>();
    List<Product> getProductList = new ArrayList<>();
    Product selectKeys = new Product(
        productId.orElse(0)
        , productCategory.orElse("")
        , productCategoryChild.orElse(""));

    getProductList = productService.productList(selectKeys);
    responseData.put("data",getProductList);

    return new ResponseEntity(responseData, HttpStatus.OK);
  }

  @GetMapping(value = {"/product-info/{productId}", "/product-info"})
  @ResponseBody
  public ResponseEntity productInfo(
      @PathVariable Optional<Integer> productId) {
    Map<String, Object> responseData = new HashMap<>();
    Product productInfo;

    if (productId.isEmpty()) {
      responseData.put("message", "상품 ID 값이 없습니다.");
      return new ResponseEntity(responseData, HttpStatus.BAD_REQUEST);
    } else {
      productInfo = productService.productInfo(productId.orElse(0));
    }
    responseData.put("data", productInfo);

    return new ResponseEntity(responseData, HttpStatus.OK);
  }

  @PostMapping(value = "/product-register", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity productRegister(@RequestBody Product product) {
    Map<String, Object> responseData = new HashMap<>();
    int flag = 0;

    if (product.invalidValue()) {
      responseData.put("message", "상품 카테고리와 상품명은 필수입니다.");
      return new ResponseEntity(responseData, HttpStatus.BAD_REQUEST);

    } else {
      flag = productService.productRegister(product);
    }
    if (flag == -1) {
      responseData.put("message", "상품 등록에 실패했습니다.");
      return new ResponseEntity(responseData, HttpStatus.BAD_REQUEST);
    } else if (flag == 0) {
      responseData.put("message", "상품 등록에 실패했습니다.");
      return new ResponseEntity(responseData, HttpStatus.BAD_REQUEST);
    } else {
      responseData.put("message", "상품이 성공적으로 등록되었습니다.");
    }

    return new ResponseEntity(responseData, HttpStatus.CREATED);
  }

  @PutMapping(value = "/product-changer", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity productChanger(@RequestBody Product product) {
    Map<String, Object> responseData = new HashMap<>();
    int flag = 0;

    if (product.invalidValue() || product.invalidId()) {
      responseData.put("message", "상품 카테고리와 상품명은 필수입니다.");
      return new ResponseEntity(responseData, HttpStatus.BAD_REQUEST);
    } else {
      flag = productService.productChanger(product);
    }
    if (flag == -1) {
      responseData.put("message", "상품 수정에 실패했습니다.");
      return new ResponseEntity(responseData, HttpStatus.BAD_REQUEST);
    } else if (flag == 0) {
      responseData.put("message", "해당 상품 ID가 없습니다.");
      return new ResponseEntity(responseData, HttpStatus.BAD_REQUEST);
    } else {
      responseData.put("message", "상품이 성공적으로 수정되었습니다.");
    }

    return new ResponseEntity(responseData, HttpStatus.OK);
  }

  @DeleteMapping(value = {"/product-unregister/{productId}"
      , "/product-unregister"})
  @ResponseBody
  public ResponseEntity productUnregister(
      @PathVariable Optional<Integer> productId) {
    Map<String, Object> responseData = new HashMap<>();
    Map<String, Object> deleteKeys = new HashMap<>();
    deleteKeys.put("productId", productId.orElse(0));
    int flag = 0;
    if (!productId.isPresent()) {
      responseData.put("message", "상품 ID 값이 없습니다.");
      return new ResponseEntity(responseData, HttpStatus.BAD_REQUEST);
    } else {
      flag = productService.productUnregister(deleteKeys);
    }
    if (flag == -1) {
      responseData.put("message", "상품 삭제에 실패했습니다.");
      return new ResponseEntity(responseData, HttpStatus.BAD_REQUEST);
    } else if (flag == 0) {
      responseData.put("message", "해당 상품 ID가 없습니다.");
      return new ResponseEntity(responseData, HttpStatus.BAD_REQUEST);
    } else {
      responseData.put("message", "상품이 성공적으로 삭제되었습니다.");
    }
    return new ResponseEntity(responseData, HttpStatus.ACCEPTED);
  }
}