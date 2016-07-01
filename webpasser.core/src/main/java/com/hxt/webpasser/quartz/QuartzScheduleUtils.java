/*
 * 系统名称: 
 * 模块名称: webpasser.core
 * 类 名 称: QuartzScheduleUtils.java
 *   
 */
package com.hxt.webpasser.quartz;

import java.util.Map;

import org.quartz.*;
/**
 * 功能说明:  <br>
 * 系统版本: v1.0 <br>
 * 作者: hanxuetong <br>
 * ======== ====== ============================================ <br>
 * 
 */
public class QuartzScheduleUtils {

	
	 /**
     * 获取触发器key
     * 
     * @param jobName
     * @param jobGroup
     * @return
     */
    public static TriggerKey getTriggerKey(String jobName, String jobGroup) {

        return TriggerKey.triggerKey(jobName, jobGroup);
    }

    /**
     * 获取表达式触发器
     *
     * @param scheduler the scheduler
     * @param jobName the job name
     * @param jobGroup the job group
     * @return cron trigger
     * @throws SchedulerException 
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            return (CronTrigger) scheduler.getTrigger(triggerKey);
        } catch (SchedulerException e) {
            throw new SchedulerException("获取定时任务CronTrigger出现异常");
        }
    }


    /**
     * 创建定时任务
     *
     * @param scheduler the scheduler
     * @param jobName the job name
     * @param jobGroup the job group
     * @param cronExpression the cron expression
     * @param isSync the is sync
     * @param param the param
     * @throws SchedulerException 
     */
    public static void createScheduleJob(Scheduler scheduler, String jobName, String jobGroup,
                                         String cronExpression, Class<? extends Job> jobClass ,Map<String,Object> jobClassParam) throws SchedulerException {
        //同步或异步
      //  Class<? extends Job> jobClass = isSync ? JobSyncFactory.class : JobFactory.class;

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();

        if(jobClassParam!=null&&jobClassParam.size()>0){
            //放入参数，运行时的方法可以获取
        	for(Map.Entry<String, Object> entry:jobClassParam.entrySet()){
        		jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
        	}
            
        }

        //表达式调度构建器  加上 withMisfireHandlingInstructionDoNothing防止启动就运行
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
            .withSchedule(scheduleBuilder).build();
        
        
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new SchedulerException("创建定时任务失败");
        }
    }

    /**
     * 运行一次任务
     * 
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException 
     */
    public static void runOnce(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            throw new SchedulerException("运行一次定时任务失败");
        }
    }

    /**
     * 暂停任务
     * 
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException 
     */
    public static void pauseJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new SchedulerException("暂停定时任务失败");
        }
    }

    /**
     * 恢复任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException 
     */
    public static void resumeJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new SchedulerException("暂停定时任务失败");
        }
    }

    /**
     * 获取jobKey
     *
     * @param jobName the job name
     * @param jobGroup the job group
     * @return the job key
     */
    public static JobKey getJobKey(String jobName, String jobGroup) {

        return JobKey.jobKey(jobName, jobGroup);
    }


    /**
     * 更新定时任务
     *
     * @param scheduler the scheduler
     * @param jobName the job name
     * @param jobGroup the job group
     * @param cronExpression the cron expression
     * @param isSync the is sync
     * @param param the param
     * @throws SchedulerException 
     */
    public static void updateScheduleJob(Scheduler scheduler, String jobName, String jobGroup,
                                         String cronExpression) throws SchedulerException {

        //同步或异步
//        Class<? extends Job> jobClass = isSync ? JobSyncFactory.class : JobFactory.class;

        try {
//            JobDetail jobDetail = scheduler.getJobDetail(getJobKey(jobName, jobGroup));

//            jobDetail = jobDetail.getJobBuilder().ofType(jobClass).build();

            //更新参数 实际测试中发现无法更新
//            JobDataMap jobDataMap = jobDetail.getJobDataMap();
//            jobDataMap.put(ScheduleJobVo.JOB_PARAM_KEY, param);
//            jobDetail.getJobBuilder().usingJobData(jobDataMap);

            TriggerKey triggerKey = getTriggerKey(jobName, jobGroup);

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder)
                .build();

            //按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            throw new SchedulerException("更新定时任务失败");
        }
    }

    /**
     * 删除定时任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException 
     */
    public static void deleteScheduleJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        try {
            scheduler.deleteJob(getJobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            throw new SchedulerException("删除定时任务失败");
        }
    }
	
}
