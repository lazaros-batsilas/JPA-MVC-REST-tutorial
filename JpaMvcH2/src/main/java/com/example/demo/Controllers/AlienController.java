package com.example.demo.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Models.Alien;
import com.example.demo.dao.AlienRepo;

@Controller
public class AlienController {
	
	@Autowired
	AlienRepo repo;

	@RequestMapping("/")
	public String home() {
		return "home.jsp";
	}
	
	@RequestMapping("/addAlien")
	public String addAlien(Alien alien) {
		repo.save(alien);
		return "home.jsp";
	}
	
	@RequestMapping("/getAlien")
	public ModelAndView getAlien(@RequestParam int aid) {
		
//		System.out.println(repo.findByTech("Python"));
//		System.out.println(repo.findByAidGreaterThan(102));
//		System.out.println(repo.findByTechSorted("Ruby"));
		
		ModelAndView mv = new ModelAndView("showAlien.jsp");
		Alien alien = repo.findById(aid).orElse(new Alien());
		mv.addObject(alien);
		return mv;
	}
	
	@RequestMapping("/updateAlien")
	public ModelAndView updateAlien(Alien alien) {
		ModelAndView mv = new ModelAndView("showAlien.jsp");
		
		if(!repo.existsById(alien.getAid())) 
		{
			System.out.println("Alien not in db");
			return null;
		} else {
			repo.save(alien);
			mv.addObject("Obj", alien);
			return mv;
			}
	}
	
	@RequestMapping("/removeAlien")
	public ModelAndView deleteAlien(@RequestParam Integer aid) {
		
		ModelAndView mv = new ModelAndView("deletedAlien.jsp"); 
		repo.deleteById(aid);
		mv.addObject("aid", aid);
		return mv;
	}
	
	@GetMapping("/aliens")
	@ResponseBody
	public List<Alien> getAliens() {	
		
		return (List<Alien>) repo.findAll();
	}
	
	@GetMapping("/alien/{aid}")
	@ResponseBody
	public Optional<Alien> getAlienById(@PathVariable("aid") int aid) {	
		
		return repo.findById(aid);
	}
	
	@PostMapping("/alien")
	@ResponseBody
	public Alien addAlienRest(@RequestBody Alien alien) {

		repo.save(alien);
		return alien;
	}
	
	@DeleteMapping("/alien/{aid}")
	@ResponseBody
	public ResponseEntity<Map<String, Boolean>> deleteAlienRest(@PathVariable("aid") Integer aid) {
		repo.deleteById(aid);
		Map<String, Boolean> map =new HashMap<String, Boolean>();
		map.put("success deleting user", true);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PutMapping("/alien")
	@ResponseBody
	public Alien saveOrUpdateAlien(@RequestBody Alien a) {
		repo.save(a);
		return a;
	}
	
	

}
