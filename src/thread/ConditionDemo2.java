package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * java 并发编程学习：lock 通信 Condition
 * 参考：http://blog.csdn.net/ghsau/article/details/7481142 Bathtub
 * 2016-02-18
 */
public class ConditionDemo2
{
	class Business
	{
		private boolean bool = true;

		private Lock lock = new ReentrantLock();

		private Condition condition = lock.newCondition();

		public/* synchronized */void main(int loop) throws InterruptedException
		{
			lock.lock();
			try
			{
				while (bool)
				{
					condition.await();// this.wait();
				}
				for (int i = 0; i < 10; i++)
				{
					System.out.println("main thread seq of " + i + " ,loop of " + loop);
				}
				bool = true;
				condition.signal();// this.notify();
			}
			finally
			{
				lock.unlock();
			}
		}

		public/* synchronized */void sub(int loop) throws InterruptedException
		{
			lock.lock();
			try
			{
				while (!bool)
				{
					condition.await();// this.wait();
				}
				for (int i = 0; i < 10; i++)
				{
					System.out.println("sub thread seq of " + i + " ,loop of " + loop);
				}
				//通过设置bool保证sub和main方法交替进行
				bool = false;
				condition.signal();// this.notify();
			}
			finally
			{
				lock.unlock();
			}
		}
	}

	public static void threadExecute(Business business, String threadType)
	{
		for (int i = 0; i < 10; i++)
		{
			try
			{
				if ("main".equals(threadType))
				{
					business.main(i);
				}
				else
				{
					business.sub(i);
				}
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args)
	{
		ConditionDemo2 test = new ConditionDemo2();
		final Business business = test.new Business();
		new Thread(new Runnable()
		{
			public void run()
			{
				threadExecute(business, "sub");
			}
		}).start();
		threadExecute(business, "main");
	}
}
