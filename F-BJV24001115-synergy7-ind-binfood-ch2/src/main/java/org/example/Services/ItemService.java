package org.example.Services;

import java.util.List;
import java.util.Map;

import org.example.Models.Item;

public interface ItemService {
    List<Item> getItemList();
    Item getItemById(Long id);

    void create(Item item);
    void delete(Long id);
    void update(Long id, Item item);
}
