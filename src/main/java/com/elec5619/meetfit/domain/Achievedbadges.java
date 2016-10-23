package com.elec5619.meetfit.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Achievedbadges.
 */
@Entity
@Table(name = "achievedbadges")
public class Achievedbadges implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "points")
    private Integer points;

    @ManyToOne
    private Badges badges;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public Achievedbadges points(Integer points) {
        this.points = points;
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Badges getBadges() {
        return badges;
    }

    public Achievedbadges badges(Badges badges) {
        this.badges = badges;
        return this;
    }

    public void setBadges(Badges badges) {
        this.badges = badges;
    }

    public User getUser() {
        return user;
    }

    public Achievedbadges user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Achievedbadges achievedbadges = (Achievedbadges) o;
        if(achievedbadges.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, achievedbadges.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Achievedbadges{" +
            "id=" + id +
            ", points='" + points + "'" +
            '}';
    }
}
