package run.onco.batch.api.cron;

public interface BaseCron {

	public void register();

	public void reschedule(String cronExpression);

	public void pause();

	public void resume();

}
