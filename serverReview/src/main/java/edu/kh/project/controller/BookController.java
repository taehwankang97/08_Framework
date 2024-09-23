package edu.kh.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.kh.project.dto.Book;

@Controller
public class BookController {
	
	
	@GetMapping("goHome")
	public String goHomeBook(@ModelAttribute Book book, Model model) {
		

		model.addAttribute("bookTitle", book.getBookTitle());
		model.addAttribute("bookWriter", book.getBookWriter());
		model.addAttribute("bookPrice", book.getBookPrice());

		return "book/book";
	}

}
