package org.lucifer.abchat.dao;

import org.lucifer.abchat.domain.Identificator;

import java.util.List;

public interface BaseDao<Entity extends Identificator> {

    Entity save(Entity entity);

    Entity delete(Long id);

    List<Entity> getAll();

    List<Entity> getPage(Long page);

    Long count();

    Entity findById(Long id);

    Long countPages();
}
