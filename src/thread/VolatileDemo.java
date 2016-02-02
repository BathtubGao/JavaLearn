package thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * java 并发编程学习：volatile无法保证原子性
 * 参考：http://www.cnblogs.com/dolphin0520/p/3920373.html
 * Bathtub 2016-02-02
 */
public class VolatileDemo
{
	//public volatile int inc = 0;
	//AtomicInteger，一个提供原子操作的Integer的类
	public  AtomicInteger inc = new AtomicInteger();
	
	public void increase() 
	{
		//inc++;
		inc.getAndIncrement();
	}
	
	public static void main(String[] args)
	{
		final VolatileDemo test = new VolatileDemo();
		for(int i=0;i<10;i++)
		{
			new Thread() {
				public void run() 
				{
					for(int j=0;j<1000;j++)
					{
						test.increase();
					}
				}
			}.start();
		}
		
		//保证前面的线程都执行完
		while(Thread.activeCount() > 1)
		{
			Thread.yield();
		}
		System.out.println(test.inc);
	}
}
