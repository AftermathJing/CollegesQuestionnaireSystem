package com.github.aftermathjing.questionnaire.utils;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CORS {

  @CrossOrigin(origins = "*") // 允许所有来源访问
  @GetMapping("/example")
  public String example() {
    // 处理请求
    return "Response";
  }
}
