package org.example.Services;

import java.util.List;

import org.example.Data;
import org.example.Models.Item;
import org.example.Utils.FormatMessageUtil;

public class ItemServiceImpl implements ItemService {

    @Override
    public void create(Item item) {
        try {
            if (Data.items.contains(item)) {
                FormatMessageUtil.duplicateMessage();
            } else {
                Data.items.add(item);
                FormatMessageUtil.succesToAddMessage();

            }
        } catch (Exception e) {
            FormatMessageUtil.failedToAddMessage();
            System.out.println(e);
        }
    }

    @Override
    public List<Item> getItemList() {
        return Data.items;
    }

    @Override
    public Item getItemById(Long id) {
        for (Item item : getItemList()) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        try {
            if (getItemById(id) == null) {
                FormatMessageUtil.notFoundMessage();
            } else {
                int index = getItemList().indexOf(getItemById(id));
                Data.items.remove(index);
                FormatMessageUtil.succesToDeleteMessage();
            }
        } catch (Exception e) {
            FormatMessageUtil.failedToDeleteMessage();
            System.out.println(e);
        }
    }

    @Override
    public void update(Long id, Item item) {
        try {
            if (getItemById(id) == null) {
                FormatMessageUtil.notFoundMessage();
            } else {

                int index = getItemList().indexOf(getItemById(id));
                Data.items.set(index, item);
                FormatMessageUtil.succesToEditMessage();

            }
        } catch (Exception e) {
            FormatMessageUtil.failedToEditMessage();
            System.out.println(e);
        }
    }

}
