package echo;

import java.net.Socket;

public class CacheHandler extends ProxyHandler{
    private SafeTable cache;

    public CacheHandler(Socket s) {
        super(s);
        cache = new SafeTable();
    }
    public CacheHandler() {
        super();
        cache = new SafeTable();
    }

    protected String response(String request) throws Exception {
        if (cache.containsKey(request)) {
            System.out.println("Response found in cache");
            return cache.get(request);
        } else {
            System.out.println("New response, updated cache");
            String response = super.response(request);
            cache.put(request, response);
            return response;
        }
    }
}
