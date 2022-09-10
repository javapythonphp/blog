package com.zyc.myblog.service;

import com.zyc.myblog.po.Tag;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveType(Tag tag);

    Tag getType(Long id);

    Tag getTagByName(String name);

    Page<Tag> ListTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    Tag updateTag(Long id,Tag tag) throws NotFoundException;

    List<Tag> listTagTop(Integer size);

    void deleteType(Long id);
}
