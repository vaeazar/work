package com.test.work.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

  @RequestMapping(value = {"/","index"})
  public ModelAndView index() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("index");
    return mv;
  }

  @RequestMapping(value = {"productList"})
  public ModelAndView productList() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("productList");
    return mv;
  }

  @RequestMapping(value = {"productRegister"})
  public ModelAndView productAdd() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("productRegister");
    return mv;
  }

  @RequestMapping(value = {"productChanger"})
  public ModelAndView productChanger() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("productChanger");
    return mv;
  }

  @RequestMapping(value = {"productUnregister"})
  public ModelAndView productUnregister() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("productUnregister");
    return mv;
  }
}
