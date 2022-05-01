package com.code.et.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.code.et.model.Response;
import com.code.et.model.Tracker;
import com.code.et.service.TrackerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/tracker")
public class TrackerController {
	@Autowired
	private TrackerService service;

	@Value("${api-secret-key}")
	private String apiKey;
	@Value("${token-validity}")
	private Long tokenValidity;

	private String generateJWTToken(Tracker tracker) {
		Long timestamp = System.currentTimeMillis();
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, apiKey)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + tokenValidity))
				.claim("id", tracker.getId())
				.compact();
	}

	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody Tracker tracker) {
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("token", generateJWTToken(service.saveTracker(tracker))))
						.message("Tracker created/updated")
						.status(HttpStatus.CREATED)
						.statusCode(HttpStatus.CREATED.value())
						.build());
	}

	@PostMapping("/login")
	public ResponseEntity<Response> loginTracker(@RequestBody Map<String, Object> tracker) {
		Tracker tempTracker = service.validateTracker(tracker.get("email").toString(),
				tracker.get("password").toString());
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("token", generateJWTToken(tempTracker)))
						.message("Tracker logged in")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
	}

	@GetMapping("/account")
	public ResponseEntity<Response> fetchTracker(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("tracker", service.getTracker(userId)))
						.message("Tracker retrieved")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
	}

	@PutMapping("/account")
	public ResponseEntity<Response> saveTracker(@Valid @RequestBody Tracker tracker, HttpServletRequest request) {
		int userId = (Integer) request.getAttribute("userId");
		tracker.setId(userId);
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("token", generateJWTToken(service.saveTracker(tracker))))
						.message("Tracker created/updated")
						.status(HttpStatus.CREATED)
						.statusCode(HttpStatus.CREATED.value())
						.build());
	}

	@DeleteMapping("/account")
	public ResponseEntity<Response> deleteTracker(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
		service.deleteTracker(userId);
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.message("Tracker deleted")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
	}
}
