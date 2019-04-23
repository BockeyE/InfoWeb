package com.InfoWeb.demo.async;

import com.InfoWeb.demo.util.LocalQueue;
import com.InfoWeb.demo.util.QueueKeyUtil;
import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class EventProducer {

    //    @Autowired
//    private JedisAdapter jedisAdapter;
    @Resource
    private LocalQueue localQueue;

    public boolean startEvent(EventModel eventModel){
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = QueueKeyUtil.getEventQueueKey();
            localQueue.lpush(key,json);
            System.out.println("产生一个异步事件：" + eventModel.getType());
            return true;
        }catch (Exception e){
            return  false;
        }
    }
}
