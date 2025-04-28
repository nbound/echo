package echo;

import java.util.*;

public class SafeTable extends HashMap<String, String> {

    public SafeTable(){
        super();
    }

    public synchronized String get(String request){
        return super.get(request);
    }

    public synchronized String put(String request, String response){
        return super.put(request, response);
    }
}
