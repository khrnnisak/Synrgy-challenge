package org.example.Services;

import java.util.List;
import java.util.Optional;

import org.example.Data;
import org.example.Models.Item;
import org.example.Utils.FormatMessageUtil;

public class ItemServiceImpl implements ItemService {

    @Override
    public String create(Item item) {
        try {
            Optional<Item> opt_item = Optional.ofNullable(item);
            if (!opt_item.isPresent()) {
                return FormatMessageUtil.errorMessageFormat("Kesalahan saat menambahkan Item");
            }
            Item getItem = getItemById(item.getId());
            if (getItem != null) {
                return FormatMessageUtil.duplicateMessage();
            } else {
                Data.items.add(item);
                return FormatMessageUtil.succesToAddMessage();

            }
        } catch (Exception e) {
            return FormatMessageUtil.failedToAddMessage() + e;
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
    public String delete(Long id) {
        try {
            if (getItemById(id) == null) {
                return FormatMessageUtil.notFoundMessage();
            } else {
                int index = getItemList().indexOf(getItemById(id));
                Data.items.remove(index);
                return FormatMessageUtil.succesToDeleteMessage();
            }
        } catch (Exception e) {
            return FormatMessageUtil.failedToDeleteMessage() + e;
        }
    }

    @Override
    public String update(Long id, Item item) {
        try {
            if (getItemById(id) == null) {
                return FormatMessageUtil.notFoundMessage();
            } else {

                int index = getItemList().indexOf(getItemById(id));
                Data.items.set(index, item);
                return FormatMessageUtil.succesToEditMessage();

            }
        } catch (Exception e) {
            return FormatMessageUtil.failedToEditMessage() + e;
        }
    }

}
