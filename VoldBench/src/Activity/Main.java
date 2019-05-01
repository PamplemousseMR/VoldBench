package Activity;

import Benchmark.Find;
import Benchmark.Insert;
import Benchmark.InsertSynchronized;
import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;

class Main {

    public static void main(String[] _args) {
        Option.parseArgs(_args);
        Option.displayOptions();

        String serverUrl = Activity.Option.getVDMUrl() + ":" + Activity.Option.getVDMPort();
        StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(serverUrl));

        StoreClient<String, String> client = factory.getStoreClient(Activity.Option.getVDMName());

        Insert ins = new Insert(client);
        ins.bench();
        InsertSynchronized insSync = new InsertSynchronized(client);
        insSync.bench();
        Find find = new Find(client);
        find.bench();
    }
}