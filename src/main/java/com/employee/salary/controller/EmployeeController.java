package com.employee.salary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.salary.dto.EmployeeResponse;
import com.employee.salary.entity.Employee;
import com.employee.salary.service.EmployeeService;

@RestController("/")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@PostMapping("/employee/save")
	public ResponseEntity<?> saveEmployee(@RequestBody Employee employee){
		try {
		return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	

	@GetMapping("/employee/tax/{id}")
	public ResponseEntity<?> calculateTax(@PathVariable Integer id){
		try {
		return new ResponseEntity<EmployeeResponse>(employeeService.calculateTaxForCurrentYear(id), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	} 
}
