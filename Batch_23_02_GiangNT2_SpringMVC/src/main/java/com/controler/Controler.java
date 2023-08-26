package com.controler;

/** 
 * Controller
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
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dto.DataControler;
import com.dto.Project_DTO;
import com.model.Project;
import com.model.Users;
import com.service.Service;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class Controler {
	public static final String VIEWS_REGISTER = "views/register";
	public static final String VIEWS_UPDATE = "views/update";
	public static final String VIEWS_HOME = "views/home";

	@GetMapping("/admin")
	public String hehe() {
		return VIEWS_HOME;
	}

	/**
	 * 
	 * The DataControler instance used for data control and management. It is
	 * autowired by the application context.
	 */
	@Autowired
	private DataControler dataControler;
	/**
	 * 
	 * The Service instance used for service operations. It is autowired by the
	 * application context.
	 */
	@Autowired
	private Service service;

	/**
	 * 
	 * Retrieves the login page.
	 * 
	 * @return The view name for the login page.
	 */
	@GetMapping("/login")
	public String loginSecurity() {
		dataControler.setProject(new Project_DTO());
		dataControler.setPaging(1);
		return "views/login";
	}

	/**
	 * Retrieves the login page. Logs out the user and invalidates the session.
	 * 
	 * @param request The HttpServletRequest object for accessing the session.
	 * @return The redirect view name for the login page with a logout parameter.
	 */
	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		System.gc();
		return "redirect:/home";
	}

	/**
	 * 
	 * Handles the GET request for the "/registerLogin" endpoint. Displays the
	 * registration form for a new user.
	 *
	 * @param model the model object to be populated and passed to the view
	 * @return the logical view name for the registration form view
	 */
	@GetMapping("/registerLogin")
	public String viewRegisterUser(Model model) {
		model.addAttribute("USER", new Users());
		return "views/registerUser";
	}

	@PostMapping("/registerLogin")
	public String registerUser(@ModelAttribute("USER") Users users, Model model) {
		if (service.checkUsernameAndEmailExits(users, model)) {
			return "views/registerUser";
		} else {
			dataControler.setData_users(users);
			String authencode = service.sendVerificationCode(users.getEmail()).toString();
			dataControler.setAuthenticationemail(authencode);
			return "redirect:/authenticationemail";
		}
	}

	/**
	 * Handles the registration/login submission.
	 * 
	 * @param users The USERS object containing the user's information.
	 * @param model The Model object for adding attributes to the view.
	 * @return The view name for either the registration form or the authentication
	 *         email page.
	 */
	@GetMapping("/authenticationemail")
	public String authenticationemailGet(Model model,
			@RequestParam(name = "verificationcode", required = false) Boolean verificationcode) {
		if (verificationcode != null && verificationcode == true) {
			String authencode = service.sendVerificationCode(dataControler.getData_users().getEmail()).toString();
			dataControler.setAuthenticationemail(authencode);
		}
		if (dataControler.getAuthenticationemail() == null) {
			return "redirect:/login";
		}
		return "views/authenticationEmail";

	}

	/**
	 * 
	 * Handles the submission of the authentication email verification code.
	 * 
	 * @param verificationCode The verification code entered by the user.
	 * @param model            The Model object for adding attributes to the view.
	 * @return The view name for either the login page or the authentication email
	 *         page with error feedback.
	 */
	@PostMapping("/authenticationemail")
	public String authenticationemailPost(
			@RequestParam(name = "verificationCode", required = false) String verificationCode, Model model) {

		if (dataControler.getAuthenticationemail().equals(verificationCode)) {
			service.insertUser(dataControler.getData_users());
			dataControler.setData_users(new Users());
			dataControler.setAuthenticationemail(null);
			return "redirect:/login";
		} else {
			model.addAttribute("authenticationemail", ((String) model.getAttribute("authenticationemail")));
			model.addAttribute("feedbackAuthenticationemail", "Mã Xác Thực Không Hợp Lệ");
			return "views/authenticationEmail";
		}
	}

	/**
	 * 
	 * Retrieves the home page.
	 * 
	 * @param checkDelete    Boolean value indicating if a delete feedback should be
	 *                       displayed.
	 * @param feedbackUpdate Boolean value indicating if an update feedback should
	 *                       be displayed.
	 * @param model          The Model object for adding attributes to the view.
	 * @return The view name for the home page.
	 */
	@GetMapping({ "/home" })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String displayHomePage(@RequestParam(name = "feedBackDelete", required = false) Boolean checkDelete,
			@RequestParam(name = "feedbackUpdate", required = false) Boolean feedbackUpdate, Model model) {
		dataControler.setData_ProjectRegister(new Project_DTO());
		if (dataControler.getProject() == null) {
			dataControler.setProject(new Project_DTO());
		}
		service.paging(model, dataControler.getPaging(), false, dataControler.getProject());
		List<Project> list = service.searchProject(dataControler.getProject(), dataControler.getPaging());
		if (list.size() == 0) {
			service.paging(model, 0, true, dataControler.getProject());
		}
		model.addAttribute("feedbackdelete", checkDelete);
		model.addAttribute("feedbackUpdate", feedbackUpdate);
		model.addAttribute("listSearch", list);
		model.addAttribute("Project", dataControler.getProject());
		model.addAttribute("ListDep", service.allListDep());
		return VIEWS_HOME;
	}

	/**
	 * 
	 * Handles the submission of the project search form.
	 * 
	 * @param Project The Project_DTO object containing the search criteria.
	 * @param model   The Model object for adding attributes to the view.
	 * @return The redirect view name for the home page.
	 */

	@PostMapping({ "/home" })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String searchProjectbyProjectDTO(@ModelAttribute(name = "Project") Project_DTO project_Dto, Model model) {
		dataControler.setProject(project_Dto);
		dataControler.setPaging(1);
		return "redirect:/home";
	}

	/**
	 * 
	 * Handles the pagination of the search results on the home page.
	 * 
	 * @param paging The page number to retrieve.
	 * @param model  The Model object for adding attributes to the view.
	 * @return The view name for the home page.
	 */
	@GetMapping({ "home/paging" })
	public String paging(@RequestParam(name = "paging", required = false) Integer paging, Model model) {
		List<Project> list = service.searchProject(dataControler.getProject(), paging);
		dataControler.setPaging(paging);
		if (list.size() == 0) {
			service.paging(model, 0, true, dataControler.getProject());
		} else {
			service.paging(model, paging, false, dataControler.getProject());
		}
		model.addAttribute("listSearch", list);
		model.addAttribute("Project", dataControler.getProject());
		model.addAttribute("ListDep", service.allListDep());
		return VIEWS_HOME;
	}

	/**
	 * 
	 * Retrieves the registration form for a new project.
	 * 
	 * @param model The Model object for adding attributes to the view.
	 * @return The view name for the registration form.
	 */
	@GetMapping("home/register")
	public String viewRegisterProject(Model model) {
		model.addAttribute("Project", dataControler.getData_ProjectRegister());
		if (dataControler.getData_ProjectRegister() == null) {
			model.addAttribute("Project", new Project_DTO());
		}
		model.addAttribute("ListDep", service.allListDep());
		return VIEWS_REGISTER;
	}

	/**
	 * 
	 * Handles the submission of the project registration form.
	 * 
	 * @param Project The Project_DTO object containing the project information.
	 * @param model   The Model object for adding attributes to the view.
	 * @return The view name for either the registration form or the home page with
	 *         validation feedback.
	 */
	@PostMapping({ "home/register" })
	public String registerProject(@ModelAttribute(name = "Project") Project_DTO project_Dto, Model model) {
		model.addAttribute("ListDep", service.allListDep());
		if (service.checkDuplicateIDandName(model, project_Dto.getProjectId(), project_Dto.getProjectName())) {
			return VIEWS_REGISTER;
		} else {
			model.addAttribute("feedbackValidate", true);
			dataControler.setData_ProjectRegister(project_Dto);
			return VIEWS_REGISTER;
		}
	}

	/**
	 * 
	 * Retrieves the form for inserting a new project.
	 * 
	 * @param model The Model object for adding attributes to the view.
	 * @return The view name for the registration form.
	 */
	@GetMapping("home/register/insert")
	public String viewInsertProject(Model model) {
		model.addAttribute("Project", new Project_DTO());
		model.addAttribute("ListDep", service.allListDep());
		return VIEWS_REGISTER;
	}

	/**
	 * 
	 * Handles the submission of the new project insertion form.
	 * 
	 * @param Project The Project_DTO object containing the project information.
	 * @param model   The Model object for adding attributes to the view.
	 * @return The view name for the registration form.
	 */
	@PostMapping("home/register/insert")
	public String insertProject(@ModelAttribute(name = "Project") Project_DTO project_Dto, Model model) {
		Project project = new Project(project_Dto.getProjectId(), project_Dto.getProjectName(), project_Dto.getDeptId(),
				project_Dto.getDifficulty(), new Date(), null, project_Dto.getVersion(), project_Dto.getLocation());
		dataControler.setData_ProjectRegister(new Project_DTO());
		model.addAttribute("feedbackregister", service.insertProject(project));
		model.addAttribute("Project", new Project_DTO());
		model.addAttribute("ListDep", service.allListDep());
		return VIEWS_REGISTER;
	}

	/**
	 * 
	 * Retrieves the update form for a project.
	 * 
	 * @param projectid The ID of the project to update.
	 * @param model     The Model object for adding attributes to the view.
	 * @return The view name for the update form.
	 */
	@GetMapping({ "home/update" })
	public String viewUpdateProject(@RequestParam(name = "projectid", required = false) Integer projectid,
			Model model) {
		model.addAttribute("ListDep", service.allListDep());
		if (projectid == null) {
			model.addAttribute("Project", dataControler.getData_ProjectUpdate());
			return VIEWS_UPDATE;
		}
		Project_DTO project_DTO = service.selectByprojectID(projectid);
		dataControler.setData_ProjectUpdate(project_DTO);
		model.addAttribute("Project", project_DTO);
		return VIEWS_UPDATE;
	}

	/**
	 * 
	 * Handles the submission of the project update form.
	 * 
	 * @param Project The Project_DTO object containing the updated project
	 *                information.
	 * @param model   The Model object for adding attributes to the view.
	 * @return The view name for either the update form or the home page with
	 *         validation feedback.
	 */
	@PostMapping({ "home/update" })
	public String updateProject(@ModelAttribute(name = "Project") Project_DTO project_Dto, Model model) {
		model.addAttribute("ListDep", service.allListDep());
		if (service.checkUniqueName(model, project_Dto.getProjectId(), project_Dto.getProjectName())) {
			return VIEWS_UPDATE;
		} else {
			model.addAttribute("feedbackValidate", true);
			dataControler.setData_ProjectUpdate(project_Dto);
			return VIEWS_UPDATE;
		}
	}

	/**
	 * 
	 * Handles the submission of the project update form for updating the project.
	 * 
	 * @param Project The Project_DTO object containing the updated project
	 *                information.
	 * @param model   The Model object for adding attributes to the view.
	 * @return The redirect view name for the home page with the update feedback.
	 */
	@PostMapping("home/update/update")
	public String updateProjectByprojectID(@ModelAttribute(name = "Project") Project_DTO project_Dto, Model model) {
		Project project = new Project(project_Dto.getProjectId(), project_Dto.getProjectName(), project_Dto.getDeptId(),
				project_Dto.getDifficulty(), null, new Date(), project_Dto.getVersion(), project_Dto.getLocation());
		Boolean feedbackUpdate = service.updateByprojectID(project);
		return "redirect:/home?feedbackUpdate=" + feedbackUpdate;
	}

	/**
	 * 
	 * Handles the deletion of a project.
	 * 
	 * @param projectId The ID of the project to delete.
	 * @param model     The Model object for adding attributes to the view.
	 * @return The redirect view name for the home page with the delete feedback.
	 */
	@GetMapping("/home/delete")
	public String deleteProjectbyprojectID(@RequestParam(name = "projectId") Integer projectId, Model model) {
		Boolean checkDelete = service.deleteProject(projectId);
		return "redirect:/home?feedBackDelete=" + checkDelete;
	}

}
