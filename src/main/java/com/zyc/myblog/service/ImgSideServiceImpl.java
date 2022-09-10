package com.zyc.myblog.service;

import com.zyc.myblog.dao.ImgSideRepository;
import com.zyc.myblog.po.ImgSide;
import com.zyc.myblog.util.MyBeanUtils;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ImgSideServiceImpl implements ImgSideService{
    private ImgSideRepository imgSideRepository;
    @Autowired
    public void setImgSideRepository(ImgSideRepository imgSideRepository) {
        this.imgSideRepository = imgSideRepository;
    }

    @Override
    public ImgSide saveImg(ImgSide imgSide) {
        imgSide.setCreateTime(new Date());
        imgSide.setUpdateTime(new Date());
        return imgSideRepository.save(imgSide);
    }

    @Override
    public void deleteImgById(Long id) {
         imgSideRepository.deleteById(id);
    }

    @Override
    public ImgSide updateImg(Long id, ImgSide imgSide) throws NotFoundException {
        ImgSide imgSide1 = imgSideRepository.findById(id).orElse(null);
        if(imgSide1==null){
            throw new NotFoundException("该侧边图片不存在");
        }
        imgSide.setUpdateTime(new Date());
        BeanUtils.copyProperties(imgSide,imgSide1, MyBeanUtils.getNullPropertyNames(imgSide));
        return imgSideRepository.save(imgSide1);
    }


    @Override
    public List<ImgSide> findAll() {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        return imgSideRepository.findAll(sort);
    }

    @Override
    public ImgSide findById(Long id) throws NotFoundException {
        ImgSide imgSide = imgSideRepository.findById(id).orElse(null);
        if(imgSide==null){
            throw new NotFoundException("该id不存在图片");
        }
        return imgSide;
    }
}
