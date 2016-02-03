package org.lucifer.abchat.service;


import org.lucifer.abchat.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@Transactional
public class PersonServiceImpl extends BaseServiceImpl<User> implements PersonService{

}
