package org.lucifer.abchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "SEMANTIC_NETWORK")
public class SemanticNetwork extends Identificator implements Serializable{
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "network")
    private Set<SemanticNode> nodes;

    @ManyToOne(cascade = CascadeType.ALL)
    private Bot bot;

    public SemanticNetwork() {

    }

    public SemanticNetwork(Bot bot) {
        this.bot = bot;
    }

    public Set<SemanticNode> getNodes() {
        return nodes;
    }

    public void setNodes(Set<SemanticNode> nodes) {
        this.nodes = nodes;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
