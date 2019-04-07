package Benchmark;

import voldemort.client.StoreClient;

public class Insert extends IBenchmark {

    public Insert(StoreClient<String, String> _client) {
        super("Insert", _client);
    }

    protected void run() {
        m_CLIENT.put("Key", "Value");
    }

}
