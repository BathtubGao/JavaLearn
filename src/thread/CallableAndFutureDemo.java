package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 	java 并发编程学习：Callable&Future
 * 	参考：http://www.cnblogs.com/dolphin0520/p/3949310.html
 * Bathtub 2016-01-26
 */
public class CallableAndFutureDemo
{
	
	public static void main(String[] args)
	{
		
		ExecutorService executor = Executors.newCachedThreadPool();
		Task task = new Task();
		Future<Integer> result = executor.submit(task);
		executor.shutdown();
		
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("主线程在执行任务");
		
		try
		{
			System.out.println("task的运行结果是：" + result.get());
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("所有任务执行完毕");
	}
	
	static class Task implements Callable<Integer>
	{
		public Integer call() throws InterruptedException 
		{
			System.out.println("子线程在进行计算");
			Thread.sleep(3000);
			int sum = 0;
			for(int i = 0;i < 100;i++)
			{
				sum += i;
			}
			return sum;
		}
	}
}
