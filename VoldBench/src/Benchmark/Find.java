package Benchmark;

import Activity.Option;
import voldemort.client.StoreClient;

import java.util.Random;

public class Find extends IBenchmark {

    public Find(StoreClient<String, String> _client) {
        super("Find", _client);
    }

    @Override
    protected void setup() {
        for(long i = 0; i< Option.getDBSize() ; ++i) {
            m_CLIENT.put("Key_" + i, String.valueOf(1));
        }
    }

    @Override
    protected void run() {
        Random rnd = new Random();
        for(long i=0L ; i< Option.getLoop() ; ++i) {
            m_CLIENT.get("Key_" + rnd.nextLong() % Option.getDBSize());
        }
    }

    @Override
    protected void clean() {
        for(long i = 0; i< Option.getDBSize() ; ++i) {
            m_CLIENT.delete("Key_" + i);
        }
    }
}
