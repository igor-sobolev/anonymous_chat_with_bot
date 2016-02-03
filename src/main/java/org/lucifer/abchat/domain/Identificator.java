package org.lucifer.abchat.domain;

import javax.persistence.*;

/**
 * Created by ����� on 16.06.2015.
 */

@MappedSuperclass
public abstract class Identificator {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    protected Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
