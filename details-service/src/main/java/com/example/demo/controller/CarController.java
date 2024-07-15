package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.entity.Car;
import com.example.demo.entity.StartMessage;
import com.example.demo.exception.CarNotFoundException;
import com.example.demo.feignproxy.StartServiceProxy;
import com.example.demo.repository.CarRepository;

@RestController
@RequestMapping("car-service/")
public class CarController {

	@Autowired
	private StartServiceProxy startServiceProxy;

	@Autowired
	private CarRepository carRepository;

	@GetMapping("cars")
	public ResponseEntity<List<Car>> getAllCars() {
		return ResponseEntity.ok().body(carRepository.findAll());
	}

	@GetMapping("cars/{id}")
	public ResponseEntity<EntityModel<Car>> getCarById(@PathVariable(name = "id") Long id) {
		Optional<Car> car = carRepository.findById(id);
		if (car.isEmpty()) {
			throw new CarNotFoundException(id.toString());
		}

		//Prepare 'links' along with response to /cars get endpoint with HATEOAS
		EntityModel<Car> entity = EntityModel.of(car.get());

		Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllCars())
				.withRel("all-cars");

		entity.add(link);

		return ResponseEntity.ok().body(entity);
	}

	@PostMapping("cars")
	public ResponseEntity<Car> addCars(@RequestBody Car car) {
		Car savedCar = carRepository.save(car);

		// Create a URI to new Car with id "/cars/1" and returns it in the Header params
		// "location"
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCar.getId())
				.toUri();

		// Created - 201 status code
		return ResponseEntity.created(location).build();
	}

	@GetMapping("v1/cars-greeting")
	public ResponseEntity<StartMessage> greet() {
		return new RestTemplate().getForEntity("http://localhost:8000/start-service", StartMessage.class);
	}

	@GetMapping("v2/cars-greeting")
	public ResponseEntity<StartMessage> greetUsingFeign() {
		return ResponseEntity.ok().body(startServiceProxy.getStartMessage());
	}
}
