import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;


class Main {

    public static void main(String[] _args) {
        Option.parseArgs(_args);
        Option.displayOptions();

        String bootstrapUrl = Option.getUrl() + ":" + Option.getPort();
        StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(bootstrapUrl));

        StoreClient<String, String> client = factory.getStoreClient(Option.getDBName());

        // create the value
        client.put("some_key", "someValue");

        System.out.println("done");
    }
}