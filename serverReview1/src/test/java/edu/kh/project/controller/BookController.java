package edu.kh.project.controller;

public class BookController {
	@PostMapping("select")

	public String selectStudent(Model model, @ModelAttribute ) {

	model.addAttribute("stdName", student.getStdName());

	model.addAttribute("stdAge", student.getStdAge());

	model.addAttribute("stdAddress", student.getStdAddress());

	return "student/select";

	}
}
