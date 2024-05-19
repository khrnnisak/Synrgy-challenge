package com.example.FBJV24001115synergy7indbinfoodch4.views;

import java.util.List;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;

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
