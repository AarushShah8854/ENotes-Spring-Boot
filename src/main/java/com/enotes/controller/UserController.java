package com.enotes.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.enotes.entity.Notes;
import com.enotes.entity.UserDtls;
import com.enotes.repository.NotesRepository;
import com.enotes.repository.UserRepository;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NotesRepository notesRepository;
	
	@ModelAttribute
	public void addCommnData(Principal p, Model m) {
		String email = p.getName();
		UserDtls user = userRepository.findByEmail(email);
		m.addAttribute("user", user);		
	}
	
	@GetMapping("/addnotes")
	public String home() {
		return "user/add_notes";
	}
	
	@PostMapping("/updatenotes")
	public String updatenote(@ModelAttribute Notes notes, 
			RedirectAttributes attributes, Principal p) {
		String email = p.getName();
		UserDtls userDtls = userRepository.findByEmail(email);
		notes.setUserDtls(userDtls);
		Notes updateNotes = notesRepository.save(notes);
		
		if(updateNotes!=null) {
			attributes.addFlashAttribute("msg", "Note Updated Successfully");
		} else {
			attributes.addFlashAttribute("msg", "Something wrong on Server");
		}
		
		System.out.println(notes);
		return "redirect:/user/viewnotes/0";
	}
	

	@GetMapping("/deletenotes/{id}")
	public String deletenotes(@PathVariable int id, RedirectAttributes attributes) {
		Optional<Notes> notes = notesRepository.findById(id);
		if(notes!=null) {
			notesRepository.delete(notes.get());
			attributes.addFlashAttribute("msg", "Note Deleted Successfully");
		}
		
		return "redirect:/user/viewnotes/0";
	}
	
	@GetMapping("/viewnotes/{page}")
	public String viewnotes(@PathVariable int page, Model m, Principal p) {
		
		String email = p.getName();
		UserDtls u = userRepository.findByEmail(email);
		
		Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
		Page<Notes> notes = notesRepository.findNotesByUser(u.getId(), pageable);
		
		m.addAttribute("pageNo", page);
		m.addAttribute("totalPage", notes.getTotalPages());
		m.addAttribute("Notes", notes);
		m.addAttribute("totalElements", notes.getTotalElements());
		
		return "user/view_notes";
	}
	
	@GetMapping("/editnotes/{id}")
	public String editnotes(@PathVariable int id,Model m) {
		
		Optional<Notes> n = notesRepository.findById(id);
		if(n!=null) {
			Notes note = n.get();
			m.addAttribute("notes", note);
		}
		return "user/edit_notes";
	}
		
	@GetMapping("/viewprofile")
	public String getprofile() {
		return "user/view_profile";
	}
	
	@PostMapping("/savenotes")
	public String savenotes(@ModelAttribute Notes note, RedirectAttributes attributes, 
			Principal p) {
		String email = p.getName();
		UserDtls u = userRepository.findByEmail(email);
		note.setUserDtls(u);
		
		Notes n = notesRepository.save(note);
		
		if(n!=null) {
			attributes.addFlashAttribute("msg", "Note Added Successfully");
		} else {
			attributes.addFlashAttribute("msg", "Something wrong on Server");
		}
		
		System.out.println(note);
		return "redirect:/user/addnotes";
	}

	@PostMapping("/updateuser")
	public String updateuser(@ModelAttribute UserDtls user, 
			RedirectAttributes attributes, Model m) {
		Optional<UserDtls> oldUser = userRepository.findById(user.getId());
		if(oldUser!=null) {
			user.setPassword(oldUser.get().getPassword());
			user.setRole(oldUser.get().getRole());
			user.setEmail(oldUser.get().getEmail());
			
			UserDtls updateUser = userRepository.save(user);
			if(updateUser!=null) {
				m.addAttribute("user", updateUser);
				attributes.addFlashAttribute("msg", "Profile Updated Successfully");
			}
		}
		System.out.println(user);
		return "redirect:/user/viewprofile";		
	}
}
