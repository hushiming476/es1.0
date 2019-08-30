package com.itheima.inddex;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;

public class index {

    /**
     * 一、获取连接对象
     */
    public static TransportClient getClient() throws Exception{
    //1：配置es信息

    //2.改集群名
    Settings settings = Settings.builder().put("cluster.name","cluster-es").build();
    //3.创建客户端对象
    TransportClient transportClient = new PreBuiltTransportClient(settings);
    //4.指定服务器的地址和ip
    transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));
    transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9301));
    transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9302));
    //5.返回
    return transportClient;
    }

    // 测试
    @Test
    public void testgetClient() throws Exception {
        System.out.println(getClient());
    }

    /**
     * 二、创建索引库
     */
    @Test
    public void createSerach() throws Exception {
       // 1 :获取链接对象
        TransportClient transportClient = getClient();
       // 2: 创建索引库
        CreateIndexResponse response = transportClient.admin().indices().prepareCreate("blog6").get();
        System.out.println(response.isAcknowledged());
        System.out.println(response.isShardsAcked());
        System.out.println(response.index());
       // 3:释放资源
        transportClient.close();
    }

    /**
     * 三、添加索引数据
     */
    @Test
    public void addserach()throws Exception{
    // 1 :获取链接对象
        TransportClient transportClient = getClient();
    // 2: 创建索引   .startObject()=={ }==.endObject();
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("id",3)
                .field("title","elasticsearch是一个基于lucene的搜索服务")
                .field("pic","es.txt")
                .field("context","Elasticsearch核心概念\n" +
                        "1.近实时 NRT(Near Realtime) ==netty==\n" +
                        "2.集群 cluster –==非常的方便==\n" +
                        "3.节点 node ==== 节点名（每个人自己的名字）不同，集群名\n" +
                        "4.索引 index(重点)--索引库---数据库\n" +
                        "5.类型 type(重点)----table（表） \n" +
                        "6.文档 document(重点)----数据载体\n" +
                        "7.分片和复制shards&replicas e")
                .endObject();
    // 3.写入json对象 es数据库中
        IndexResponse indexResponse = transportClient.prepareIndex("blog6","context","3")
                                    .setSource(builder)
                                    .get();
        System.out.println(indexResponse.status());
    // 4:释放资源
        transportClient.close();
    }

    /**
     * 三、修改索引数据
     */
    @Test
    public void updateserach()throws Exception{
        // 1 :获取链接对象
        TransportClient transportClient = getClient();
        // 2: 创建索引   .startObject()=={ }==.endObject();
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("id",3)
                .field("title","elasticsearch是一个基于lucene的搜索服务")
                .field("pic","es.txt")
                .field("context",
                        "能够使用java客户端操作ElasticSearch\n" +
                              "能够使用ElasticSearch集成IK分词器\n" +
                              "利用ElasticSearch能够完成索引CURD的操作\n" +
                              "利用ElasticSearch能够完成索引搜索以及分页和高亮相关操作\n" +
                              "能够完成ElasticSearch创建映射的操作")
                .endObject();
        // 3.写入json对象 es数据库中
        IndexResponse indexResponse = transportClient.prepareIndex("blog6","context")
                .setSource(builder)
                .get();
        System.out.println(indexResponse.status());
        // 4:释放资源
        transportClient.close();
    }
}
