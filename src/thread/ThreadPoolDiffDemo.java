package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * java 并发编程学习：四种流程池区别比较
 * 参考：http://blog.csdn.net/ghsau/article/details/7443324
 * Bathtub 2016-02-18
 */
public class ThreadPoolDiffDemo
{
	public static void main(String[] args)
	{
		ExecutorService threadPool = Executors.newFixedThreadPool(3);
//		ExecutorService threadPool = Executors.newCachedThreadPool();
//		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		
		for(int i = 1;i < 5;i++)
		{
			final int taskID = i;
			threadPool.execute(new Runnable() 
			{
				public void run()
				{
					for(int i=1;i<5;i++)
					{
						try
						{
							Thread.sleep(20);
						}
						catch (InterruptedException e)
						{
							// TODO: handle exception
						}
						System.out.println("第" + taskID + "次任务的第" + i + "次执行");
					}
				}
			});
		}
		//任务执行完毕，关闭线程池 
		threadPool.shutdown();
	}
}
