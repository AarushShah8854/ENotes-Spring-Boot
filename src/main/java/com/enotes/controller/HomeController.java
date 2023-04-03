package com.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.enotes.entity.UserDtls;
import com.enotes.repository.UserRepository;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	@PostMapping("/saveuser")
	public String saveUser(@ModelAttribute UserDtls user, Model model, 
			RedirectAttributes attributes) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		UserDtls u = userRepository.save(user);
				
		if(u != null) {
			attributes.addFlashAttribute("msg", "Registration Successful");
		} else {
			attributes.addFlashAttribute("msg", "Something wrong on Server");
		}
		return "redirect:/signup";
		
	}

}
