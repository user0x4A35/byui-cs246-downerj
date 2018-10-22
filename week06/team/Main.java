public class Main {
    public static void main(String[] args) {
        // count by odds
        Thread t1 = new Thread(() -> {
            for (int i = 1; i < 100; i += 2) {
                System.out.printf("%d ODD%n", i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    /* */
                }
            }
        });
        t1.start();
        
        // count by evens
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i += 2) {
                System.out.printf("%d EVEN%n", i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    /* */
                }
            }
        });
        t2.start();
        
        // display "Running"
        RunDisplayer disp = new RunDisplayer();
        Thread t3 = new Thread(disp);
        t3.start();
        
        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            // do nothing
        }
        disp.terminate();
        
        System.out.println("All fnished");
    }
}

class RunDisplayer implements Runnable {
    private boolean running;
            
    public void terminate() {
        running = false;
    }
    
    @Override
    public void run() {
        running = true;
        while (running) {
            System.out.println("Running");
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                /* */
            }
        }
    }
};