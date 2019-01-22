package server.utils.ssl.protocols.handshake;

public class Random{
    private String unixTime;
    private String randomHex;

    public Random(String unixTime,String randomHex){
        this.randomHex = randomHex;
        this.unixTime = unixTime;
    }

    public void setRandomHex(String randomHex) {
        this.randomHex = randomHex;
    }

    public void setUnixTime(String unixTime) {
        this.unixTime = unixTime;
    }

    public String getUnixTime() {
        return unixTime;
    }

    public String getRandomHex() {
        return randomHex;
    }
}
