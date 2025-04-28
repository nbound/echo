package echo;

import java.net.Socket;

public class SecurityHandler extends ProxyHandler{
    private SafeTable loginInfo;
    private boolean loggedIn;

    public SecurityHandler(Socket s) {
        super(s);
        loginInfo = new SafeTable();
        loggedIn = false;
    }
    public SecurityHandler() {
        super();
        loginInfo = new SafeTable();
        loggedIn = false;
    }

    protected String response(String request) throws Exception {
        String[] response = request.split(" ");
        if(!loggedIn){
            if(response.length == 3){
                if (response[0].equalsIgnoreCase("new")) {  //assuming username is unique
                    loginInfo.put(response[1], response[2]);
                    return "Security: New login created";
                }else if(response[0].equalsIgnoreCase("login")){
                    if(loginInfo.containsKey(response[1])){
                        if(response[2].equals(loginInfo.get(response[1]))){
                            loggedIn = true;
                            return "Security: Login verified";
                        }
                    }
                    return "Security: Invalid login";
                }
            }
            return "Security: Please login first";
        }else{
            return super.response(request);
        }
    }
}
