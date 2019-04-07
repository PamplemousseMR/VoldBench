package Activity;

import Benchmark.Insert;
import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;

class Main {

    public static void main(String[] _args) {
        Option.parseArgs(_args);
        Option.displayOptions();

        String serverUrl = Activity.Option.getUrl() + ":" + Activity.Option.getPort();
        StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(serverUrl));

        StoreClient<String, String> client = factory.getStoreClient(Activity.Option.getDBName());

        Insert ins = new Insert(client);
        ins.bench();
    }
}