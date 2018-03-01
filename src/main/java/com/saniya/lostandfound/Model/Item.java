package com.saniya.lostandfound.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @NotNull
    private String category; //clothes, pets or other

    @Column
    @NotNull
    private String itemName;

    @Column
    private String description;

    @Column
    private String image;

    @Column
    private String status; //lost or found

    @ManyToMany(mappedBy = "items")
    private Set<User> users;

    public Item(String status) {
        this.users = new HashSet<>();
        this.status = status;
    }

    public Item(String category, String itemName) {
        this.category = category;
        this.itemName = itemName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void itemFound(){
        this.setStatus("Found");
    }

    public void itemLost(){
        this.setStatus("Lost");
    }

}
