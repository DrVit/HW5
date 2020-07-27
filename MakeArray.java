public class MakeArray {
    static final int size = 10000000;
    static final int half = size / 2;


    public static void main(String[] args) {
        long time = MakeSimpleArray();
        System.out.println("Времени потрачено в 1-м случае " + (System.currentTimeMillis() - time));
        long time1 = MakeTwoThread();
        System.out.println("Времени потрачено во 2-м случае " + (System.currentTimeMillis() - time1));

    }

    private static long MakeTwoThread() {
        float[] arr = new float[size];
        float[] a1 = new float[half];
        float[] a2 = new float[half];

        long time1 = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }


        System.arraycopy(arr, 0, a1, 0, half);
        System.arraycopy(arr, half, a2, 0, half);

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " starts ");
            for (int i = 0; i < half; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4 + i / 2));
            }
            System.out.println("Закончилось выполнение " + Thread.currentThread().getName());
        });
        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " starts ");
            for (int i = 0; i < half; i++) {
                int h = i + half;
                a2[i] = (float) (a2[i] * Math.sin(0.2f + h / 5) * Math.cos(0.2f + h / 5) * Math.cos(0.4 + h / 2));
            }
            System.out.println("Закончилось выполнение " + Thread.currentThread().getName());
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, half);
        System.arraycopy(a2, 0, arr, half, half);

        return time1;

    }

    private static long MakeSimpleArray() {
        float[] arr = new float[size];
        long time = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }

        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4 + i / 2));
        }

        return time;
    }

}
