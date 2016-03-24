package org.lucifer.abchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "BOT_STRATEGY")
public class BotStrategy extends Identificator implements Serializable {
    @Column(name = "NAME")
    private String name;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "strategy", cascade = CascadeType.ALL)
    private Set<StdMessage> messages;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "strategy")
    private Set<Bot> bots;

    @Column(name = "SILENCE_PROB")
    private Double silenceProb;

    @Column(name = "MSG_LIMIT")
    private Long msgLimit;

    @Column(name = "ERROR_PROB")
    private Double errorProb;

    @Column(name = "TANIMOTO_THRESHOLD")
    private Double tanimotoThreshold;

    @Column(name = "CROWD_RAND")
    private Double crowdRand;

    @Column(name = "INITIATIVE")
    private Double initiative;

    @Column(name = "REPEAT_W")
    private Boolean repeat;

    @Column(name = "UNIQUE_W")
    private Long unique;

    @Column(name = "SEMANTIC")
    private Boolean semantic;

    @Column(name = "CROWD_SOURCE")
    private Boolean crowdSource;

    public BotStrategy() {

    }

    public Boolean getRepeat() {
        return repeat;
    }

    public void setRepeat(Boolean repeat) {
        this.repeat = repeat;
    }

    public Long getUnique() {
        return unique;
    }

    public void setUnique(Long unique) {
        this.unique = unique;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Bot> getBots() {
        return bots;
    }

    public void setBots(Set<Bot> bots) {
        this.bots = bots;
    }

    public Set<StdMessage> getMessages() {
        return messages;
    }

    public void setMessages(Set<StdMessage> messages) {
        this.messages = messages;
    }

    public Double getSilenceProb() {
        return silenceProb;
    }

    public void setSilenceProb(Double silenceProb) {
        this.silenceProb = silenceProb;
    }

    public Long getMsgLimit() {
        return msgLimit;
    }

    public void setMsgLimit(Long msgLimit) {
        this.msgLimit = msgLimit;
    }

    public Double getErrorProb() {
        return errorProb;
    }

    public void setErrorProb(Double errorProb) {
        this.errorProb = errorProb;
    }

    public Double getTanimotoThreshold() {
        return tanimotoThreshold;
    }

    public void setTanimotoThreshold(Double tanimotoThreshold) {
        this.tanimotoThreshold = tanimotoThreshold;
    }

    public Double getCrowdRand() {
        return crowdRand;
    }

    public void setCrowdRand(Double crowdRand) {
        this.crowdRand = crowdRand;
    }

    public Boolean getSemantic() {
        return semantic;
    }

    public void setSemantic(Boolean semantic) {
        this.semantic = semantic;
    }

    public Boolean getCrowdSource() {
        return crowdSource;
    }

    public void setCrowdSource(Boolean crowdSource) {
        this.crowdSource = crowdSource;
    }

    public Double getInitiative() {
        return initiative;
    }

    public void setInitiative(Double initiative) {
        this.initiative = initiative;
    }
}
