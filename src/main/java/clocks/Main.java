package clocks;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Clock clock = new SimpleClock();
        Scanner scan = new Scanner(System.in);

        try {
            System.out.println(" Enter hours: ");
            clock.setHour(scan.nextInt());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(" Enter minutes: ");
            clock.setMinute(scan.nextInt());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(" Enter cost $: ");
        clock.setCost(scan.nextInt());

        System.out.println(" Enter brand: ");
        clock.setWatchBrand(scan.next());

        System.out.println("Complete information about clock:");
        System.out.println("$" + clock.getCost());
        System.out.println("Brand " + clock.getWatchBrand());
        System.out.println("Time " + clock.getHour() + ":" + clock.getMinute());

        try {
            System.out.println(" Enter time to change: ");
            ((SimpleClock) clock).changeTime(scan.nextInt(), scan.nextInt());
            System.out.println("Time " + clock.getHour() + ":" + clock.getMinute());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        ClockWithSeconds clockWithSeconds = new ClockWithSeconds();

        System.out.println("CLOCK WITH SECONDS");
        try {
            System.out.println(" Enter hours: ");
            clockWithSeconds.setHour(scan.nextInt());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(" Enter minutes: ");
            clockWithSeconds.setMinute(scan.nextInt());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(" Enter seconds: ");
            clockWithSeconds.setSeconds(scan.nextInt());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(" Enter cost $: ");
        clockWithSeconds.setCost(scan.nextInt());

        System.out.println(" Enter brand: ");
        clockWithSeconds.setWatchBrand(scan.next());

        System.out.println("Complete information about clock:");
        System.out.println("$" + clockWithSeconds.getCost());
        System.out.println("Brand " + clockWithSeconds.getWatchBrand());
        System.out.println("Time " + clockWithSeconds.getHour() + ":"
                + clockWithSeconds.getMinute() + ":" + clockWithSeconds.getSeconds());

        try {
            System.out.println(" Enter time to change: ");
            clockWithSeconds.changeTime(scan.nextInt(), scan.nextInt(), scan.nextInt());
            System.out.println("Time " + clockWithSeconds.getHour() + ":"
                    + clockWithSeconds.getMinute() + ":" + clockWithSeconds.getSeconds());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
