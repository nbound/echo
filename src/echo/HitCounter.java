package echo;
import java.net.Socket;

class HitCounter {
    private int hits = 0;
    synchronized public void incHits() { hits++; }
    synchronized public int getHits() { return hits; }
}