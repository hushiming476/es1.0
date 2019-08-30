package com.itheima.serach;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

public class serach {

    /**
     * 一、获取连接对象
     */
    public static TransportClient getClient() throws Exception{
        //1：配置es信息
        Settings settings = Settings.EMPTY;
        //2.改集群名
        Settings settings1 = Settings.builder().put("cluster.name","elasticsearch").build();
        //3.创建客户端对象
        TransportClient transportClient = new PreBuiltTransportClient(settings);
        //4.指定服务器的地址和ip
        transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));
        //5.返回
        return transportClient;
    }
}
