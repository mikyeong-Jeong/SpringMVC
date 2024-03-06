package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class TestController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/hello")
	public String HelloController(){
		
		log.info("/hello 요청 >> HelloController() 실행");
		return "hello";
	}
	@GetMapping("/jasper")
	public String JasperPageController(){
		
		return "jasper";
	}
}
