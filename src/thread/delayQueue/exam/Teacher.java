package thread.delayQueue.exam;

import java.util.concurrent.DelayQueue;

public class Teacher implements Runnable
{

	private DelayQueue<Student> students;

	public Teacher(DelayQueue<Student> students)
	{
		this.students = students;
	}

	@Override
	public void run()
	{
		try
		{
			System.out.println("test start!");
			while (!Thread.interrupted())
			{
				students.take().run();
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
