package com.xb.blog.message.config.websocket.server;

import com.xb.blog.message.common.constants.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WebSocket消息推送服务端
 */
@Component
@ServerEndpoint("/websocket/{uid}")
public class WebSocketServer {

    //所有在线的客户端连接
    private static Map<String, List<Session>> clientMap = new HashMap<>();

    private static StringRedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @OnOpen
    public void onOpen(@PathParam("uid") String uid, Session session) {
        //检查客户端集合中是否存在当前用户连接 并添加当前客户端连接
        synchronized (clientMap) {
            List<Session> list = new ArrayList<>();
            list.add(session);
            if (clientMap.containsKey(uid)) {
                list.addAll(clientMap.get(uid));
            }
            clientMap.put(uid, list);
        }
        //推送消息
        send(uid);
    }

    @OnClose
    public void onClose(@PathParam("uid") String uid, Session session) {
        //关闭连接时，将对应的客户端对象从Map集合中移除
        synchronized (clientMap) {
            List<Session> sessions = clientMap.get(uid);
            sessions.remove(session);
            if (sessions.size() == 0) {
                clientMap.remove(uid);
            } else {
                clientMap.put(uid, sessions);
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到消息：" + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("出错：" + error);
    }

    /**
     * 利用redis的发布订阅，筛选具备对应WebSocket客户端的节点
     *
     * @param uid
     * @return
     */
    public void send(String uid) {
        //将用户id发布到redis的发布订阅，所有节点监听 检查自己是否有对应连接 再推送消息
        try {
            redisTemplate.convertAndSend(RedisConstants.REDIS_CHANNEL, new String(uid.getBytes(RedisConstants.UTF8), RedisConstants.UTF8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送用户未接收的消息条数
     *
     * @param uid
     * @param count
     */
    public void sendMessageCount(String uid, int count) {
        if (clientMap.containsKey(uid)) {
            List<Session> sessions = clientMap.get(uid);
            for (Session session : sessions) {
                session.getAsyncRemote().sendObject(count);
            }
        }
    }
}
