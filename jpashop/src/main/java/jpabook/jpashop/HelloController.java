package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
	@GetMapping("hello")  //"hello"��� url�� ���� �ش� controller�� ȣ���
	public String Hello(Model model) {
		model.addAttribute("data", "Hello~!"); 
		return "hello"; // Spring ������ �������� xxx.html�� ȭ���� ȣ���� ��
	}
}
