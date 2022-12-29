package com.davmt.motivatr.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

  @GetMapping("/error")
  public String handleError(Model model, HttpServletRequest request) {
    String errorMessage = "Error!";

    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());

      if (statusCode == HttpStatus.NOT_FOUND.value()) {
        // handle HTTP 404 Not Found error
        errorMessage = "Http Error Code: 404. Resource not found!";
      } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
        // handle HTTP 403 Forbidden error
        errorMessage = "Http Error Code: 403. Forbidden!";
      } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        // handle HTTP 500 Internal Server error
        errorMessage = "Http Error Code: 500. Server Error!";
      }
    }
    model.addAttribute("errorMsg", errorMessage);

    return "error";
  }
}
