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

        if (loggedIn) {
            return super.response(request);
        }
        if (attemptsLeft <= 0) {
            return "Security: You have been locked out. Please enter \"quit\" to terminate the session.";
        }
        if (response.length != 3) {
            return "Security: Please login first";
        }
        String command = response[0];
        String username = response[1];
        String password = response[2];
        if (command.equalsIgnoreCase("new")) {
            loginInfo.put(username, password);
            return "Security: New login created";
        }
        if (command.equalsIgnoreCase("login")) {
            if (loginInfo.containsKey(username) && password.equals(loginInfo.get(username))) {
                loggedIn = true;
                return "Security: Login verified";
            } else {
                attemptsLeft--;
                if (attemptsLeft > 0) {
                    return "Security: Invalid login. " + attemptsLeft + " attempt(s) left";
                } else {
                    return "Security: Invalid login. You have been locked out. " +
                            "Please enter \"quit\" to terminate the session.";
                }
            }
        }
        return "Security: Please login first";
    }
}
