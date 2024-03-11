package com.yskinfy.webemail.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yskinfy.webemail.util.ZenovelLoginUtil;

import jakarta.transaction.Transactional;


public interface ZenovelLoginRepo extends JpaRepository<ZenovelLoginUtil,String>{

	@Query(nativeQuery=true , value="select * from zenovel_login where username=:username")
	List<ZenovelLoginUtil> findUsername(@Param("username") String username);
	
	@Transactional
	@Modifying
	@Query(nativeQuery=true , value="update zenovel_login set unsuccessful_logins=:value where username=:username")
	void unsuccessfulLogins(@Param("value") String value, @Param("username") String username);
	
	@Transactional
	@Modifying
	@Query(nativeQuery=true , value="update zenovel_login set token=:token where username=:username")
	void updateToken(@Param("token") String token,@Param("username") String username);
	
	
	@Transactional
	@Modifying
	@Query(nativeQuery=true , value="update zenovel_login set last_logged_in=:lastLoggedIn where username=:username")
	void updateLoggedIn(@Param("lastLoggedIn") String lastLoggedIn,@Param("username") String username);
}
