package com.InfoWeb.demo.async;

import com.InfoWeb.demo.util.LocalQueue;
import com.InfoWeb.demo.util.QueueKeyUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EventDispatcher implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventDispatcher.class);

    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    private ApplicationContext applicationContext;

    //    @Autowired
//    private JedisAdapter jedisAdapter;
    @Resource
    private LocalQueue localQueue;

    //事件路由
    @Override
    public void afterPropertiesSet() throws Exception {

        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                for (EventType type : eventTypes) {
                    if (!config.containsKey(type)) {
                        config.put(type, new ArrayList<EventHandler>());
                        System.out.println("添加一个新的 ：" + type);
                    }

                    //注册每个事件的处理函数
                    config.get(type).add(entry.getValue());
                    System.out.println("注册每个事件的处理函数 ：" + type + " " + entry.getValue());
                }
            }
        }

        //启动线程去消费事件
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //从队列一直消费
                while (true) {
                    String key = QueueKeyUtil.getEventQueueKey();
                    List<String> messages = localQueue.brpop(60, key);
                    System.out.println("消费队列: messages size " + messages.size());
                    //第一个元素是队列名字
                    if (messages.size() > 0) {
                        for (String message : messages) {
                            if (key.equals(message) || message == null) {
                                continue;
                            }

                            EventModel eventModel = JSON.parseObject(message, EventModel.class);
                            //找到这个事件的处理handler列表
                            System.out.println("找到这个事件的处理handler列表 : " + eventModel.getType());
                            if (!config.containsKey(eventModel.getType())) {
                                logger.error("不能识别的事件");
                                continue;
                            }

                            for (EventHandler handler : config.get(eventModel.getType())) {
                                System.out.println("处理事件 ： " + eventModel.getType() + " " + handler.getClass());
                                handler.doHandler(eventModel);
                            }
                        }
                    }
                }
            }
        });

        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
