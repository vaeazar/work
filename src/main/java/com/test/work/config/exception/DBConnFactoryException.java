package com.test.work.config.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBConnFactoryException extends RuntimeException {

  public DBConnFactoryException(String message) {
    super(message);
  }
}
