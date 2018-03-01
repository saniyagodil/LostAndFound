package com.saniya.lostandfound.Repositories;

import com.saniya.lostandfound.Model.Item;
import com.saniya.lostandfound.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ItemRepository extends CrudRepository<Item, Long> {
    Iterable<Item> findByStatus(String status);

    Iterable<Item> findByUsers(Set<User> users);

    Item findById(long id);

    Iterable<Item> findByItemNameContains(String query);

    Iterable<Item> findByCategory(String query);
}
