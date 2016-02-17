package thread;

import java.util.concurrent.ArrayBlockingQueue;

import thread.BlockingQueueDemo.Consumer;
import thread.BlockingQueueDemo.Producer;

/**
 * java 并发编程学习：阻塞队列 ArrayBlockingQueue
 * 参考：http://www.cnblogs.com/dolphin0520/p/3932906.html 
 * Bathtub 2016-02-16
 */
public class ArrayBlockingQueueDemo
{
	private int queueSize = 10;

	private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(queueSize);

	class Consumer extends Thread
	{
		public void run()
		{
			consume();
		}

		private void consume()
		{
			while (true)
			{
				// 移走队首元素
				try
				{
					queue.take();
					System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	class Producer extends Thread
	{
		public void run()
		{
			produce();
		}

		private void produce()
		{
			while (true)
			{
				try
				{
					queue.put(1);
					System.out.println("向队列中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args)
	{
		ArrayBlockingQueueDemo test = new ArrayBlockingQueueDemo();
		Producer producer = test.new Producer();
		Consumer consumer = test.new Consumer();
		producer.start();
		consumer.start();
	}
}
