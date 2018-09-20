package kr.co.kpcard.backoffice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	@RequestMapping(value="/")
	public String Home(){
		return "R2 BackOffice API";
	}
}
