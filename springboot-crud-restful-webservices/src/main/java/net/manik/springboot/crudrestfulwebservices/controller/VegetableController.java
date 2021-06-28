package net.manik.springboot.crudrestfulwebservices.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.manik.springboot.crudrestfulwebservices.model.Vegetable;
import net.manik.springboot.crudrestfulwebservices.repository.VegetableRepository;
import net.manik.springboot.crudrestfulwebservices.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class VegetableController {
	
	@Autowired
	private VegetableRepository vegetableRepository;
	
	@GetMapping("/vegetables")
	public List<Vegetable> getAllVegetable(){
		return vegetableRepository.findAll();
	}
	
	@PostMapping("/vegetables")
	public Vegetable createVegetable(@RequestBody Vegetable vegetable) {
		return vegetableRepository.save(vegetable);
	}
	
	@GetMapping("/vegetable/{id}")
	public ResponseEntity<Vegetable> getVegetableById(@PathVariable(value="id") long id) throws ResourceNotFoundException {
		Vegetable veg=vegetableRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Vegetable not found"));
		
		return ResponseEntity.ok().body(veg);
	}
	
	@PutMapping("/vegetable/{id}")
	public ResponseEntity<Vegetable> updateVegetable(@PathVariable(value="id") long id,@RequestBody Vegetable vegByFrontEnd) throws ResourceNotFoundException {
		Vegetable veg=vegetableRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Vegetable not found"));
		veg.setName(vegByFrontEnd.getName());
		veg.setPrice(vegByFrontEnd.getPrice());
		vegetableRepository.save(veg);
		
		return ResponseEntity.ok().body(veg);
	}
	
	@DeleteMapping("/vegetable/{id}")
	public ResponseEntity deleteVegetable(@PathVariable(value="id") long id) throws ResourceNotFoundException  {
		vegetableRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Vegetable not found"));
		vegetableRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/vegetable/offer/{perc}")
	public List<Vegetable> setActualPrice(@PathVariable(value="perc") int perc) throws ResourceNotFoundException{
		 List<Vegetable> vegs=vegetableRepository.findAll();
		 
		 for(Vegetable v:vegs) {
			 v.setActual(v.getPrice()-(v.getPrice()*perc)/100);
		 }
		 
		 return vegs;
	}
}
