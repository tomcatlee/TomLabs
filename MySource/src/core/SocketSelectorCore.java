package core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;

public class SocketSelectorCore {
    private ServerSocketChannel serverChannel;
    private Selector selector;
    private static String CRLF ="\r\n";

    public SocketSelectorCore(ServerSocketChannel serverChannel,
            Selector selector) {
        super();
        this.serverChannel = serverChannel;
        this.selector = selector;
    }
    
    public String chunkedString(String data){
        StringBuilder bldr = new StringBuilder();
        
        bldr.append(Long.toHexString(data.length()));
        bldr.append(CRLF);
        if( data.length() > 0){
            bldr.append(data);
        }
        bldr.append(CRLF);
        return bldr.toString();
    }
    
    private HashMap<String, String> getHeaders(SelectionKey key){
        StringBuilder reqBody = (StringBuilder)key.attachment();
        String request = reqBody.substring(0, reqBody.indexOf(CRLF));
        String[] lines = request.split(CRLF);
        HashMap<String, String> ret = new HashMap<String, String>();
        boolean first = true;
        for(String line : lines ){
            if (first){
                ret.put("", line);
            }else{
                String[] parts = line.split(":");
                if( parts.length != 2 ){
                    continue;
                }
                ret.put(parts[0], parts[1].trim());
            }
        }
        return ret;
    }

    public void write(SelectionKey key) {
        if (key == null) {
            return;
        }
        
        HashMap<String, String> req = getHeaders(key);
        boolean keepAlive = req.get("Connection") == "Keep-Alive";
        
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 200 OK");
        response.append(CRLF);
        response.append("Content-Type: text/plain");
        response.append(CRLF);
        response.append("Connection: keep-alive");
        response.append(CRLF);
        response.append("Transfer-Encoding: chunked");
        response.append(CRLF);
        
        response.append(CRLF);
        response.append(chunkedString("Hello World"));
        response.append(chunkedString(""));

        ByteBuffer b = ByteBuffer.wrap(response.toString().getBytes());
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            channel.write(b);
            channel.close();
            key.cancel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(SelectionKey key) {
        if (key == null) {
            return;
        }
        ByteBuffer b = ByteBuffer.allocate(4096);
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            int read = channel.read(b);
            if( read > 0){
                StringBuilder bldr = (StringBuilder) key.attachment();
                String curr = new String(b.array());
                bldr.append(curr);
                if( bldr.indexOf("\r\n\r\n") > -1 ){
                    key.interestOps(SelectionKey.OP_WRITE|SelectionKey.OP_READ);
                }else{
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
            if( read < 0 ){
                channel.close();
                key.cancel();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void accept(SelectionKey key) {
        if (key == null) {
            return;
        }
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key
                    .channel();

            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(key.selector(), SelectionKey.OP_READ);
            socketChannel.keyFor(key.selector()).attach(new StringBuilder());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void run() {
        try {
            serverChannel.configureBlocking(false);
            while (true) {
                selector.select();
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys()
                        .iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey k = selectedKeys.next();
                    selectedKeys.remove();

                    try {
                        if (!k.isValid()) {
                            continue;
                        }
                        
                        if (k.isAcceptable()) {
                            accept(k);
                        }else if (k.isReadable()) {
                            read(k);
                        }else if (k.isWritable()) {
                            write(k);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
