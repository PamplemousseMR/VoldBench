package Benchmark;

import voldemort.client.StoreClient;

public abstract class IBenchmark {

    final StoreClient<String, String> m_CLIENT;
    private final String m_NAME;

    IBenchmark(String _name, StoreClient<String, String> _client) {
        m_NAME = _name;
        m_CLIENT = _client;
    }

    public void bench() {
        long startTime = System.nanoTime();
        run();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println("Benchmark : " + m_NAME + " => " + duration / 1000000.0);
    }

    protected abstract void run();

}
