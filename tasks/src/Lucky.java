public class Lucky {
    public static class StateObject {
        private int x = 0;
        private int count = 0;

        public synchronized int getAndIncrementX() {
            return ++x;
        }

        public synchronized void incCount() {
            count++;
        }

        public synchronized int getCount() {
            return count;
        }
    }

    static class LuckyThread extends Thread {
        private final StateObject obj;

        public LuckyThread(StateObject obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            while (true) {
                int currentX = obj.getAndIncrementX();
                if (currentX > 999999) {
                    break;
                }

                if ((currentX % 10) + (currentX / 10) % 10 + (currentX / 100) % 10 == (currentX / 1000)
                        % 10 + (currentX / 10000) % 10 + (currentX / 100000) % 10) {
                    System.out.println(currentX);
                    obj.incCount();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        StateObject obj = new StateObject();

        Thread t1 = new LuckyThread(obj);
        Thread t2 = new LuckyThread(obj);
        Thread t3 = new LuckyThread(obj);

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        System.out.println("Total: " + obj.getCount());
    }
}