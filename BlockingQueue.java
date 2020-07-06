package edu.utdallas.taskExecutor;

public interface BlockingQueue
{
	Task buffer[N];
	int nextin, nextout;
	int count;
	Object notfull, notempty; // Monitors used for synchronization

	void put(Task task) 
	{
	  if(count == N) notfull.wait();   // Buffer is full, wait for take

	  synchronized(this) {
	    buffer[nextin] = X;
	    nextin = (nextin + 1) % N;
	    count++;
	    notempty.notify(); // Signal waiting take threads
	  }
	}

	Task take() 
	{
	  if(count == 0) notempty.wait(); // Buffer is empty, wait for put

	  synchronized(this) {
	    Task result = buffer[nextout];
	    nextout = (nextout + 1) % N;
	    count--;
	    notfull.notify(); // Signal waiting put threads
	    return result;
	  }
	}

}
