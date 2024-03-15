package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    static ArrayList<String> menu = new ArrayList<>();
    static ArrayList<Integer> hargaMenu = new ArrayList<>();
    static ArrayList<Integer> qty = new ArrayList<>();

    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu() {
        int pilih;
        String namaMenu = "";
        int harga = 0;
        int inputQty;
        do {
            System.out.println("=================================");
            System.out.println("Selamat datang di");
            System.out.println("=================================");
            System.out.println("Silakan pilih Makanan : ");
            System.out.println("1. Nasi Goreng \t\t | 15.000");
            System.out.println("2. Mie Goreng \t\t | 13.000");
            System.out.println("3. Nasi + Ayam \t\t | 18.000");
            System.out.println("4. Es teh manis \t | 3.000");
            System.out.println("5. Es Jeruk \t\t | 5.000");
            System.out.println("99. Pesan dan Bayar");
            System.out.println("0. Keluar aplikasi");
            System.out.print("=> ");
            pilih = input.nextInt();
            if (pilih >= 1 && pilih <= 5) {
                switch (pilih) {
                    case 1:
                        namaMenu = "Nasi Goreng";
                        harga = 15000;
                        break;
                    case 2:
                        namaMenu = "Mie Goreng";
                        harga = 13000;
                        break;
                    case 3:
                        namaMenu = "Nasi + Ayam";
                        harga = 18000;
                        break;
                    case 4:
                        namaMenu = "Es Teh Manis";
                        harga = 3000;
                        break;
                    case 5:
                        namaMenu = "Es Jeruk";
                        harga = 5000;
                        break;
                    case 99:
                        namaMenu = "Es Jeruk";
                        harga = 5000;
                        break;
                    case 0:
                        namaMenu = "Es Jeruk";
                        harga = 5000;
                        break;
                    default:
                        break;
                }
                System.out.println("============================");
                System.out.println("Berapa Pesanan Anda");
                System.out.println("============================");
                System.out.println(namaMenu + "\t | " + harga);
                System.out.println("(input 0 untuk kembali)");
                System.out.print("qty =>");
                inputQty = input.nextInt();
                if (inputQty > 0) {
                    if (menu.contains(namaMenu)) {
                        int indexMenu = menu.indexOf(namaMenu);
                        qty.set(indexMenu, (qty.get(indexMenu) + inputQty));
                    } else {
                        menu.add(namaMenu);
                        hargaMenu.add(harga);
                        qty.add(inputQty);
                    }
                }
            } else if (pilih == 99) {
                showStruk();
            }
        } while (pilih != 0);
    }

    public static void showStruk() {
        int total = 0;
        int subTotal = 0;
        StringBuilder nota = new StringBuilder();
        for (int i = 0; i < menu.size(); i++) {
            subTotal = hargaMenu.get(i) * qty.get(i);
            total += subTotal;
            nota.append(menu.get(i)).append(" qty : ").append(qty.get(i)).append(" total : ").append(subTotal).append("\n");
        }
        nota.append("total : ").append(total);
        cetakNota(nota.toString());
    }

    public static void cetakNota(String nota){
        String PATH = "E:/Kumpulankoding/java/Synrgy-challenge/F-BJV24001115-synergy7-ind-binfood-ch1/test1.txt";
        File file = new File(PATH);
        try {
            if (file.createNewFile()) {
                System.out.println("File Created");
                FileWriter fileWriter = new FileWriter(PATH);
                fileWriter.write(nota);
                fileWriter.close();
                System.out.println("Berhasil dicetak");
            }else{
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("Terjadi error");
            throw new RuntimeException(e);
        }
    }
}