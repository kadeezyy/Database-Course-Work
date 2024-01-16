import java.util.Random;

public class Main {
    public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
    public static void main(String[] args) {
        int pred1 = rnd(1, 3);
        int pred2 = rnd(4, 7);
        int correct = rnd(1, 2);
        int cycles;

        System.out.println("Pervoe ditya stavit na " + pred1 + " circles");
        System.out.println("drugoye ditya stavit na " + pred2 + " circles");
        if (correct == 1) {
            cycles = pred1;
        } else {
            cycles = pred2;
        }
        System.out.println(cycles + " cycles");
        if (correct == 1) {
            System.out.println("Perviy zasharil");
        } else {
            System.out.println("Vtoroy normis pobeda obeda");
        }
    }
}
