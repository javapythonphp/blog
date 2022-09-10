package com.zyc.myblog.service;

import com.zyc.myblog.po.Column;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ColumnService {

    Column saveColumn(Column column);

    void deleteColumn(Long id);

    Column updateColumn(Long id,Column column) throws NotFoundException;

    Column findColumnById(Long id);

    Page<Column> listColumn(Pageable pageable);

    List<Column> listColumn();

    List<Column> listTop(Integer size);

    Page<Column> listColumnByTitle(String title,Pageable pageable);

    List<Column> findAllByTitle(String title);

}
