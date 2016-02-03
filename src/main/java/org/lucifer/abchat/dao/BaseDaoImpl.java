package org.lucifer.abchat.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.lucifer.abchat.domain.Identificator;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseDaoImpl<Entity extends Identificator> implements BaseDao<Entity>{

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public Entity save(Entity entity) {
        getSession().saveOrUpdate(entity);
        return entity;
    }

    public Entity delete(Long id) {
        Entity entity = findById(id);
        getSession().delete(entity);
        return entity;
    }

    public Entity findById(Long id){
        Entity entity = (Entity) getSession().get(getType(), id);
        return entity;
    }

    public List<Entity> getAll() {
        Session session = getSession();
        Query query = session.createQuery("FROM " + getType().getSimpleName());
        return  query.list();
    }

    public List<Entity> getList(Long page) {
        Session session = getSession();
        Query query = session.createQuery("FROM " + getType().getSimpleName());
        final long limitResultsPerPage = 10;
        query.setFirstResult((int)(page * limitResultsPerPage));
        query.setMaxResults((int)limitResultsPerPage);
        return (List<Entity>) query.list();
    }

    protected Class getType(){
        return getGenericParameterClass(this.getClass(),0);
    }
    protected Class getGenericParameterClass(Class actualClass, int parameterIndex){
        return (Class) ((ParameterizedType) actualClass.getGenericSuperclass()).getActualTypeArguments()[parameterIndex];
    }
}