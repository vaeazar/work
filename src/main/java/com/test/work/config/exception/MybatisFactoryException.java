/*
 * COPYRIGHT (c) Enliple 2019
 * This software is the proprietary of Enliple
 *
 * @author <a href="mailto:jshan@enliple.com">jshan</a>
 * @since 2019. 4. 23
 */
package com.test.work.config.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MybatisFactoryException extends RuntimeException {

  public MybatisFactoryException(String message) {
    super(message);
  }
}
