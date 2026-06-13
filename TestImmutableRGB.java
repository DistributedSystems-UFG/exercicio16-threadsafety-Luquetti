public class TestImmutableRGB {
    public static void main(String[] args) throws InterruptedException {
        final ImmutableRGB original = new ImmutableRGB(0, 0, 0, "Preto");

        Runnable task = () -> {
            for (int i = 0; i < 100000; i++) {
                ImmutableRGB inverted = original.invert();

                // O objeto original NUNCA muda, então isso sempre é verdade
                if (original.getRGB() != 0x000000 || !original.getName().equals("Preto")) {
                    System.out.println("INCONSISTENCIA detectada no original!");
                }
                if (inverted.getRGB() != 0xFFFFFF || !inverted.getName().equals("Inverse of Preto")) {
                    System.out.println("INCONSISTENCIA no objeto invertido!");
                }
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Teste finalizado. Original permanece: " + original.getName()
                + " RGB=" + Integer.toHexString(original.getRGB()));
    }
}