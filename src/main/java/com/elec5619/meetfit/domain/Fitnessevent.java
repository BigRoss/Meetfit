package com.elec5619.meetfit.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Fitnessevent.
 */
@Entity
@Table(name = "fitnessevent")
public class Fitnessevent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "starttime", nullable = false)
    private ZonedDateTime starttime;

    @NotNull
    @Column(name = "endtime", nullable = false)
    private ZonedDateTime endtime;

    @ManyToOne
    @NotNull
    private User organiser;

    @ManyToMany
    @JoinTable(name = "fitnessevent_attending",
               joinColumns = @JoinColumn(name="fitnessevents_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="attendings_id", referencedColumnName="ID"))
    private Set<User> attendings = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Fitnessevent name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Fitnessevent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public Fitnessevent location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ZonedDateTime getStarttime() {
        return starttime;
    }

    public Fitnessevent starttime(ZonedDateTime starttime) {
        this.starttime = starttime;
        return this;
    }

    public void setStarttime(ZonedDateTime starttime) {
        this.starttime = starttime;
    }

    public ZonedDateTime getEndtime() {
        return endtime;
    }

    public Fitnessevent endtime(ZonedDateTime endtime) {
        this.endtime = endtime;
        return this;
    }

    public void setEndtime(ZonedDateTime endtime) {
        this.endtime = endtime;
    }

    public User getOrganiser() {
        return organiser;
    }

    public Fitnessevent organiser(User user) {
        this.organiser = user;
        return this;
    }

    public void setOrganiser(User user) {
        this.organiser = user;
    }

    public Set<User> getAttendings() {
        return attendings;
    }

    public Fitnessevent attendings(Set<User> users) {
        this.attendings = users;
        return this;
    }

    public Fitnessevent addAttending(User user) {
        attendings.add(user);
        return this;
    }

    public Fitnessevent removeAttending(User user) {
        attendings.remove(user);
        return this;
    }

    public void setAttendings(Set<User> users) {
        this.attendings = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fitnessevent fitnessevent = (Fitnessevent) o;
        if(fitnessevent.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fitnessevent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fitnessevent{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", location='" + location + "'" +
            ", starttime='" + starttime + "'" +
            ", endtime='" + endtime + "'" +
            '}';
    }
}
