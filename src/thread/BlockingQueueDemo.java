package thread;

import java.util.PriorityQueue;

/**
 * java 并发编程学习：阻塞队列
 * 参考：http://www.cnblogs.com/dolphin0520/p/3932906.html 
 * Bathtub 2016-02-16
 */
public class BlockingQueueDemo
{
	private int queueSize = 10;

	private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);

	class Consumer extends Thread
	{
		public void run()
		{
			consume();
		}

		private void consume()
		{
			while(true)
			{
				synchronized(queue) 
				{
					while(queue.size() == 0)
					{
						try
						{
							System.out.println("队列空，等待数据");
							queue.wait();
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
							queue.notify();
						}
					}
					//移走队首元素
					queue.poll();
					queue.notify();
					System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");
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
			while(true)
			{
				synchronized(queue)
				{
					while(queue.size() == queueSize)
					{
						System.out.println("队列满，等待有空余空间");
						try
						{
							queue.wait();
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
							queue.notify();
						}
					}
					//每次插入一个元素
					queue.offer(1);
					queue.notify();
					System.out.println("向队列中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		BlockingQueueDemo test = new BlockingQueueDemo();
		Producer producer = test.new Producer();
		Consumer consumer = test.new Consumer();
		producer.start();
		consumer.start();
	}
}
