package com.zyc.myblog.service;

import com.zyc.myblog.dao.ImgFlowRepository;
import com.zyc.myblog.po.ImgFlow;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ImgFlowServiceImpl implements ImgFlowService{

    private ImgFlowRepository imgFlowRepository;
    @Autowired
    public void setImgFlowRepository(ImgFlowRepository imgFlowRepository) {
        this.imgFlowRepository = imgFlowRepository;
    }
    @Transactional
    @Override
    public ImgFlow saveImg(ImgFlow imgFlow) {
        return imgFlowRepository.save(imgFlow);
    }
    @Transactional
    @Override
    public void deleteImgById(Long id) {
         ImgFlow imgFlow = imgFlowRepository.findById(id).orElse(null);
         if(imgFlow!=null){
             imgFlowRepository.deleteById(id);
         }
    }
    @Transactional
    @Override
    public ImgFlow updateImg(Long id,ImgFlow imgFlow) {
        ImgFlow img = imgFlowRepository.findById(id).orElse(null);
        if(img==null){
            return imgFlowRepository.save(imgFlow);
        }else{
            BeanUtils.copyProperties(imgFlow,img);
            return imgFlowRepository.save(img);
        }
    }
    @Transactional
    @Override
    public List<ImgFlow> findAll() {
        return imgFlowRepository.findAll();
    }

    @Override
    public ImgFlow findById(Long id) {
        return imgFlowRepository.findById(id).orElse(null);
    }
}
