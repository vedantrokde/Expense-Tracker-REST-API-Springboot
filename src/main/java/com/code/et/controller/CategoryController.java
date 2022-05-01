package com.code.et.controller;

import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.code.et.model.Category;
import com.code.et.model.Response;
import com.code.et.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<Response> fetchAll(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("categories", service.getAllCategories(userId)))
						.message("Categories retrieved")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Response> fetchCategory(HttpServletRequest request, @PathVariable("id") int id) {
        int userId = (Integer) request.getAttribute("userId");
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("category", service.getCategory(userId, id)))
						.message("Category retrieved")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<Response> saveCategory(HttpServletRequest request, @RequestBody Category category) {
        int userId = (Integer) request.getAttribute("userId");
        return ResponseEntity.ok(
            Response.builder()
                    .timeStamp(LocalDateTime.now())
                    .data(Map.of("category", service.saveCategory(userId, category)))
                    .message("Category saved")
                    .status(HttpStatus.CREATED)
                    .statusCode(HttpStatus.CREATED.value())
                    .build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCategory(HttpServletRequest request, @PathVariable("id") int id) {
        int userId = (Integer) request.getAttribute("userId");
        service.deleteCategory(userId, id);
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.message("Category deleted")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
    }
}
