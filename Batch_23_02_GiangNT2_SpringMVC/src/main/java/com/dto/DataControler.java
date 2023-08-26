package com.dto;

/** 
 * Data_Controler
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
import org.springframework.stereotype.Component;

import com.model.Users;

@Component
public class DataControler {
	// Biến này để lưu lại giá trị khi search để tái sử dụng
	private Project_DTO project;
	// Lưu lại page hiện tại
	private Integer paging = 1;
	// Biến này để lưu lại giá trị khi Update để tái sử dụng
	private Project_DTO data_ProjectUpdate;
	// Biến này để lưu lại giá trị khi Register để tái sử dụng
	private Project_DTO data_ProjectRegister;
	// Biến này để lưu lại thông tin người dùng để tái sử dụng
	private Users data_users;
	// Lưu lại Email để tái sử dụng
	private String authenticationemail;

	public String getAuthenticationemail() {
		return authenticationemail;
	}

	public void setAuthenticationemail(String authenticationemail) {
		this.authenticationemail = authenticationemail;
	}

	public Users getData_users() {
		return data_users;
	}

	public void setData_users(Users data_users) {
		this.data_users = data_users;
	}

	public Project_DTO getData_ProjectRegister() {
		return data_ProjectRegister;
	}

	public void setData_ProjectRegister(Project_DTO data_ProjectRegister) {
		this.data_ProjectRegister = data_ProjectRegister;
	}

	public Project_DTO getData_ProjectUpdate() {
		return data_ProjectUpdate;
	}

	public void setData_ProjectUpdate(Project_DTO data_ProjectUpdate) {
		this.data_ProjectUpdate = data_ProjectUpdate;
	}

	public Project_DTO getProject() {
		return project;
	}

	public void setProject(Project_DTO project) {
		this.project = project;
	}

	public Integer getPaging() {
		return paging;
	}

	public void setPaging(Integer paging) {
		this.paging = paging;
	}
}
