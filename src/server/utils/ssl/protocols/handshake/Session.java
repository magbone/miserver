package server.utils.ssl.protocols.handshake;

/***
 *
 * Session
 *
 */
public class Session{
    private int sessionIdLen;
    private String sessionId;

    private Session(){

    }

    public Session(int sessionIdLen,String sessionId){
        this.sessionId = sessionId;
        this.sessionIdLen = sessionIdLen;
    }

    public void setSessionIdLen(int sessionIdLen) {
        this.sessionIdLen = sessionIdLen;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getSessionIdLen() {
        return sessionIdLen;
    }

    public String getSessionId() {
        return sessionId;
    }
}