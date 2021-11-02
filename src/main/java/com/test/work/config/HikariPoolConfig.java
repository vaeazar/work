package com.test.work.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class HikariPoolConfig extends UnpooledDataSourceFactory {
  public HikariPoolConfig() {
    this.dataSource = new HikariDataSource();
  }
}
