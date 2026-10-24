package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("students", q == null || q.isBlank()
                ? studentRepository.findAll()
                : studentRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrCourseContainingIgnoreCase(q, q, q));
        model.addAttribute("q", q == null ? "" : q);
        return "students/list";
    }

    @GetMapping("/students/new")
    public String newStudent(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Add Student");
        return "students/form";
    }

    @PostMapping("/students")
    public String create(@Valid @ModelAttribute Student student, BindingResult result,
                         RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add Student");
            return "students/form";
        }
        try {
            studentRepository.save(student);
            redirect.addFlashAttribute("success", "Student added successfully.");
            return "redirect:/";
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("email", "duplicate", "This email is already registered.");
            model.addAttribute("pageTitle", "Add Student");
            return "students/form";
        }
    }

    @GetMapping("/students/{id}/edit")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        return studentRepository.findById(id).map(student -> {
            model.addAttribute("student", student);
            model.addAttribute("pageTitle", "Edit Student");
            return "students/form";
        }).orElseGet(() -> {
            redirect.addFlashAttribute("error", "Student not found.");
            return "redirect:/";
        });
    }

    @PostMapping("/students/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Student student,
                         BindingResult result, RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            student.setId(id);
            model.addAttribute("pageTitle", "Edit Student");
            return "students/form";
        }
        student.setId(id);
        try {
            studentRepository.save(student);
            redirect.addFlashAttribute("success", "Student updated successfully.");
            return "redirect:/";
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("email", "duplicate", "This email is already registered.");
            model.addAttribute("pageTitle", "Edit Student");
            return "students/form";
        }
    }

    @PostMapping("/students/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            redirect.addFlashAttribute("success", "Student deleted successfully.");
        } else {
            redirect.addFlashAttribute("error", "Student not found.");
        }
        return "redirect:/";
    }
}
