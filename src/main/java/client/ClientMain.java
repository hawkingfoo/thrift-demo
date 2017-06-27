package client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.service.ComputeRequest;
import thrift.service.ComputeResponse;
import thrift.service.ComputeServer;
import thrift.service.ComputeType;

/**
 * Created by hawkingfoo on 2017/6/27 0027.
 */
public class ClientMain {
    private ComputeRequest request;

    public ClientMain() {
        request = new ComputeRequest();
        request.setX(1);
        request.setY(2);
        request.setComputeType(ComputeType.ADD);
    }

    public static void main(String[] args) {
        TTransport transport = null;
        try {
            System.out.println("***********");
            long begin = System.currentTimeMillis();
            // localhost
            transport = new TFramedTransport(new TSocket("127.0.0.1", 9000));
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            ComputeServer.Client client = new ComputeServer.Client(protocol);

            //调用client的getComputeResult方法
            ComputeResponse response = client.getComputeResult(new ClientMain().request);
            System.out.println("cost:[" + (System.currentTimeMillis() - begin) + "ms]");
            System.out.println("***********");
            if (response != null) {
                System.out.println(response.toString());
            }
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null)
                transport.close();
        }
    }
}
