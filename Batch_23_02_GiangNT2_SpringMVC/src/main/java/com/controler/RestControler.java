package com.controler;

/** 
 * RestController
 * 
 * Version 0.0.1-SNAPSHOT
 * 
 * Date: 28-4-2023
 * 
 * Copyright 
 * 
 * Modification Logs:
 * 
 * DATE                 AUTHOR          DESCRIPTION
 * -----------------------------------------------------------------------
 * 09-06-2023              GiangNT2            Create
 *  
 * */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapper.ProjectMapper;
import com.model.Project;

@RestController
@RequestMapping("restAPI")
public class RestControler {
	@Autowired
	private ProjectMapper projectMapper;

	/**
	 * 
	 * Retrieves all projects using a RESTful API.
	 * 
	 * @return A ResponseEntity containing the list of projects and an HTTP status
	 *         code.
	 */
	@GetMapping("/selectall")
	public ResponseEntity<List<Project>> restSelectAll() {
		List<Project> list = projectMapper.selectByExample(null);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 
	 * Deletes a project by its ID using a RESTful API.
	 * 
	 * @param id The ID of the project to delete.
	 * @return A ResponseEntity with an HTTP status code indicating the result of
	 *         the deletion. csharp Copy code If the deletion is successful,
	 *         HttpStatus.OK is returned. Otherwise, HttpStatus.FAILED_DEPENDENCY is
	 *         returned.
	 */
	@GetMapping("/delete")
	public ResponseEntity<Void> restDeleteById(@RequestParam Integer id) {
		if (projectMapper.deleteByPrimaryKey(id) == 0) {
			return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 
	 * Deletes a project by its ID using a RESTful API.
	 * 
	 * Creates a new project using a RESTful API.
	 * 
	 * @param project The PROJECT object containing the project information to
	 *                create.
	 * @return A ResponseEntity with an HTTP status code indicating the result of
	 *         the creation. If the creation is successful, HttpStatus.OK is
	 *         returned. Otherwise, HttpStatus.FAILED_DEPENDENCY is returned.
	 * 
	 */
	@PostMapping("/create")
	public ResponseEntity<Void> restCreate(@RequestBody Project project) {
		if (projectMapper.insertSelective(project) == 0) {
			return new ResponseEntity<Void>(HttpStatus.FAILED_DEPENDENCY);
		}
		;
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * 
	 * Updates a project using a RESTful API.
	 * 
	 * @param project The PROJECT object containing the updated project information.
	 * @return A ResponseEntity with an HTTP status code indicating the result of
	 *         the update. If the update is successful, HttpStatus.OK is returned.
	 *         Otherwise, HttpStatus.FAILED_DEPENDENCY is returned.
	 * 
	 */
	@PostMapping("/update")
	public ResponseEntity<Void> restUpdate(@RequestBody Project project) {
		if (projectMapper.updateByPrimaryKeySelective(project) == 0) {
			return new ResponseEntity<Void>(HttpStatus.FAILED_DEPENDENCY);
		}
		;

		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
