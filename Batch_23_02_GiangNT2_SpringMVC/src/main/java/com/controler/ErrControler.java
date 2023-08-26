package com.controler;
/** 
 * ErrControler
 * 
 * Version 0.0.1-SNAPSHOT
 * 
 * Date: 28-4-2023
 * 
 * Copyright 
 * 
 * Modification Logs:
 * 
 * DATE                 AUTHOR          DESCRIPTION
 * -----------------------------------------------------------------------
 * 09-06-2023              GiangNT2            Create
 *  
 * */
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrControler implements ErrorController {

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Integer statusCode = null;
		String contentCtatusCode = null;
		if (status != null) {
			statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				contentCtatusCode = "we are sorry, but the page you requested was not found";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				contentCtatusCode = "we are sorry internal server error";
			} else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				contentCtatusCode = "we are sorry bad Request";
			} else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
				contentCtatusCode = "we are sorry method not allowed";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				contentCtatusCode = "we are sorry access is denied";
			}
		}
		model.addAttribute("errCode", statusCode);
		model.addAttribute("feedbackError", contentCtatusCode);
		return "errors/error";
	}
}