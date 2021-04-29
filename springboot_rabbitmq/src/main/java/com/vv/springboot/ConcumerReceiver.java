package com.vv.springboot;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConcumerReceiver {
	

	
	//直连模式的多个消费者，会分到其中一个消费者进行消费。类似task模式
	//通过注入RabbitContainerFactory对象，来设置一些属性，相当于task里的channel.basicQos
	@RabbitListener(queues="helloWorldqueue")
	public void helloWorldReceive(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {

		System.out.println("helloWorld模式 received message : " +message);
		try {
			channel.basicAck(deliveryTag,false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//工作队列模式
    @RabbitListener(queues="work_sb_mq_q")
    public void wordQueueReceiveq1(String message) {

		System.out.println("工作队列模式1 received message : " +message);
    }

    @RabbitListener(queues="work_sb_mq_q")
    public void wordQueueReceiveq2(String message) {

		System.out.println("工作队列模式2 received message : " +message);
    }


	//pub/sub模式进行消息监听
	@RabbitListener(queues="fanout.q1")
	public void fanoutReceiveq1(String message) {

	    System.out.println("发布订阅模式1received message : " +message);
	}
	@RabbitListener(queues="fanout.q2")
	public void fanoutReceiveq2(String message) {

	    System.out.println("发布订阅模式2 received message : " +message);
	}


    //Routing路由模式
    @RabbitListener(queues="direct_sb_mq_q1")
    public void routingReceiveq1(String message) {

	    System.out.println("Routing路由模式routingReceiveq11111 received message : " +message);
    }

    @RabbitListener(queues="direct_sb_mq_q2")
    public void routingReceiveq2(String message) {

	    System.out.println("Routing路由模式routingReceiveq22222 received message : " +message);
    }


    //topic 模式
	//注意这个模式会有优先匹配原则。例如发送routingKey=hunan.IT,那匹配到hunan.*(hunan.IT,hunan.eco),之后就不会再去匹配*.ITd
	@RabbitListener(queues="topic_sb_mq_q1")
	public void topicReceiveq1(String message) {
		System.out.println("Topic模式 topic_sb_mq_q1 received message : " +message);
	}

	@RabbitListener(queues="topic_sb_mq_q2")
	public void topicReceiveq2(String message) {
		System.out.println("Topic模式 topic_sb_mq_q2 received  message : " +message);
	}

	@RabbitListener(queues="dlxQueue")
	public void delayReceiveq(String message) {
		System.out.println("延迟队列 dlxQueue received  message : " +message);
	}
	
}
