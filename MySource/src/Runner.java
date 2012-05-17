import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;

import core.SocketSelectorCore;


public class Runner {
    public static void main(String[] args) throws IOException, InterruptedException{
        Selector selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel server = ServerSocketChannel.open();
        InetSocketAddress isa = new InetSocketAddress((InetAddress)null, 8080);
        
        server.configureBlocking(false);
        server.socket().bind(isa);
        server.register(selector, SelectionKey.OP_ACCEPT);
        
        SocketSelectorCore core = new SocketSelectorCore(server, selector);
        
        core.run();
    }
}