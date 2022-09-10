package com.zyc.myblog.dao;

import com.zyc.myblog.po.ImgSide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface ImgSideRepository extends JpaRepository<ImgSide,Long> {

}
