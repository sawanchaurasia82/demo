package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ResourceNotFoundException;
import com.example.demo.model.LeadInformation;
import com.example.demo.repository.LeadRepository;

@RestController
public class LeadController {

	@Autowired
	LeadRepository leadRepository;

	@GetMapping("/api/leads/{lead_id}")
	public ResponseEntity<LeadInformation> getEmployeeById(@PathVariable(value = "lead_id") Long employeeId)
			throws ResourceNotFoundException {
		LeadInformation lead = leadRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("lead not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(lead);
	}

	@PostMapping("/api/leads/")
	public LeadInformation createEmployee(@Valid @RequestBody LeadInformation lead) {
		return leadRepository.save(lead);
	}

	@PutMapping("/api/leads/{lead_id}")
	public ResponseEntity<LeadInformation> updateEmployee(@PathVariable(value = "lead_id") Long leadId,
			@Valid @RequestBody LeadInformation leadInformation) throws ResourceNotFoundException {
		LeadInformation leaddetail = leadRepository.findById(leadId)
				.orElseThrow(() -> new ResourceNotFoundException("lead not found for this id :: " + leadId));
		leaddetail.setFirst_name(leadInformation.getFirst_name());
		leaddetail.setLast_name(leadInformation.getLast_name());
		leaddetail.setMobile(leadInformation.getMobile());
		leaddetail.setEmail(leadInformation.getEmail());
		leaddetail.setLocation_type(leadInformation.getLocation_type());
		leaddetail.setLocation_string(leadInformation.getLocation_string());
		final LeadInformation updatedlead = leadRepository.save(leaddetail);
		return ResponseEntity.ok(updatedlead);
	}

	@DeleteMapping("/api/leads/{lead_id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "lead_id") Long leadid)
			throws ResourceNotFoundException {
		LeadInformation lead = leadRepository.findById(leadid)
				.orElseThrow(() -> new ResourceNotFoundException("lead not found for this id :: " + leadid));
		leadRepository.delete(lead);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
