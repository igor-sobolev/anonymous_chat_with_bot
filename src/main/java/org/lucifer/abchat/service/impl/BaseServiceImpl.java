package org.lucifer.abchat.service.impl;

import org.lucifer.abchat.dao.BaseDao;
import org.lucifer.abchat.domain.Identificator;
import org.lucifer.abchat.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public abstract class BaseServiceImpl<Entity extends Identificator> implements BaseService<Entity> {
    @Autowired
    protected BaseDao<Entity> dao;

    public Entity save(Entity entity) {
        return dao.save(entity);
    }

    public List<Entity> getAll() {
        return dao.getAll();
    }

    public List<Entity> getPage(Long page) {
        return dao.getPage(page);
    }

    public Entity delete(Long id) {
        return dao.delete(id);
    }

    public Entity findById(Long id) {
        return dao.findById(id);
    }

    public Long count() {
        return dao.count();
    }

    @Override
    public Long countPages() {
        return dao.countPages();
    }
}
