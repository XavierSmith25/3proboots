package com.kedu.home.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kedu.home.services.NaverRouteService;
import com.kedu.home.services.TmapRouteService;

@RestController
@RequestMapping("/api")
public class NaverRouteController {
	
	@Autowired
	private NaverRouteService naverRouteService;
	
	@Autowired
	private TmapRouteService tmapRouteService;
	
	@GetMapping("/getNaverRoute")
	public ResponseEntity<String> getRoute(
			@RequestParam double startX,
			@RequestParam double startY,
			@RequestParam String goals) throws IOException {
		String decodedGoals = URLDecoder.decode(goals,"UTF-8");
		List<Map<String, Double>> goalList = new ObjectMapper().readValue(decodedGoals, new TypeReference<List<Map<String, Double>>>() {});
		String result = naverRouteService.getNaverRoute(startX, startY, goalList);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/getTmapTransitRoute")
	public ResponseEntity<String> getTrnasitRoute(@RequestBody Map<String, Object> request) {
		double startX = (double) request.get("startX");
	    double startY = (double) request.get("startY");
	    double endX = (double) request.get("endX");
	    double endY = (double) request.get("endY");
	    
	    // Tmap API 호출 서비스 메서드 실행
	    String result = tmapRouteService.getTmapTransitRoute(startX, startY, endX, endY);
	  
	    return ResponseEntity.ok(result);
	
	}
	
	
}
