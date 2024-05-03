package org.example.Views;

import java.util.List;

import org.example.Models.Item;

public class ItemView {

    public static void displayItemList(List<Item> items){
        displayHeader();
        items.forEach(item -> displayItem(item));

    }
    public static void displayItem(Item item){
        System.out.println(item.getId()+" | "+item.getName()+" | "+item.getPrice());
    }
    public static void displayHeader(){
        System.out.println("ID      |   NAMA    |   HARGA");
        System.out.println("=".repeat(35));
    }
}
