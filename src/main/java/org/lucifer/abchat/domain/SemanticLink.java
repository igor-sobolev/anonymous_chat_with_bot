package org.lucifer.abchat.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SEMANTIC_LINK")
public class SemanticLink extends Identificator implements Serializable {
    @Column(name = "VALUE")
    private String value;

    @ManyToOne(cascade = CascadeType.ALL)
    private SemanticNode node;

    @OneToOne(cascade = CascadeType.ALL)
    private SemanticNode linkedNode;

    public SemanticLink() {

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SemanticNode getNode() {
        return node;
    }

    public void setNode(SemanticNode node) {
        this.node = node;
    }

    public SemanticNode getLinkedNode() {
        return linkedNode;
    }

    public void setLinkedNode(SemanticNode linkedNode) {
        this.linkedNode = linkedNode;
    }
}
