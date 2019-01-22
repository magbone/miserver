package server.utils.ssl.protocols.handshake;

import java.util.ArrayList;
import java.util.List;

/**
 * Cipher
 */
public class CipherSuits{
    private List<String> cipherSuits = new ArrayList<>();
    private int cipherSuitsLen;

    //cipher suites type
    public final static int TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384 = 0xc02c;
    public final static int TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256 = 0xc02b;
    public final static int TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384 = 0xc024;
    public final static int TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256 = 0xc023;
    public final static int TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA = 0xc00a;
    public final static int TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA = 0xc009;
    public final static int TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256 = 0xcca9;
    public final static int TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384 = 0xc030;
    public final static int TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256 = 0xc02f;
    public final static int TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384 = 0xc028;
    public final static int TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256 = 0xc027;
    public final static int TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA = 0xc014;
    public final static int TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA = 0xc013;
    public final static int TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256 = 0xcca8;
    public final static int TLS_RSA_WITH_AES_256_GCM_SHA384 = 0x009d;
    public final static int TLS_RSA_WITH_AES_128_GCM_SHA256 = 0x009c;
    public final static int TLS_RSA_WITH_AES_256_CBC_SHA256 = 0x003d;
    public final static int TLS_RSA_WITH_AES_128_CBC_SHA256 = 0x003c;
    public final static int TLS_RSA_WITH_AES_256_CBC_SHA = 0x0035;
    public final static int TLS_RSA_WITH_AES_128_CBC_SHA = 0x002f;
    public final static int TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA = 0xc008;
    public final static int TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA = 0xc012;
    public final static int TLS_RSA_WITH_3DES_EDE_CBC_SHA = 0x000a;

    public CipherSuits(){
        cipherSuits.clear();
    }

    public void setCipherSuits(String s) {
        this.cipherSuits.add(s);
    }

    public void setCipherSuitsLen(int cipherSuitsLen) {
        this.cipherSuitsLen = cipherSuitsLen;
    }

    public int getCipherSuitsLen() {
        return cipherSuitsLen;
    }

    public List<String> getCipherSuits() {
        return cipherSuits;
    }
}

