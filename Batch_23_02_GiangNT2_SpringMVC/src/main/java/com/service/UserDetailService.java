package com.service;

/** 
 * UserDetailService
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
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.model.Users;
import com.model.UsersExample;

@Component
public class UserDetailService implements UserDetailsService {
	/**
	 * 
	 * The USERSMapper instance used for database operations on the USERS table. It
	 * is autowired by the application context.
	 */
	@Autowired
	private com.mapper.UsersMapper mapper;

	/**
	 * 
	 * Loads user details by username for authentication.
	 * 
	 * @param username The username of the user.
	 * @return The UserDetails object containing the user details.
	 * @throws UsernameNotFoundException if the user is not found with the specified
	 *                                   username.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsersExample userExample = new UsersExample();
		userExample.createCriteria().andUsernameEqualTo(username);
		List<Users> list = mapper.selectByExample(userExample);
		List<GrantedAuthority> authorities = new ArrayList<>();
		GrantedAuthority authority;
		if (list.size() > 0) {
			Users user = list.get(0);
			authority = new SimpleGrantedAuthority(user.getAuthority());
			authorities.add(authority);
			UserDetails details = new User(username, user.getPassword(), authorities);
			return details;
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}
