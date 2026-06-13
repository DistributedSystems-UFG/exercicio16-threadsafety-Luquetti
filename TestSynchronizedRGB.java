public class TestSynchronizedRGB {
    public static void main(String[] args) throws InterruptedException {
        SynchronizedRGB color = new SynchronizedRGB(0, 0, 0, "Preto");

        Runnable writer = () -> {
            for (int i = 0; i < 100000; i++) {
                if (i % 2 == 0) {
                    color.set(255, 0, 0, "Vermelho");
                } else {
                    color.set(0, 255, 0, "Verde");
                }
            }
        };

        Runnable reader = () -> {
            for (int i = 0; i < 100000; i++) {
                int rgb = color.getRGB();
                String name = color.getName();
                if (rgb == 0xFF0000 && !name.equals("Vermelho")) {
                    System.out.println("INCONSISTENCIA: " + name + " com RGB=" + Integer.toHexString(rgb));
                }
                if (rgb == 0x00FF00 && !name.equals("Verde")) {
                    System.out.println("INCONSISTENCIA: " + name + " com RGB=" + Integer.toHexString(rgb));
                }
            }
        };

        Thread t1 = new Thread(writer);
        Thread t2 = new Thread(reader);
        Thread t3 = new Thread(reader);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Teste finalizado. Estado final: " + color.getName());
    }
}