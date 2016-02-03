package org.lucifer.abchat.dao;

import org.lucifer.abchat.domain.Identificator;

import java.util.List;


public interface BaseDao <Entity extends Identificator>{

    public Entity save(Entity entity);

    public Entity delete(Long id);

    public List<Entity> getAll();

    public List<Entity> getList(Long page);

    public Entity findById(Long id);
}
