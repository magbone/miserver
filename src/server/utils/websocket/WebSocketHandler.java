package server.utils.websocket;


import server.utils.websocket.util.protocols.handshake.Protocols;
import server.utils.websocket.util.protocols.websocket.Header;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class WebSocketHandler implements Runnable{


    private int port;


    AbstractWebSocket server1;
    public WebSocketHandler(Class<? extends AbstractWebSocket> server){
        this(server,8082);
    }

    public WebSocketHandler(Class<? extends AbstractWebSocket> server,int port){
        if (server == null) throw new NullPointerException("handler could not be null");
        this.port = port;
        try {
            server1 = server.newInstance();
        }catch (IllegalAccessException | InstantiationException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try{
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.socket().bind(new InetSocketAddress(this.port));
            channel.configureBlocking(false);
            Selector selector = Selector.open();
            SelectionKey key = channel.register(selector,SelectionKey.OP_ACCEPT);

            while (true){
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key1 = null;
                while (it.hasNext()){
                    key1 = it.next();
                    it.remove();
                    if (key1.isValid()){
                        if (key1.isAcceptable()){
                            ServerSocketChannel sec = (ServerSocketChannel) key1.channel();
                            SocketChannel sc = sec.accept();
                            sc.configureBlocking(false);
                            sc.socket().setReuseAddress(true);
                            sc.register(selector,SelectionKey.OP_READ);
                        }

                        if (key1.isReadable()){
                            SocketChannel sc = (SocketChannel)key1.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            int readBytes = sc.read(readBuffer);

                            if (readBytes > 0){
                                readBuffer.flip();
                                byte[] bytes = new byte[readBuffer.remaining()];
                                readBuffer.get(bytes);
                                String body = new String(bytes);
                                //System.out.println(body);
                                //WebSocket Get Protocol
                                if (Protocols.isWebSocketProtocol(body)){
                                    WebSocketShakeHandler handler = new WebSocketShakeHandler(body,server1);
                                    byte[] bytes2 = handler.toString().getBytes();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(bytes2.length);
                                    writeBuffer.put(bytes2);
                                    writeBuffer.flip();
                                    sc.write(writeBuffer);
                                }
                                //WebSocket Protocol
                                else{
                                    Header header = new Header(bytes);
                                    for (int i = 0;i < bytes.length;i++){
                                        System.out.println(bytes[i]);
                                    }
                                    byte[] bytes2 = "2".toString().getBytes();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(bytes2.length);
                                    writeBuffer.put(bytes2);
                                    writeBuffer.flip();
                                    sc.write(writeBuffer);
                                }
                            }
                        }
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
