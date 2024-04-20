package org.example.Controllers;

import java.util.List;

import org.example.Models.Item;
import org.example.Services.ItemService;
import org.example.Services.ItemServiceImpl;
import org.example.Views.ItemView;

public class ItemController {
    public void displayItem(){
        ItemService i_serv = new ItemServiceImpl();
        List<Item> i_list = i_serv.getItemList();

        ItemView i_view = new ItemView();
        i_view.displayItemList(i_list);
    }

    public Item displayItemById(Long id){
        ItemService i_serv = new ItemServiceImpl();
        Item item = i_serv.getItemById(id);
        
        return item;
    }
}
