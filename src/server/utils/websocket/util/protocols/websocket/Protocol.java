package server.utils.websocket.util.protocols.websocket;

import server.utils.websocket.AbstractWebSocket;
import server.utils.websocket.WebSocket;
import server.utils.websocket.util.protocols.Utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;


/**
 * WebSocket Basing Framing Protocol
 *
 * I Copy the document from FRC6455 <link>https://tools.ietf.org/html/rfc6455</link>
 *
 * The details as follow.
 *
 *      0                   1                   2                   3
 *      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *      +-+-+-+-+-------+-+-------------+-------------------------------+
 *      |F|R|R|R| opcode|M| Payload len |    Extended payload length    |
 *      |I|S|S|S|  (4)  |A|     (7)     |             (16/64)           |
 *      |N|V|V|V|       |S|             |   (if payload len==126/127)   |
 *      | |1|2|3|       |K|             |                               |
 *      +-+-+-+-+-------+-+-------------+ - - - - - - - - - - - - - - - +
 *      |     Extended payload length continued, if payload len == 127  |
 *      + - - - - - - - - - - - - - - - +-------------------------------+
 *      |                               |Masking-key, if MASK set to 1  |
 *      +-------------------------------+-------------------------------+
 *      | Masking-key (continued)       |          Payload Data         |
 *      +-------------------------------- - - - - - - - - - - - - - - - +
 *      :                     Payload Data continued ...                :
 *      + - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - +
 *      |                     Payload Data continued ...                |
 *      +---------------------------------------------------------------+
 *
 *  @author magbone
 *  @version 1
 */



public class Protocol implements WebSocket{

    Frame frame = new Frame();

    StringBuffer sb = new StringBuffer();

    byte[] bytes;

    SocketChannel sc;

    AbstractWebSocket webSocket;

    public Protocol(byte[] bytes){
        this.bytes = bytes;
        setHeader(bytes);
    }

    private void setHeader(byte[] bytes){
        int index = 0;

        frame.setFIN((bytes[index] >> 7) & 1);
        frame.setOpcode(bytes[index++] & 15);
        frame.setMASK((bytes[index] >> 7)& 1);
        frame.setPayLoadLen(bytes[index++] & 0x7f);

        if (frame.getPayLoadLen() == 126){
            frame.setPayLoadLen(bytes[index++] << 8 + bytes[index++]);
        }

        if (frame.getPayLoadLen() == 127){
            index += 4;
            frame.setPayLoadLen((bytes[index++] << 24) + (bytes[index++] << 16) + (bytes[index++] << 8) + bytes[index++]);
        }


        /**
         *
         *
         * Octet i of the transformed data ("transformed-octet-i") is the XOR of
         * octet i of the original data ("original-octet-i") with octet at index
         * i modulo 4 of the masking key ("masking-key-octet-j"):
         *
         *   j                   = i MOD 4
         *  transformed-octet-i = original-octet-i XOR masking-key-octet-j
         *
         */
        if (frame.getMASK() == 1){
            frame.setMaskingKey(new int[]{bytes[index++],bytes[index++],bytes[index++],bytes[index++]});
            byte[] bytes1 = new byte[frame.PayLoadLen];
            for (int j = 0;j < frame.getPayLoadLen();j++){
                bytes1[j] = (byte)( bytes[index+j] ^ frame.getMaskingKey()[j % 4]);
            }
            sb.append(new String(bytes1));
        }else{
            sb.append(new String(Utils.sbBytes(bytes,index,index + frame.getPayLoadLen())));
        }

    }



    public String getString(){
        return this.sb.toString();
    }


    public byte[] write(String s,int opcode ){
        byte[] bytes1 = s.getBytes();
        int len = bytes1.length;

        List<Byte> byteList = new ArrayList<>();
        byteList.add((byte)((opcode << 7) + 1));

        if (bytes1.length < 126)
            byteList.add((byte) len);
        else if (bytes1.length < 0x10000){
            byteList.add((byte) 126);
            byteList.add((byte) (len &0xFFF0 >> 8));
            byteList.add((byte) (len &0xFF));
        }else{
            byteList.add((byte) 127);
            byteList.add((byte) 0);
            byteList.add((byte) 0);
            byteList.add((byte) 0);
            byteList.add((byte) 0);
            byteList.add((byte) ((len & 0xFF000000) >> 24));
            byteList.add((byte) ((len & 0xFF0000) >> 16));
            byteList.add((byte) (len & 0xFF));
        }

        byte[] bytes2 = new byte[byteList.size()];
        for (int i = 0; i < byteList.size();i++){
            bytes2[i] = byteList.get(i);
        }
        return Utils.copyByte(bytes2,bytes1);
    }

    public void setSocketChannel(SocketChannel sc){
        this.sc = sc;
    }

    public void setServer(AbstractWebSocket wb){
        this.webSocket = wb;
    }
    @Override
    public int readyState() {
        return 0;
    }

    @Override
    public int version() {
        return 0;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void send(String s){
        try {
            byte[] bytes2 = this.write(s, 0x1);
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes2.length);
            writeBuffer.put(bytes2);
            writeBuffer.flip();
            sc.write(writeBuffer);
        }catch (IOException e){
            webSocket.onError(this,e);
        }
    }

    @Override
    public boolean isClose() {
        return false;
    }

    @Override
    public void close() {
        try {
            byte[] bytes2 = this.write("", 0x8);
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes2.length);
            writeBuffer.put(bytes2);
            writeBuffer.flip();
            sc.write(writeBuffer);
        }catch (IOException e){
            webSocket.onError(this,e);
        }
    }

    @Override
    public void open() {

    }

    class Frame{
        /**
         *  Indicates that this is the final fragment in a message.  The first
         *  fragment MAY also be the final fragment.
         */
        private int FIN; //1 bit


        //We needn't it
        private int RSV1; //1 bit
        private int PSV2; //1 bit
        private int PSV3; //1 bit

        /***
         *  Defines the interpretation of the "Payload data".  If an unknown
         *   opcode is received, the receiving endpoint MUST _Fail the
         *  WebSocket Connection_.  The following values are defined.

         *  <strong>%x0</strong> denotes a continuation frame

         *  <strong>%x1</strong> denotes a text frame

         *  <strong>%x2</strong> denotes a binary frame

         *  <strong>%x3-7</strong> are reserved for further non-control frames

         *  <strong>%x8</strong> denotes a connection close

         *  <strong>%x9</strong> denotes a ping

         * <strong>%xA</strong> denotes a pong

         *  <strong>%xB-F</strong> are reserved for further control frames
         */
        private int Opcode; //4 bit

        private int MASK; //1 bit
        private int PayLoadLen; //7 bit


        private int[] MaskingKey;

        public Frame(){
            this(0,0,0,0);
        }

        public Frame(int FIN,int Opcode,int MASK,int PayloadLen){
            this.FIN = FIN;
            this.Opcode = Opcode;
            this.MASK = MASK;
            this.PayLoadLen = PayloadLen;
        }


        public int getFIN() {
            return FIN;
        }

        public int getMASK() {
            return MASK;
        }

        public int getOpcode() {
            return Opcode;
        }

        public int getPayLoadLen() {
            return PayLoadLen;
        }

        public int[] getMaskingKey() {
            return MaskingKey;
        }

        public void setFIN(int FIN) {
            this.FIN =  FIN;
        }

        public void setMASK(int MASK) {
            this.MASK = MASK;
        }

        public void setOpcode(int opcode) {
            this.Opcode = opcode;
        }

        public void setPayLoadLen(int payLoadLen) {
            this.PayLoadLen = payLoadLen;
        }

        public void setMaskingKey(int[] maskingKey) {
            MaskingKey = maskingKey;
        }

        @Override
        public String toString() {
            return "FIN:" + this.FIN + ",Opcode:" + this.PayLoadLen + ",Mask:" + this.MASK + ",PayloadLen:" + this.PayLoadLen;
        }

    }
}
