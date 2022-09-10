package com.zyc.myblog.service;

import com.zyc.myblog.dao.ColumnRepository;
import com.zyc.myblog.po.Blog;
import com.zyc.myblog.po.Column;
import com.zyc.myblog.po.Type;
import com.zyc.myblog.util.MyBeanUtils;
import com.zyc.myblog.vo.BlogQuery;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ColumnServiceImpl implements ColumnService{
    private ColumnRepository columnRepository;
    @Autowired
    public void setColumnRepository(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    @Override
    public Column saveColumn(Column column) {
        if(column.getId()==null){
           column.setCreateTime(new Date());
           return columnRepository.save(column);
        }
        return null;
    }

    @Override
    public void deleteColumn(Long id) {
        columnRepository.deleteById(id);
    }

    @Override
    public Column updateColumn(Long id, Column column) throws NotFoundException {
        Column column1 = columnRepository.findById(id).orElse(null);
        if(column1==null){
            throw new NotFoundException("该专栏不存在");
        }
        BeanUtils.copyProperties(column,column1, MyBeanUtils.getNullPropertyNames(column));
        return columnRepository.save(column1);
    }

    @Override
    public Column findColumnById(Long id) {
        return columnRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Column> listColumn(Pageable pageable) {
        return columnRepository.findAll(pageable);
    }

    @Override
    public List<Column> listColumn() {
        return columnRepository.findAll();
    }

    @Override
    public List<Column> listTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = new PageRequest(0,size,sort);
        return columnRepository.listTop(pageable);
    }

    @Override
    public Page<Column> listColumnByTitle(String title, Pageable pageable) {
        return columnRepository.findByTitle(title,pageable);
    }

    @Override
    public List<Column> findAllByTitle(String title) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        return columnRepository.findByTitle(title,sort);
    }


}
