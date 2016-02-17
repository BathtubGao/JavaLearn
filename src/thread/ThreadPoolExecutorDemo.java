package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * java 并发编程学习：线程池学习
 * 参考：http://blog.csdn.net/u012516914/article/details/38715935 Bathtub
 * 2016-02-17
 */
public class ThreadPoolExecutorDemo
{
	public static void main(String[] args)
	{
		ExecutorService threadPool = Executors.newSingleThreadExecutor();

		for (int i = 0; i <= 4; i++)
		{
			final int task = i;
			threadPool.execute(new Runnable()
			{
				public void run()
				{
					for (int j = 0; j <= 10; j++)
					{
						try
						{
							Thread.sleep(20);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for task of "
								+ task);
					}
				}
			});
		}

		Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable()
		{
			public void run()
			{
				System.out.println("boombing");
			}
		}, 5, 2, TimeUnit.SECONDS);

	}
}
