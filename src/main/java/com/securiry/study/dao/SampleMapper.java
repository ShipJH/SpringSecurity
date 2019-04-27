package com.securiry.study.dao;

import org.springframework.stereotype.Repository;

import com.securiry.study.vo.UserVo;

@Repository
public interface SampleMapper {

	UserVo getUser();
}
