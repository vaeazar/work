package com.test.work.config;

import com.test.work.config.exception.MybatisFactoryException;
import com.test.work.constants.Schema;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

@Slf4j
public final class MybatisFactory {

  private static Map<String, SqlSessionFactory> sessionMap = new HashMap<>();
  private static String defaultConnName = "default";
  private static String propertiesPath = "sqlmap/mybatis-config.xml";

  private MybatisFactory() {
    throw new IllegalStateException("use only static method.");
  }

  /**
   * default로 등록한 세션을 리턴 함. sqlmap/mybatis-config.xml 에 설정 정보를 리턴. default는 {@code auto commit}
   * 상태이다..
   *
   * @return 디폴트 세션을 반환
   * @throws MybatisFactoryException 디폴트 설정을 안하고 호출한 경우
   */
  public static SqlSession getSession() {
    return getSessionFactory().openSession(true);
  }

  /**
   * 커스텀 세션을 리턴 함. sqlmap/mybatis-스키마-config.xml 에 설정 정보를 리턴.
   *
   * @return 커스텀 세션을 반환
   * @throws MybatisFactoryException 커스텀 설정을 안하고 호출한 경우
   */
  public static SqlSession getSession(Schema schema) {
    return getSessionFactory(schema).openSession(true);
  }

  /**
   * default로 등록한 세션 팩토리를 리턴 함. sqlmap/mybatis-config.xml 에 설정 정보를 리턴
   *
   * @return 디폴트 세션을 반환
   * @throws MybatisFactoryException 디폴트 설정을 안하고 호출한 경우
   */
  public static SqlSessionFactory getSessionFactory() {
    if (sessionMap.get(defaultConnName) == null) {
      regSqlSession(defaultConnName, propertiesPath);
      if (sessionMap.get(defaultConnName) == null) {
        throw new MybatisFactoryException(
            "there is no default session! if you want use default session, "
                + "make sure that the config file is placed `sqlmap/mybatis-config.xml` ");
      }
    }
    return sessionMap.get(defaultConnName);
  }

  /**
   * 커스텀 세션 팩토리를 리턴 함. sqlmap/mybatis-스키마-config.xml 에 설정 정보를 리턴
   *
   * @return 커스텀 세션을 반환
   * @throws MybatisFactoryException 디폴트 설정을 안하고 호출한 경우
   */
  public static SqlSessionFactory getSessionFactory(Schema schema) {
    if (sessionMap.get(String.valueOf(schema)) == null) {
      String configPath = String.format("sqlmap/mybatis-%s-config.xml", schema);
      regSqlSession(String.valueOf(schema), configPath);
      if (sessionMap.get(String.valueOf(schema)) == null) {
        throw new MybatisFactoryException(
            String.format(
                "there is no custom session! if you want use custom session, "
                    + "make sure that the config file is placed `sqlmap/mybatis-%s-config.xml` ",
                schema));
      }
    }
    return sessionMap.get(String.valueOf(schema));
  }

  /**
   * 세션을 등록함.
   *
   * @param name 해당 세션 네임
   * @param path config 경로 (ex) sqlmap/custom-config.xml);
   */
  public static void regSqlSession(String name, String path) {
    try {
      InputStream inputStream = Resources.getResourceAsStream(path);
      SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
      sessionMap.put(name, sessionFactory);
    } catch (IOException e) {
      log.error(String.format("%s", e));
    }
  }

  /**
   * 등록된 세션을 반환한다.
   *
   * @param name 세션이름
   * @return 등록된 세션을 반환
   * @throws MybatisFactoryException 해당 이름으로된 세션이 없을 경우
   */
  public static SqlSession getSession(String name) {
    if (sessionMap.get(name) == null) {
      throw new MybatisFactoryException("there is no " + name + " session ");
    }
    return sessionMap.get(name).openSession(true);
  }

  /**
   * SQL Enviroment 세션을 등록함.
   *
   * @param name 해당 세션 네임
   * @param env SQL Enviroment
   */
  public static void regSqlEnvSession(String name, String env) {
    try {
      InputStream inputStream = Resources.getResourceAsStream(propertiesPath);
      SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream, env);
      sessionMap.put(name, sessionFactory);
    } catch (IOException e) {
      log.error(String.format("%s", e));
    }
  }
}
