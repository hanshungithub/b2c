package cn.hassan.rest.controller;

import com.sun.javafx.fxml.builder.URLBuilder;
import com.taotao.common.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;

@Slf4j
public class ServiceTest {

    /**
     * 单机版测试
     * <p>Title: testSpringJedisSingle</p>
     * <p>Description: </p>
     */
    @Test
    public void testSpringJedisSingle() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
        Jedis jedis = pool.getResource();
        String string = jedis.get("key1");
        System.out.println(string);
        jedis.close();
        pool.close();
    }

    /**
     * jedis测试
     *
     * @throws Exception
     */
    @Test
    public void jedisTest() {
        //创建jedis对象
        Jedis jedis = new Jedis("192.168.48.128",6379);
        jedis.set("key1", "hassan");
        String s = jedis.get("key1");
        System.out.println(s);
        jedis.close();
    }

    @Test
    public void doGet() throws Exception {
        try {
            //创建httpclient对象
            CloseableHttpClient client = HttpClients.createDefault();
            //创建get对象
            HttpGet get = new HttpGet("http://www.baidu.com");
            //执行请求
            CloseableHttpResponse response = client.execute(get);
            //取相应的结果
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                //如果打印出来的内容是乱码，则可以添加编码
                String s = EntityUtils.toString(entity,"utf-8");
                System.out.println(s);
            }
            //
            client.close();
        } catch (IOException e) {
            log.error("httpclient连接失败");
            throw new Exception("连接失败");
        }
    }

    @Test
    public void doGetWitParams() throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder("http://www.baidu.com/s");
        builder.addParameter("wd", "java");
        HttpGet get = new HttpGet(builder.build());
        CloseableHttpResponse response = client.execute(get);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity,"utf-8"));
        }
    }

    @Test
    public void doPost() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8081/rest/content/httpclient/post.html");
        CloseableHttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
        response.close();
        client.close();
    }
}
