package echo;

import java.net.Socket;

public class SecurityHandler extends ProxyHandler{
    private SafeTable loginInfo;
    private boolean loggedIn;
    private int attemptsLeft;

    public SecurityHandler(Socket s) {
        super(s);
        init();
    }
    public SecurityHandler() {
        super();
        init();
    }

    private void init(){
        loginInfo = new SafeTable();
        loggedIn = false;
        attemptsLeft = 3;
    }

    protected String response(String request) throws Exception {
        String[] response = request.split(" ");
        if(!loggedIn){
            if(attemptsLeft > 0){
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
                        attemptsLeft--;
                        if(attemptsLeft > 0){
                            return "Security: Invalid login. "+attemptsLeft+
                                    " attempt(s) left";
                        }else{
                            return "Security: Invalid login. You have been locked out. " +
                                    "Please enter \"quit\" to terminate the session.";
                        }
                    }
                }
                return "Security: Please login first";
            }else{
                return "Security: You have been locked out. " +
                        "Please enter \"quit\" to terminate the session.";
            }

        }else{
            return super.response(request);
        }
    }
}
