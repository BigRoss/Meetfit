package com.elec5619.meetfit.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "location")
    private String location;

    @Column(name = "goalweight")
    private Float goalweight;

    @Column(name = "gender")
    private String gender;

    @Column(name = "bio")
    private String bio;

    @OneToOne
    @JoinColumn(unique = true)
    private User userprofile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHeight() {
        return height;
    }

    public Profile height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public Profile weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getLocation() {
        return location;
    }

    public Profile location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getGoalweight() {
        return goalweight;
    }

    public Profile goalweight(Float goalweight) {
        this.goalweight = goalweight;
        return this;
    }

    public void setGoalweight(Float goalweight) {
        this.goalweight = goalweight;
    }

    public String getGender() {
        return gender;
    }

    public Profile gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public Profile bio(String bio) {
        this.bio = bio;
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User getUserprofile() {
        return userprofile;
    }

    public Profile userprofile(User user) {
        this.userprofile = user;
        return this;
    }

    public void setUserprofile(User user) {
        this.userprofile = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profile profile = (Profile) o;
        if(profile.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + id +
            ", height='" + height + "'" +
            ", weight='" + weight + "'" +
            ", location='" + location + "'" +
            ", goalweight='" + goalweight + "'" +
            ", gender='" + gender + "'" +
            ", bio='" + bio + "'" +
            '}';
    }
}
