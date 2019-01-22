package server.utils.ssl.protocols;

public class ProtocolVersion {

    /***
     * TLS 1.2 major = 3 minor = 3
     * TLS 1.0 major = 3 minor = 0
     */
    private int major;
    private int minor;

    public ProtocolVersion(int major,int minor){
        this.major = major & 0x00ff;
        this.minor = minor & 0x00ff;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMajor(int major) {
        this.major = major & 0x00ff;
    }

    public void setMinor(int minor) {
        this.minor = minor & 0x00ff;
    }
    @Override
    public String toString() {
        return "major:" + major +",minor:" + minor;
    }
}
