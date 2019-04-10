package Benchmark;

import Activity.Option;
import voldemort.client.StoreClient;

public abstract class IBenchmark {

    final StoreClient<String, String> m_CLIENT;
    private final String m_NAME;

    IBenchmark(String _name, StoreClient<String, String> _client) {
        m_NAME = _name;
        m_CLIENT = _client;
    }

    public void bench() {
        System.out.print("...");
        setup();

        System.out.print(" ...");
        long startTime = System.nanoTime();
        run();
        long endTime = System.nanoTime();

        System.out.print(" ...");
        clean();

        long duration = (endTime - startTime);
        System.out.println(" Benchmark on " + Option.getLoop() + " loops : " + m_NAME + " => " + duration / 1000000.0 + " ms : " + Option.getLoop()/ (duration / 1000000000.0) + " l/s");
    }

    protected void setup() {
    }

    protected abstract void run();

    protected void clean(){

    }

}
