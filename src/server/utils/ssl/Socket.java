package server.utils.ssl;

import server.utils.ssl.protocols.record.TSLRecord;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Socket implements Runnable {

    private SSLBean sslBean;
    private int port;
    public Socket(SSLBean sslBean){
        this.sslBean = sslBean;
        this.port = sslBean.getPort();
    }

    @Override
    public void run() {
        try{
            System.out.println("Server run over https, https://127.0.0.1:" + this.port);
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
                                TSLRecord record = new TSLRecord(bytes);
                                byte[] bytes2 = record.getBytes();
                                ByteBuffer writeBuffer = ByteBuffer.allocate(bytes2.length);
                                writeBuffer.put(bytes2);
                                writeBuffer.flip();
                                sc.write(writeBuffer);
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
