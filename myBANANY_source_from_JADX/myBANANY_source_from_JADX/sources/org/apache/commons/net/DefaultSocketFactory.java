package org.apache.commons.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;

public class DefaultSocketFactory extends SocketFactory {
    private final Proxy connProxy;

    public DefaultSocketFactory() {
        this(null);
    }

    public DefaultSocketFactory(Proxy proxy) {
        this.connProxy = proxy;
    }

    public Socket createSocket() throws IOException {
        if (this.connProxy != null) {
            return new Socket(this.connProxy);
        }
        return new Socket();
    }

    public Socket createSocket(String host, int port) throws UnknownHostException, IOException {
        if (this.connProxy == null) {
            return new Socket(host, port);
        }
        Socket s = new Socket(this.connProxy);
        s.connect(new InetSocketAddress(host, port));
        return s;
    }

    public Socket createSocket(InetAddress address, int port) throws IOException {
        if (this.connProxy == null) {
            return new Socket(address, port);
        }
        Socket s = new Socket(this.connProxy);
        s.connect(new InetSocketAddress(address, port));
        return s;
    }

    public Socket createSocket(String host, int port, InetAddress localAddr, int localPort) throws UnknownHostException, IOException {
        if (this.connProxy == null) {
            return new Socket(host, port, localAddr, localPort);
        }
        Socket s = new Socket(this.connProxy);
        s.bind(new InetSocketAddress(localAddr, localPort));
        s.connect(new InetSocketAddress(host, port));
        return s;
    }

    public Socket createSocket(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException {
        if (this.connProxy == null) {
            return new Socket(address, port, localAddr, localPort);
        }
        Socket s = new Socket(this.connProxy);
        s.bind(new InetSocketAddress(localAddr, localPort));
        s.connect(new InetSocketAddress(address, port));
        return s;
    }

    public ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }

    public ServerSocket createServerSocket(int port, int backlog) throws IOException {
        return new ServerSocket(port, backlog);
    }

    public ServerSocket createServerSocket(int port, int backlog, InetAddress bindAddr) throws IOException {
        return new ServerSocket(port, backlog, bindAddr);
    }
}
