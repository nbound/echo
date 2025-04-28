package echo;

import java.net.Socket;

public class ProxyHandler extends RequestHandler {

    protected Correspondent peer;

    public ProxyHandler(Socket s) { super(s); }
    public ProxyHandler() { super(); }

    public void initPeer(String peerHost, int peerPort) {
        peer = new Correspondent();
        peer.requestConnection(peerHost, peerPort);
    }

    protected void shutDown() {
        peer.send("quit");
        super.shutDown();
    }

    protected String response(String msg) throws Exception {
        // forward msg to peer
        peer.send(msg);
        // return peer's response
        return peer.receive();
    }
}