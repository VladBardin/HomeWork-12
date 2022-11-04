package Task2;

import java.util.function.IntConsumer;

public class TaskTwo {
    public static void main(String[] args) {
        final int input = 15;
        TaskTwo fizzBuzz = new TaskTwo(input);
        Thread threadBuzz = new Thread(() -> {
            try {
                fizzBuzz.buzz(() -> System.out.println("Buzz"));
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread threadFizz = new Thread(() -> {
            try {
                fizzBuzz.fizz(() -> System.out.println("Fizz"));
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread threadFizzBuzz = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(() -> System.out.println("FizzBuzz"));
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread threadNumber = new Thread(() -> {
            try {
                fizzBuzz.number(System.out::println);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        threadFizz.start();
        threadBuzz.start();
        threadFizzBuzz.start();
        threadNumber.start();
    }
    private final int n;
    private int count;
    public TaskTwo(int n) {
        this.n = n;
        this.count = 1;
    }
    synchronized public void fizz(Runnable printFizz) throws InterruptedException {
        while (count <= n) {
            if (count % 3 == 0 && count % 5 != 0) {
                count++;
                printFizz.run();
                notifyAll();
            }
            else {
                wait();
            }
        }
    }
    synchronized public void buzz(Runnable printBuzz) throws InterruptedException {
        while (count <= n) {
            if (count % 5 == 0 && count % 3 != 0) {
                printBuzz.run();
                count++;
                notifyAll();
            }
            else {
                wait();
            }
        }
    }
    synchronized public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (count <= n) {
            if (count % 5 == 0 && count % 3 == 0) {
                printFizzBuzz.run();
                count++;
                notifyAll();
            }
            else {
                wait();
            }
        }
    }
    synchronized public void number(IntConsumer printNumber) throws InterruptedException {
        while (count <= n) {
            if (count % 3 == 0 || count % 5 == 0) {
                wait();
            }
            else {
                printNumber.accept(count);
                count++;
                notifyAll();
            }
        }
    }
}
