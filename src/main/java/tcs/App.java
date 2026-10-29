package tcs;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello good World!" );
    }
}
package com.example.sonartest;

public class OrderService {

    public void processOrder() {
        String customer = "test";
        String product = "laptop";
        int quantity = 10;
        double price = 1000.0;

        System.out.println("Customer: " + customer);
        System.out.println("Product: " + product);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price: " + price);

        double total = quantity * price;
        double tax = total * 0.18;
        double finalAmount = total + tax;

        System.out.println("Total: " + total);
        System.out.println("Tax: " + tax);
        System.out.println("Final Amount: " + finalAmount);
    }
}
