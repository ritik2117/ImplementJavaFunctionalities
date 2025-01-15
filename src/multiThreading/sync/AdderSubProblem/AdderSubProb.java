package multiThreading.sync.AdderSubProblem;

public class AdderSubProb {
    public static void main(String[] args) {
        SharedResourceAdderSub sharedResource = new SharedResourceAdderSub();
        Thread adderThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                sharedResource.add(10);
            }
        });

        Thread subtractorThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                sharedResource.subtract(5);
            }
        });

        adderThread.start();
        subtractorThread.start();

        try {
            adderThread.join();
            subtractorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final value: " + sharedResource.getValue());
    }
}
