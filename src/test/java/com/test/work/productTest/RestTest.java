package com.test.work.productTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.work.controller.RestController;
import com.test.work.domain.Product;
import com.test.work.repository.ProductRepository;
import com.test.work.service.ProductService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matchers;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestTest {

  Logger log = (Logger) LoggerFactory.getLogger(RestTest.class);

  @Autowired
  RestController controller;

  MockMvc mvc;

  @Autowired
  ApplicationContext context;

  int savedPid = 0;

  @BeforeAll
  void setup() throws Exception {
    mvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  private MvcResult result;

  @Test
  @Order(1)
  @DisplayName("insert API working test")
  void TestInsert() {
    try {
      String jsonString = "{\"productCategory\":\"testData\",\n" +
          "\"productCategoryChild\":\"testDataChild\",\n" +
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
  @Order(2)
  @DisplayName("select API working test")
  public void testSelect() throws Exception{
    List<Product> productList = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    String data = "";

    result = mvc.perform(MockMvcRequestBuilders.get("/product-list/testData/testDataChild"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    data = result.getResponse().getContentAsString();
    JSONObject json = JSONObject.fromObject(data);
    List<Product> getProductList =
        mapper.readValue(json.get("data").toString(), new TypeReference<List<Product>>(){});
    assertThat(getProductList.size() == 0).isFalse();
    savedPid = getProductList.get(0).getProductId();
    Product product = new Product(savedPid,"testData","testDataChild","mushinsa","Y");

    result = mvc.perform(MockMvcRequestBuilders.get("/product-info/" + savedPid))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    data = result.getResponse().getContentAsString();
    json = JSONObject.fromObject(data.trim());
    Product getProduct = mapper.readValue(json.get("data").toString(), Product.class);


    assertThat(getProduct).isEqualTo(product);
  }

  @Test
  @Order(3)
  @DisplayName("update API working test")
  void TestUpdate() {
    try {
      String jsonString = "{\"productId\":"+savedPid+",\n" +
          "\"productCategory\":\"changedCategory\",\n" +
          "\"productCategoryChild\":\"changedCategoryChild\",\n" +
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
  @Order(4)
  @DisplayName("delete API working test")
  public void deleteItem() throws Exception{
    mvc.perform(MockMvcRequestBuilders.delete("/product-unregister/" + savedPid))
        .andExpect(MockMvcResultMatchers.status().isAccepted());
  }

}
