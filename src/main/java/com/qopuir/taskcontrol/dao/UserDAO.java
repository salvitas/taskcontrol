package com.qopuir.taskcontrol.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qopuir.taskcontrol.entities.UserVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;

public interface UserDAO {
	/**
     * Create a new user.
     */
    void create(UserVO user);
    
    /**
     * Get list of users
     */
    List<UserVO> list();
    
    /**
     * Get list of control's users
     */
    List<UserVO> listControlUsers(@Param("controlName") ControlName controlName);
}