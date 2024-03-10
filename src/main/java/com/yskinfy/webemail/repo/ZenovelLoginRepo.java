package com.yskinfy.webemail.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yskinfy.webemail.util.ZenovelLoginUtil;


public interface ZenovelLoginRepo extends JpaRepository<ZenovelLoginUtil,String>{

	
}
