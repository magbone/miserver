package server.utils.websocket.util.protocols.websocket;

import server.utils.websocket.util.protocols.Utils;


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



public class Header {

    Frame frame = new Frame();

    StringBuffer sb = new StringBuffer();
    public Header(byte[] bytes){
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

        if (frame.getMASK() == 1){
            frame.setMaskingKey(new int[]{bytes[index++],bytes[index++],bytes[index++],bytes[index++]});


            for (int j = 0;j < frame.getPayLoadLen();j++){
                sb.append(bytes[index+j] ^ frame.getMaskingKey()[j%4]);
            }
        }else{
            sb.append(new String(Utils.sbBytes(bytes,index,index + frame.getPayLoadLen())));
        }
        System.out.println(frame.toString());
        System.out.println(sb.toString());

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
