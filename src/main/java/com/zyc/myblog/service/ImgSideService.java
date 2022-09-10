package com.zyc.myblog.service;

import com.zyc.myblog.po.ImgFlow;
import com.zyc.myblog.po.ImgSide;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ImgSideService {
    ImgSide saveImg(ImgSide imgSide);

    void deleteImgById(Long id);

    ImgSide updateImg(Long id,ImgSide imgSide) throws NotFoundException;

    List<ImgSide> findAll();

    ImgSide findById(Long id) throws NotFoundException;
}
