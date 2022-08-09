package com.capstone.caboodle.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;

    @OneToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Listing> listings;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "profiles_categories",
            joinColumns = {@JoinColumn(name = "profile_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")}
    )
    private Set<Category> categories = new HashSet<>();

    public Profile() {
    }

    public Profile(Long id, String name, Integer age, User user, Set<Listing> listings, Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.user = user;
        this.listings = listings;
        this.categories = categories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Listing> getListings() {
        return listings;
    }

    public void setListings(Set<Listing> listings) {
        this.listings = listings;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
