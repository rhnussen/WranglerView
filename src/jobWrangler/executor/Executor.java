package jobWrangler.executor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import jobWrangler.job.Job;

/**
 * Base class for things that can execute jobs. �
 * @author brendanofallon
 *
 */
public abstract class Executor {

	protected List<ExecutorListener> listeners = new ArrayList<ExecutorListener>();
	
	/**
	 * The number of jobs currently contained in this executor
	 * @return
	 */
	public abstract int getJobCount();
	
	/**
	 * Should return true if we can immediately begin running the given job
	 * @param job
	 * @return
	 */
	public abstract boolean canSubmitJob(Job job);
	
	/**
	 * Attempt to immediately begin running the given job. Jobs may be rejected, for
	 * instance, if something else submitted a job right before the call did
	 * @param job
	 */
	public abstract void runJob(Job job);
	
	
	public void addListener(ExecutorListener l) {
		listeners.add(l);
	}
	
	public boolean removeListener(ExecutorListener l) {
		return listeners.remove(l);
	}
	
	protected void fireEvent(ExecutorEvent evt) {
		for(ExecutorListener l : listeners) {
			l.executorUpdated(evt);
		}
	}
	
	
	class RunningJob extends SwingWorker {

		final Job job;
		
		public RunningJob(Job job) {
			this.job = job;
		}
		
		@Override
		protected Object doInBackground() throws Exception {
			job.runJob();
			return job;
		}
		
		public Job getJob() {
			return job;
		}
		
		
	}
	
}