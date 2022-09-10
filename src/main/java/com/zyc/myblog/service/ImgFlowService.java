package com.zyc.myblog.service;

import com.zyc.myblog.po.ImgFlow;

import java.util.List;

public interface ImgFlowService {

    ImgFlow saveImg(ImgFlow imgFlow);

    void deleteImgById(Long id);

    ImgFlow updateImg(Long id,ImgFlow imgFlow);

    List<ImgFlow> findAll();

    ImgFlow findById(Long id);
}
