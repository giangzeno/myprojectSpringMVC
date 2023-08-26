package com.service;

/** 
 * Service
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
 * 06-05-2023              GiangNT2            Create
 *  
 * */
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import com.dto.DataControler;
import com.dto.Project_DTO;
import com.mapper.DeptMapper;
import com.mapper.ProjectMapper;
import com.mapper.UsersMapper;
import com.model.Dept;
import com.model.DeptExample;
import com.model.Project;
import com.model.Users;
import com.model.UsersExample;

@org.springframework.stereotype.Service
public class Service {

	/**
	 * 
	 * The USERSMapper instance used for database operations on the USERS table. It
	 * is autowired by the application context.
	 */
	@Autowired
	private UsersMapper usersMapper;
	/**
	 * 
	 * The DataControler instance used for data control and management. It is
	 * autowired by the application context.
	 */
	@Autowired
	private DataControler dataControler;
	/**
	 * 
	 * The DEPTMapper instance used for database operations on the DEPT table. It is
	 * autowired by the application context.
	 */
	@Autowired(required = true)
	private DeptMapper deptMapper;
	/**
	 * 
	 * The PROJECTMapper instance used for database operations on the PROJECT table.
	 * It is autowired by the application context.
	 */
	@Autowired(required = true)
	private ProjectMapper projectMapper;

	/**
	 * 
	 * Retrieves a list of all departments.
	 * 
	 * @return A list of DEPT objects representing all the departments.
	 */
	public List<Dept> allListDep() {
		DeptExample dept = new DeptExample();
		List<Dept> list = deptMapper.selectByExample(dept);
		return list;
	}

	/**
	 * 
	 * Searches for projects based on the provided criteria.
	 * 
	 * @param project   The Project_DTO object containing the search criteria.
	 * @param indexpage The index page number for pagination.
	 * @return A list of PROJECT objects matching the search criteria.
	 */
	public List<Project> searchProject(Project_DTO project, int indexpage) {
		int offset = 0;
		if (indexpage > 1) {
			offset = (indexpage - 1) * 6;
		}
		return projectMapper.selectByRequiment(project.getProjectId(), project.getProjectName(),
				project.getDifficulty(), project.getDeptId(), offset);
	}

	/**
	 * 
	 * Sets up pagination parameters for the view.
	 * 
	 * @param model     The model object to add attributes to.
	 * 
	 * @param indexPage The index page number.
	 * 
	 * @param flax      A flag indicating if the pagination is disabled.
	 * 
	 * @param dto       The Project_DTO object containing the search criteria.
	 */
	public void paging(Model model, int indexPage, boolean flax, Project_DTO dto) {

		if (flax) {
			model.addAttribute("listPage", 0);
			model.addAttribute("pageSize", 0);
			model.addAttribute("indexPage", 0);
			return;
		}
		// Mảng page từ vị trí 0 đến pageSize
		List<Integer> listPageSize = new ArrayList<Integer>();
		// Page hiện tại
		int currentPage = indexPage;
		// Số lượng page hiện có
		int pageSize = 0;
		Long sizeList = projectMapper.coutSelect(dto.getProjectId(), dto.getProjectName(), dto.getDifficulty(),
				dto.getDeptId());
		// Nếu select không có vị giá trị nào thì sizeList = 0
		if (sizeList == null) {
			sizeList = 0L;
		}
		// Nếu sizeList < 6 thì pageSize và currentPage bằng 0
		if (sizeList <= 6) {
			pageSize = 0;
			currentPage = 0;
		}
		// Ngược lại số lượng select được lớn hơn 6 hàng sẽ /6 để tính số lượng page
		else {
			pageSize = (int) Math.ceil((double) sizeList / 6);
		}
		// Nếu page hiện tại lớn hơn số lượng mà page hiện có thì sẽ set page hiện tại bằng page hiện có
		// tái sử dụng bean Datacontroler để sử dụng query theo page hiện có
		if (currentPage > pageSize) {
			dataControler.setPaging(pageSize);
		}
		//  Nếu số lượng page hiện có  là 0 thì setPaging  là 0
		if (pageSize == 0) {
			dataControler.setPaging(0);
		}
		// Lặp dể tạo ra mảng gồm phần tử số lượng page
		for (int i = 1; i <= pageSize; i++) {
			listPageSize.add(i);
		}
		model.addAttribute("endGame", listPageSize);
		model.addAttribute("listPage", listPageSize);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("indexPage", currentPage);
	}

	/**
	 * 
	 * Inserts a new project into the database.
	 * 
	 * @param project The PROJECT object to be inserted.
	 * @return True if the insertion is successful, false otherwise.
	 */
	public Boolean insertProject(Project project) {
		return projectMapper.insert(project) > 0;
	}

	/**
	 * 
	 * Checks if a project with the given projectID already exists in the database.
	 * 
	 * @param model     The model object to add attributes to.
	 * @param projectID The project ID to check.
	 * @return True if the project ID already exists, false otherwise.
	 */
	public Boolean checkExitID(Model model, Integer projectID) {
		if (projectID == null) {
			model.addAttribute("validate", "Project ID already exists");
		} else {
			if (projectMapper.selectByPrimaryKey(projectID) != null) {
				model.addAttribute("validate", "Project ID already exists");
			} else {
				model.addAttribute("validate", "");
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * Checks if a project with the given project name already exists in the
	 * database.
	 * 
	 * @param model        The model object to add attributes to.
	 * @param project_name The project name to check.
	 * @return True if the project name already exists, false otherwise.
	 */
	public Boolean checkUniqueName(Model model, String project_name) {
		if (project_name == null) {
			model.addAttribute("validateprojectName", "Project Name already exists");
		} else {
			if (projectMapper.selectByprojectNM(project_name) != 0) {
				model.addAttribute("validateprojectName", "Project Name already exists");
			} else {
				model.addAttribute("validateprojectName", "");
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * Checks if a project with the given project ID and project name already exists
	 * in the database.
	 * 
	 * @param model        The model object to add attributes to.
	 * @param projectID    The project ID to check.
	 * @param project_name The project name to check.
	 * @return True if either the project ID or project name already exists, false
	 *         otherwise.
	 */
	public Boolean checkDuplicateIDandName(Model model, Integer projectID, String project_name) {
		Boolean flax = false;
		if (checkUniqueName(model, project_name)) {
			flax = true;
		}
		if (checkExitID(model, projectID)) {
			flax = true;
		}
		return flax;
	}

	/**
	 * 
	 * Retrieves a project from the database based on the project ID.
	 * 
	 * @param projectID The ID of the project to retrieve.
	 * @return The Project_DTO object representing the retrieved project.
	 */
	public Project_DTO selectByprojectID(Integer projectID) {
		Project project = projectMapper.selectByPrimaryKey(projectID);
		return new Project_DTO(project.getProjectId(), project.getProjectName(), project.getDeptId(),
				project.getDifficulty(), project.getVersion(), project.getLocation());
	}

	/**
	 * 
	 * Updates a project in the database based on the project ID.
	 * 
	 * @param project The PROJECT object containing the updated project information.
	 * @return True if the update was successful, false otherwise.
	 */
	public Boolean updateByprojectID(Project projectID) {
		return projectMapper.updateByPrimaryKey(projectID) > 0;
	}

	/**
	 * 
	 * Checks if a project name already exists in the database, excluding the
	 * specified project ID.
	 * 
	 * @param model     The model object to add the validation message.
	 * @param projectID The ID of the project to exclude from the validation.
	 * @param projectNM The project name to validate.
	 * @return True if the project name already exists, false otherwise.
	 */
	public Boolean checkUniqueName(Model model, Integer projectID, String projectNM) {
		if (projectNM == null) {
			model.addAttribute("validateprojectName", "Project Name already exists");
			return true;
		}
		if (projectMapper.validateExitsName(projectNM, projectID) > 0) {
			model.addAttribute("validateprojectName", "Project Name already exists");
			return true;
		}
		return false;

	}

	/**
	 * 
	 * Deletes a project from the database based on the project ID.
	 * 
	 * @param projectID The ID of the project to delete.
	 * @return True if the deletion was successful, false otherwise.
	 */
	public Boolean deleteProject(Integer projectID) {
		return projectMapper.deleteByPrimaryKey(projectID) > 0;
	}

	/**
	 * 
	 * Checks if a username and email already exist in the database.
	 * 
	 * @param users The USERS object containing the username and email to check.
	 * 
	 * @param model The model object to add the feedback messages.
	 * 
	 * @return True if either the username or email already exist, false otherwise.
	 */
	public Boolean checkUsernameAndEmailExits(Users users, Model model) {
		UsersExample usersExampleEmail = new UsersExample();
		usersExampleEmail.createCriteria().andEmailEqualTo(users.getEmail());

		UsersExample usersExampleUserName = new UsersExample();
		usersExampleUserName.createCriteria().andUsernameEqualTo(users.getUsername());
		Boolean faBoolean = false;
		if (usersMapper.selectByExample(usersExampleEmail).size() > 0) {
			model.addAttribute("feedbackEmail", "Email Already Exists !!!");
			faBoolean = true;
		}

		if (usersMapper.selectByExample(usersExampleUserName).size() > 0) {
			model.addAttribute("feedbackUserName", "User Name Already Exists !!!");
			faBoolean = true;
		}
		return faBoolean;
	}

	/**
	 * 
	 * Inserts a new user into the database. The password is encoded using
	 * BCryptPasswordEncoder before being stored. The authority is set to "ADMIN"
	 * for the new user.
	 * 
	 * @param users The USERS object representing the user to insert.
	 */
	public void insertUser(Users users) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(users.getPassword());
		users.setPassword(encodedPassword);
		users.setAuthority("ADMIN");
		usersMapper.insert(users);
	}

	/**
	 * 
	 * Sends a verification code to the specified recipient email address.
	 * 
	 * @param recipientEmail The email address of the recipient.
	 * 
	 * @return The randomly generated verification code.
	 */
	public Integer sendVerificationCode(String recipientEmail) {
		// Thông tin đăng nhập email
		final String senderEmail = "giangzeno15@gmail.com";
		final String senderPassword = "ulaujubzbinxmqpu";

		// Cấu hình properties cho session email
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.host", "smtp.gmail.com"); // Thay đổi host email của bạn
		properties.put("mail.smtp.port", "465"); // Thay đổi cổng email của bạn

		// Tạo session email với thông tin đăng nhập
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});
		Random random = new Random();
		int randomNumber = random.nextInt(1000, 10000);
		try {
			// Tạo đối tượng MimeMessage
			Message message = new MimeMessage(session);
			// Đặt thông tin người gửi
			message.setFrom(new InternetAddress(senderEmail));
			// Đặt thông tin người nhận
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			// Đặt tiêu đề email
			message.setSubject("Xác thực đăng ký");
			// Tạo nội dung email
			String emailContent = "Mã xác thực của bạn là: " + randomNumber;
			message.setText(emailContent);
			// Gửi email
			Transport.send(message);
			return randomNumber;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return randomNumber;
	}

	public void sendEmail() {
		
		
	}
}
