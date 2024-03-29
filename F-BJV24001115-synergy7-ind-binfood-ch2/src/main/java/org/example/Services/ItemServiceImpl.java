package org.example.Services;

import java.util.List;

import javax.naming.NameNotFoundException;

import org.example.Data;
import org.example.Models.Item;

public class ItemServiceImpl implements ItemService{

    @Override
    public void create(Item item) {
        Data.items.add(item);
    }
    @Override
    public List<Item> getItemList() {
        return Data.items;
    }

    @Override
    public Item getItemById(Long id) {
        for(Item item : getItemList()){
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        int index = getItemList().indexOf(getItemById(id));
        Data.items.remove(index);
    }

    @Override
    public void update(Long id, Item item) {
        int index = getItemList().indexOf(getItemById(id));
        Data.items.set(index, item);
    }

}
