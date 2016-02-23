package org.lucifer.abchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "BOT")
@SuppressWarnings("JpaAttributeTypeInspection")
public class Bot extends Identificator implements Serializable {
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "bot", cascade = CascadeType.ALL)
    private Cospeaker cospeaker;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bot")
    private Set<SemanticNetwork> networks;

    public  Bot() {

    }

    public Cospeaker getCospeaker() {
        return cospeaker;
    }

    public void setCospeaker(Cospeaker cospeaker) {
        this.cospeaker = cospeaker;
    }

    public Set<SemanticNetwork> getNetworks() {
        return networks;
    }

    public void setNetworks(Set<SemanticNetwork> networks) {
        this.networks = networks;
    }
}
