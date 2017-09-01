package byit.aladdin.workBook.entity;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MyDetailQuartzJobBean extends QuartzJobBean {
	protected Logger logger = Logger.getLogger(getClass());
	private String targetObject;
	private String targetMethod;
	private ApplicationContext ctx;
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.debug("=============进入MyDetailQuartzJobBean类=============");
			//通过上下文获取  
			//JobKey jobKey = context.getJobDetail().getKey();

			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
			//System.out.println("集群列子1：" + jobKey + " 在 " + dateFormat.format(new Date()) + " 时运行");

			//System.out.println("targetObject===>" + targetObject + "*******" + "targetMethod==>" + targetMethod);

			//logger.info("execute [" + targetObject + "] at once>>>>>>");
			Object otargetObject = ctx.getBean(targetObject);
			Method m = null;
			try {
				m = otargetObject.getClass().getMethod(targetMethod, new Class[] { JobExecutionContext.class });
				m.invoke(otargetObject, new Object[] { context });
			} catch (SecurityException e) {
				logger.error(e);
			} catch (NoSuchMethodException e) {
				logger.error(e);
			}

		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.ctx = applicationContext;
	}

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

	/*
	 * @Override public void execute(JobExecutionContext context) throws JobExecutionException { try { //通过上下文获取 JobKey jobKey =
	 * context.getJobDetail().getKey();
	 * 
	 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒"); System.out.println("集群列子1："+ jobKey + " 在 " +
	 * dateFormat.format(new Date())+" 时运行");
	 * 
	 * System.out.println("targetObject===>"+targetObject+"targetMethod==>"+targetMethod); logger.info("execute [" + targetObject + "] at once>>>>>>"
	 * ); Object otargetObject = ctx.getBean(targetObject); Method m = null; try { m = otargetObject.getClass().getMethod(targetMethod, new Class[]
	 * {JobExecutionContext.class}); m.invoke(otargetObject, new Object[] {context}); } catch (SecurityException e) { logger.error(e); } catch
	 * (NoSuchMethodException e) { logger.error(e); }
	 * 
	 * } catch (Exception e) { throw new JobExecutionException(e); } }
	 */
}