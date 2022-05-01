package com.code.et.controller;

import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.code.et.model.Response;
import com.code.et.model.Transaction;
import com.code.et.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category/{categoryId}/transaction")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @GetMapping
    public ResponseEntity<Response> fetchTransactions(HttpServletRequest request, @PathVariable("categoryId") int categoryId) {
        int userId = (Integer) request.getAttribute("userId");
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("transaction", service.getAllTransactionsByCategory(userId, categoryId)))
						.message("Transaction retrieved")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
    }

	@GetMapping("/{id}")
    public ResponseEntity<Response> fetchTransaction(HttpServletRequest request, @PathVariable("categoryId") int categoryId, @PathVariable("id") int id) {
        int userId = (Integer) request.getAttribute("userId");
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("transaction", service.getTransaction(userId, categoryId, id)))
						.message("Transaction retrieved")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<Response> saveTransaction(HttpServletRequest request, @PathVariable("categoryId") int categoryId, @Valid @RequestBody Transaction transaction) {
        int userId = (Integer) request.getAttribute("userId");
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("categories", service.saveTransaction(userId, categoryId, transaction)))
						.message("Transaction saved")
						.status(HttpStatus.CREATED)
						.statusCode(HttpStatus.CREATED.value())
						.build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteTransaction(HttpServletRequest request, @PathVariable("categoryId") int categoryId, @PathVariable("id") int id) {
        int userId = (Integer) request.getAttribute("userId");
        service.deleteTransaction(userId, categoryId, id);
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.message("Transaction deleted")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
    }
}
