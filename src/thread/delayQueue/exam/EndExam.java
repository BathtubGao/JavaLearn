package thread.delayQueue.exam;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;

public class EndExam extends Student
{
	private DelayQueue<Student> students;
	
	private CountDownLatch countDownLatch;
	
	private Thread teacherThread;
	
	public EndExam(DelayQueue<Student> students,long workTime,CountDownLatch countDownLatch,Thread teacherThread)
	{
		super("强制收卷",workTime,countDownLatch);
		this.students = students;
		this.countDownLatch = countDownLatch;
		this.teacherThread = teacherThread;
	}
	
	public void run()
	{
		teacherThread.interrupt();
		Student tmpStudent;
		for(Iterator<Student> iterator2 = students.iterator();iterator2.hasNext();)
		{
			tmpStudent = iterator2.next();
			tmpStudent.setForce(true);
			tmpStudent.run();
		}
		countDownLatch.countDown();
	}
}
