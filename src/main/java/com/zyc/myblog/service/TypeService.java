package com.zyc.myblog.service;

import com.zyc.myblog.dao.TypeRepository;
import com.zyc.myblog.po.Type;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> ListType(Pageable pageable);

    Type updateType(Long id,Type type) throws NotFoundException;

    void deleteType(Long id);

    Type findByName(String name);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);
}
