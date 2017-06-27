package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import thrift.service.ComputeServer;

/**
 * Created by hawkingfoo on 2017/6/27 0027.
 */
public class ServerMain {
    private static final Logger logger = LogManager.getLogger(ServerMain.class);

    public static void main(String[] args) {
        try {
            ThriftTestImpl workImpl = new ThriftTestImpl();
            TProcessor tProcessor = new ComputeServer.Processor<ComputeServer.Iface>(workImpl);
            final TNonblockingServerTransport transport = new TNonblockingServerSocket(9000);
            TThreadedSelectorServer.Args ttpsArgs = new TThreadedSelectorServer.Args(transport);
            ttpsArgs.transportFactory(new TFramedTransport.Factory());
            ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
            ttpsArgs.processor(tProcessor);
            ttpsArgs.selectorThreads(16);
            ttpsArgs.workerThreads(32);
            logger.info("compute service server on port :" + 9000);
            TServer server = new TThreadedSelectorServer(ttpsArgs);
            server.serve();
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
