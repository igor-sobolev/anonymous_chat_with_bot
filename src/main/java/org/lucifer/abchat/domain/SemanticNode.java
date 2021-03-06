package org.lucifer.abchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "SEMANTIC_NODE")
public class SemanticNode extends Identificator implements Serializable {
    @Column(name = "VALUE")
    private String value;

    @Column(name = "PEAK")
    private Boolean peak;

    @ManyToOne(cascade = CascadeType.ALL)
    private SemanticNetwork network;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "node")
    private Set<SemanticLink> links;

    @OneToOne(mappedBy = "linkedNode")
    private SemanticLink link;

    public SemanticNode() {
        peak = false;
    }

    public Boolean getPeak() {
        return peak;
    }

    public void setPeak(Boolean peak) {
        this.peak = peak;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SemanticNetwork getNetwork() {
        return network;
    }

    public void setNetwork(SemanticNetwork network) {
        this.network = network;
    }

    public Set<SemanticLink> getLinks() {
        return links;
    }

    public void setLinks(Set<SemanticLink> links) {
        this.links = links;
    }

    public SemanticLink getLink() {
        return link;
    }

    public void setLink(SemanticLink link) {
        this.link = link;
    }

    public SemanticNode getLinkedNode(String link) {
        for (SemanticLink sl : links) {
            if (sl.getValue().equals(link)) {
                return sl.getLinkedNode();
            }
        }
        return null;
    }
}
