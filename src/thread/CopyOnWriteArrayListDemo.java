package thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * java 并发编程学习：CopyOnWriteArrayList
 * 参考：http://www.cnblogs.com/dolphin0520/p/3938914.html; http://blog.csdn.net/imzoer/article/details/9751591
 * Bathtub  2016-02-16
 */
public class CopyOnWriteArrayListDemo
{
	public static void main(String[] args) throws InterruptedException
	{
		
		final CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
		Thread t = new Thread(new Runnable() {
			int count = -1;
			
			public void run() {
				while(true) {
					list.add(count++ + "");
				}
			}
		});
		
		t.setDaemon(true);
		t.start();
		Thread.currentThread().sleep(3);
		for(String s : list)
		{
			//list的hashCode不一样，说明list已不是原来的对象
			System.out.println(list.hashCode());
			System.out.println(s);
		}
	}
}
