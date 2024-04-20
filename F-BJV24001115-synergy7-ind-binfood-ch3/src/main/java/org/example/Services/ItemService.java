package org.example.Services;

import java.util.List;
import java.util.Map;

import org.example.Models.Item;

public interface ItemService {
    List<Item> getItemList();
    Item getItemById(Long id);

    String create(Item item);
    String delete(Long id);
    String update(Long id, Item item);
}
