package com.rainyalley.practice.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sllx on 8/20/15.
 */
public class Assembly {

    private List<Product> products = Arrays.asList(new Product(3, "C"), new Product(2, "E"));

    public static void main(String[] args) {
        new Assembly().count(20).forEach(s -> System.out.println(s));
    }

    private int firstPrice(int enableKinds) {
        return products.get(enableKinds - 1).price;
    }

    private String firstName(int enableKinds) {
        return products.get(enableKinds - 1).name;
    }

    private int totalKinds() {
        return products.size();
    }

    private List<String> count(int amount, int enableKinds) {
        if (amount == 0) {
            return new ArrayList<>();
        } else if (amount < 0) {
            return null;
        } else if (enableKinds == 0) {
            return null;
        } else {
            List<String> pre = count(amount, enableKinds - 1);
            List<String> nex = count(amount - firstPrice(enableKinds), enableKinds);

            if (pre == null && nex == null) {
                return null;
            }

            if (nex != null) {
                String name = firstName(enableKinds);
                if (nex.size() == 0) {
                    nex.add(name);
                } else {
                    nex = nex.stream().map(s -> name + s).collect(Collectors.toList());
                }
            }

            if (pre != null && nex != null) {
                pre.addAll(nex);
                return pre;
            } else if (pre == null) {
                return nex;
            } else {
                return pre;
            }
        }
    }


    public List<String> count(int amout) {
        return count(amout, totalKinds());
    }

    private static class Product {
        private int price;
        private String name;

        private Product(int price, String name) {
            this.price = price;
            this.name = name;
        }
    }
}