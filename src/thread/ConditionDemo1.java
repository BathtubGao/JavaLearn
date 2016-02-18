package thread;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * java 并发编程学习：lock 通信 Condition
 * 参考：http://www.cnblogs.com/dolphin0520/p/3920385.html
 * Bathtub 2016-02-17
 */
public class ConditionDemo1
{
	private int queueSize = 10;

	private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);

	private Lock lock = new ReentrantLock();

	//写线程条件
	private Condition notFull = lock.newCondition();

	//读线程条件
	private Condition notEmpty = lock.newCondition();

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
				lock.lock();
				try
				{
					while (queue.size() == 0)
					{
						try
						{
							System.out.println("队列空，等待数据");
							notEmpty.await();
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
					queue.poll();
					notFull.signal();
					System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");
				}
				finally
				{
					lock.unlock();
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
				lock.lock();
				try
				{
					while(queue.size() == queueSize)
					{
						try
						{
							System.out.println("队列满，等待有空余空间");
							notFull.await();
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
					queue.offer(1);
					notEmpty.signal();
					System.out.println("向队列中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
				}
				finally
				{
					lock.lock();
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		ConditionDemo1 test = new ConditionDemo1();
		Producer producer = test.new Producer();
		Consumer consumer = test.new Consumer();
		producer.start();
		consumer.start();
	}
}
