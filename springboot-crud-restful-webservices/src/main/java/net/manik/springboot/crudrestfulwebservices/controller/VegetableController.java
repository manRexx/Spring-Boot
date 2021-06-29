package net.manik.springboot.crudrestfulwebservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

@Controller
public class VegetableController {
	
	
	@Autowired
	private VegetableRepository vegetableRepository;
	
	@GetMapping("/index")
	public String getAllVegetable(Model model){
		model.addAttribute("vegetables", vegetableRepository.findAll());
		return "index";
	}
	
	@GetMapping("/addVeg")	
	public String addVeg(Vegetable vegetable){
		return "addVeg";
	}
	
	@PostMapping("/vegetables")
	public String createVegetable(Vegetable vegetable, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
            return "add-user";
        }
		
		System.out.print(vegetable);
		vegetableRepository.save(vegetable);
		
		return "redirect:/index";
	}
	
	@GetMapping("/vegetable/{id}")
	public ResponseEntity<Vegetable> getVegetableById(@PathVariable(value="id") long id) throws ResourceNotFoundException {
		Vegetable veg=vegetableRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Vegetable not found"));
		
		return ResponseEntity.ok().body(veg);
	}
	
	@GetMapping("/vegetable/edit/{id}")
	public String updateVegetable(@PathVariable(value="id") long id,Model model) throws ResourceNotFoundException {
		Vegetable veg=vegetableRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Vegetable not found"));
		model.addAttribute("vegetable", veg);
		
		return "updateVeg";
	}
	
	@PostMapping("/update/{id}")
	public String updateVegetable(@PathVariable(value="id") long id, Vegetable vegetable, BindingResult result, Model model) {
		if (result.hasErrors()) {
	        vegetable.setId(id);
	        return "updateVegetable";
	    }
	        
		vegetableRepository.save(vegetable);
	    return "redirect:/index";
	}
	
	@GetMapping("/vegetable/delete/{id}")
	public String deleteVegetable(@PathVariable(value="id") long id, Model model) throws ResourceNotFoundException  {
		vegetableRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Vegetable not found"));
		vegetableRepository.deleteById(id);
		
		return "redirect:/index";
	}
	
	@PutMapping("/vegetable/offer/{id}/{perc}")
	public Vegetable setActualPriceById(@PathVariable(value="perc") int perc,@PathVariable(value="id") long id) throws ResourceNotFoundException{
		 Vegetable veg=vegetableRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Vegetable not found"));;
		 veg.setActual(veg.getPrice()-(veg.getPrice()*perc)/100);
		 vegetableRepository.save(veg);
		 
		 System.out.print(veg);
		 
		 return veg;
	}
	
	@PutMapping("/vegetable/offer/{perc}")
	public List<Vegetable> setActualPrice(@PathVariable(value="perc") int perc) throws ResourceNotFoundException{
		 List<Vegetable> vegs=vegetableRepository.findAll();
		 
		 for(Vegetable v:vegs) {
			 v.setActual(v.getPrice()-(v.getPrice()*perc)/100);
			 vegetableRepository.save(v);
		 }
		 
		 return vegs;
	}
}
