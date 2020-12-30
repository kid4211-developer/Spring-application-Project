package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
	@GetMapping("hello")  //"hello"라는 url을 통해 해당 controller가 호출됨
	public String Hello(Model model) {
		model.addAttribute("data", "Hello~!"); 
		return "hello"; // Spring 내부적 셋팅으로 xxx.html로 화면이 호출이 됨
	}
}
