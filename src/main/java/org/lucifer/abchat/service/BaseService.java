package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.Identificator;

import java.util.List;

public interface BaseService<Entity extends Identificator> {
    Entity save(Entity entity);

    Entity delete(Long id);

    List<Entity> getAll();

    List<Entity> getPage(Long page);

    Entity findById(Long id);

    Long count();

    Long countPages();
}
