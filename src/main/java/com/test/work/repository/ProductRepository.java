package com.test.work.repository;

import com.test.work.config.MybatisFactory;
import com.test.work.constants.Schema;
import com.test.work.domain.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ProductRepository {
  public List<Product> selectProductList(Product keys) {
    SqlSession productMaria = MybatisFactory.getSession(Schema.master);
    try {
      return productMaria.selectList("work.selectProductList", keys);
    } catch (Exception e) {
      log.error("마리아 상품 조회 에러 :: {}", e.toString());
      return new ArrayList<>();
    }finally {
      productMaria.close();
    }
  }

  public Product selectProductInfo(int productId) {
    SqlSession productMaria = MybatisFactory.getSession(Schema.master);
    try {
      return productMaria.selectOne("work.selectProductInfo", productId);
    } catch (Exception e) {
      log.error("마리아 상품 조회 에러 :: {}", e.toString());
      return new Product();
    }finally {
      productMaria.close();
    }
  }

  public int insertProduct(Product keys) {
    SqlSession productMaria = MybatisFactory.getSession(Schema.master);
    try {
      return productMaria.delete("work.insertProduct", keys);
    } catch (Exception e) {
      log.error("마리아 삽입 에러 :: {}", e.toString());
      return -1;
    }finally {
      productMaria.close();
    }
  }

  public int updateProduct(Product keys) {
    SqlSession productMaria = MybatisFactory.getSession(Schema.master);
    try {
      return productMaria.delete("work.updateProduct", keys);
    } catch (Exception e) {
      log.error("마리아 수정 에러 :: {}", e.toString());
      return -1;
    }finally {
      productMaria.close();
    }
  }

  public int deleteProduct(Map<String, Object> keys) {
    SqlSession productMaria = MybatisFactory.getSession(Schema.master);
    try {
      return productMaria.delete("work.deleteProduct", keys);
    } catch (Exception e) {
      log.error("마리아 삭제 에러 :: {}", e.toString());
      return -1;
    }finally {
      productMaria.close();
    }
  }
}
