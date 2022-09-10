package com.zyc.myblog.dao;

import com.zyc.myblog.po.ImgFlow;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;

@Resource
public interface ImgFlowRepository extends JpaRepository<ImgFlow,Long> {

}
