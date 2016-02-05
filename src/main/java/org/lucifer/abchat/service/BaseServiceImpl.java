package org.lucifer.abchat.service;

import org.lucifer.abchat.dao.BaseDao;
import org.lucifer.abchat.domain.Identificator;
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

    public List<Entity> getList(Long page) {
        return dao.getList(page);
    }

    public Entity delete(Long id) {
        return dao.delete(id);
    }

    public Entity findById(Long id) {
        return dao.findById(id);
    }
}
