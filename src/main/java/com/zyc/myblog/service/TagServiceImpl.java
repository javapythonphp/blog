package com.zyc.myblog.service;

import com.zyc.myblog.dao.TagRepository;
import com.zyc.myblog.po.Tag;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;
    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    @Override
    public Tag saveType(Tag tag) {
        return tagRepository.save(tag);
    }
    @Transactional
    @Override
    public Tag getType(Long id) {
        return tagRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.getTagByName(name);
    }
    @Transactional
    @Override
    public Page<Tag> ListTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }
    @Transactional
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }
    @Transactional
    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids)&&ids!=null){
            String[] idarray = ids.split(",");
            for (int i = 0; i < idarray.length; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) throws NotFoundException {
        Tag t = tagRepository.findById(id).orElse(null);
        if(t==null){
            throw new NotFoundException("无该标签");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        tagRepository.deleteById(id);
    }
}
