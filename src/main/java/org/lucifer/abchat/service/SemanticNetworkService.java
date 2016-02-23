package org.lucifer.abchat.service;

import org.lucifer.abchat.domain.SemanticNetwork;
import org.lucifer.abchat.dto.MessageDTO;

public interface SemanticNetworkService extends BaseService<SemanticNetwork> {

    void createSN(MessageDTO msg);
}
