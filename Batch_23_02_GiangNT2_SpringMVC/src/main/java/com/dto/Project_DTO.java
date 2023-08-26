package com.dto;

/**
 * Project_DTO
 * 
 * Version 0.0.1-SNAPSHOT
 * 
 * Date: 28-4-2023
 * 
 * Copyright
 * 
 * Modification Logs:
 * 
 * DATE AUTHOR DESCRIPTION
 * -----------------------------------------------------------------------
 * 09-06-2023 GiangNT2 Create
 * 
 */
public class Project_DTO {
	private Integer projectId;
	private String projectName;
	private Integer deptId;
	private String difficulty;
	private Integer version;
	private String location;

	public Project_DTO() {
		super();
	}

	public Project_DTO(Integer projectId, String projectName, Integer deptId, String difficulty, Integer version,
			String location) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.deptId = deptId;
		this.difficulty = difficulty;
		this.version = version;
		this.location = location;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
