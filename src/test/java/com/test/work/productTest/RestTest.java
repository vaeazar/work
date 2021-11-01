package com.test.work.productTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.test.work.controller.RestController;
import com.test.work.domain.Product;
import com.test.work.repository.ProductRepository;
import com.test.work.service.ProductService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class RestTest {

  @InjectMocks
  RestController controller;

  MockMvc mvc;

  @Mock
  ProductRepository productRepository;

  @BeforeAll
  void setup() throws Exception {
    mvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  private MvcResult result;

  @Test
  public void testProductInfo() throws Exception{
    Product product = new Product(1,"shirts","T","saranghae","N");
    Mockito.when(productRepository.selectProductInfo(1)).thenReturn(product);
    mvc.perform(MockMvcRequestBuilders.get("/product-info/1"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    //Mockito.verify(productRepository).selectProductInfo(product);
  }

  @Test
  @DisplayName("insert API working test")
  void TestInsert() {
    try {
      String jsonString = "{\"productCategory\":\"pants\",\n" +
          "\"productCategoryChild\":\"jean\",\n" +
          "\"productName\":\"mushinsa\",\n" +
          "\"productStatus\":\"Y\"}";

      mvc.perform(MockMvcRequestBuilders.post("/product-register")
          .contentType(MediaType.APPLICATION_JSON)
          .content(jsonString))
          .andExpect(MockMvcResultMatchers.status().isCreated());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("update API working test")
  void TestUpdate() {
    try {
      String jsonString = "{\"productId\":1,\n" +
          "\"productCategory\":\"shirts\",\n" +
          "\"productCategoryChild\":\"T\",\n" +
          "\"productName\":\"saranghae\",\n" +
          "\"productStatus\":\"N\"}";

      mvc.perform(MockMvcRequestBuilders.put("/product-changer")
          .contentType(MediaType.APPLICATION_JSON)
          .content(jsonString))
          .andExpect(MockMvcResultMatchers.status().isOk());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("delete API working test")
  public void deleteItem() throws Exception{
    mvc.perform(MockMvcRequestBuilders.delete("/product-unregister/1"))
        .andExpect(MockMvcResultMatchers.status().isAccepted());
  }

}
