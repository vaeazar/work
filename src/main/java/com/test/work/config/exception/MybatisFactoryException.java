package com.test.work.config.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MybatisFactoryException extends RuntimeException {

  public MybatisFactoryException(String message) {
    super(message);
  }
}
