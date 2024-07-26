package com.employee.salary.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.salary.dto.EmployeeResponse;
import com.employee.salary.entity.Employee;
import com.employee.salary.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	public Employee saveEmployee(Employee employee) {
		Employee emp = null;
		try {
			if (employee != null) {
				emp = employeeRepository.save(employee);
			}
		} catch (Exception e) {
			System.out.println("Not able to save Employee " + e.getMessage());
		}
		return emp;
	}

	public EmployeeResponse calculateTaxForCurrentYear(Integer id) {
		EmployeeResponse employeeResponse = new EmployeeResponse();
		Long annualSalary = 0L;
		
		Employee employee = employeeRepository.findById(id).orElse(null);

		try {
			annualSalary = calculateAnnualSalary(employee);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Double cessAmount = calculateCessAmount(annualSalary);
		Double tax = calculateTaxAmount(annualSalary);

		employeeResponse.setEmployeeCode(id != null ?id:null);
		employeeResponse.setFirstName(employee.getFirstName() !=null ? employee.getFirstName(): null);
		employeeResponse.setLastName(employee.getLastName() !=null ? employee.getLastName(): null);
		employeeResponse.setSalary(annualSalary != null ?annualSalary:null);
		employeeResponse.setTaxAmount(tax != null ?tax:null);
		employeeResponse.setCessAmount(cessAmount != null ? cessAmount:null);
		return employeeResponse;
	}

	private Double calculateCessAmount(Long annualSalary) {
		Double cessAmount = 0.0;
		if (annualSalary >= 2500000) {
			cessAmount = 0.2 * (annualSalary - 2500000);
		}
		return cessAmount;

	}

	private Double calculateTaxAmount(Long annualSalary) {
		Double taxAmount = 0.0;
		if (annualSalary <= 250000) {
			return taxAmount;
		} else if (annualSalary > 250000 && annualSalary <= 500000) {
			taxAmount = 0.05 * annualSalary;
		} else if (annualSalary > 500000 && annualSalary <= 1000000) {
			taxAmount = 0.1 * annualSalary;
		} else if (annualSalary > 1000000) {
			taxAmount = 0.2 * annualSalary;
		}
		return taxAmount;
	}

	private Long calculateAnnualSalary(Employee employee) throws ParseException {
		Long annualSalary = 0L;
		int totalWorkingDays = 0;
		Long finalSalaryPerMonth = 0L;

		Long employeeSalaryPerMonth = employee.getSalary();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dateOfJoining = LocalDate.parse(employee.getDOJ(), formatter);

		// Annual Salary is from April to March
		switch (dateOfJoining.getMonth()) {
		case APRIL:
			finalSalaryPerMonth = perMonthSalary(totalWorkingDays, employeeSalaryPerMonth, dateOfJoining);
			annualSalary = finalSalaryPerMonth * 12;
			break;
		case MAY:
			finalSalaryPerMonth = perMonthSalary(totalWorkingDays, employeeSalaryPerMonth, dateOfJoining);
			annualSalary = finalSalaryPerMonth * 11;
			break;
		case JUNE:
			finalSalaryPerMonth = perMonthSalary(totalWorkingDays, employeeSalaryPerMonth, dateOfJoining);
			annualSalary = finalSalaryPerMonth * 10;
			break;
		case JULY:
			finalSalaryPerMonth = perMonthSalary(totalWorkingDays, employeeSalaryPerMonth, dateOfJoining);
			annualSalary = finalSalaryPerMonth * 9;
			break;
		case AUGUST:
			finalSalaryPerMonth = perMonthSalary(totalWorkingDays, employeeSalaryPerMonth, dateOfJoining);
			annualSalary = finalSalaryPerMonth * 8;
			break;
		case SEPTEMBER:
			finalSalaryPerMonth = perMonthSalary(totalWorkingDays, employeeSalaryPerMonth, dateOfJoining);
			annualSalary = finalSalaryPerMonth * 7;
			break;
		case OCTOBER:
			finalSalaryPerMonth = perMonthSalary(totalWorkingDays, employeeSalaryPerMonth, dateOfJoining);
			annualSalary = finalSalaryPerMonth * 6;
			break;
		case NOVEMBER:
			finalSalaryPerMonth = perMonthSalary(totalWorkingDays, employeeSalaryPerMonth, dateOfJoining);
			annualSalary = finalSalaryPerMonth * 5;
			break;
		case DECEMBER:
			finalSalaryPerMonth = perMonthSalary(totalWorkingDays, employeeSalaryPerMonth, dateOfJoining);
			annualSalary = finalSalaryPerMonth * 4;
			break;
		case JANUARY:
			finalSalaryPerMonth = perMonthSalary(totalWorkingDays, employeeSalaryPerMonth, dateOfJoining);
			annualSalary = finalSalaryPerMonth * 3;
			break;
		case FEBRUARY:
			finalSalaryPerMonth = perMonthSalary(totalWorkingDays, employeeSalaryPerMonth, dateOfJoining);
			annualSalary = finalSalaryPerMonth * 2;
			break;
		case MARCH:
			finalSalaryPerMonth = perMonthSalary(totalWorkingDays, employeeSalaryPerMonth, dateOfJoining);
			annualSalary = finalSalaryPerMonth;
			break;
		default:
			annualSalary = 0L;
			break;

		}
		return annualSalary;
	}

	public Long perMonthSalary(int totalWorkingDays, Long employeeSalaryPerMonth, LocalDate dateOfJoining) {
		totalWorkingDays = dateOfJoining.lengthOfMonth() - dateOfJoining.getDayOfMonth();
		return totalWorkingDays * (employeeSalaryPerMonth / dateOfJoining.lengthOfMonth());
	}

}
