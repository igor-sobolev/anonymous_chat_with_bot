package org.lucifer.abchat.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ����� on 08.06.2015.
 */

@Entity
@Table(name = "USER")
public class User extends Identificator implements Serializable{

	@Column(name = "NAME")
	private String name;

	public User(String name, String surname){
		this.name = name;
	}

	public User(){

	}

	//TODO equals and hashcode
}
