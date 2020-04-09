/*
 * Copyright 2016 LinkedIn Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.linkedin.drelephant.schedulers;

import com.linkedin.drelephant.configurations.scheduler.SchedulerConfigurationData;
import java.util.Properties;

import com.linkedin.drelephant.util.Utils;
import org.apache.log4j.Logger;


/**
 * This class provides methods to load information about workflow when you don't have scheduler.
 * We use the paragdim one job is one flow 
 */
public class TodoListScheduler implements Scheduler {

  private static final Logger logger = Logger.getLogger(TodoListScheduler.class);



  public static final String TASK_NAME = "task-flow.task-name";
  public static final String TASK_EXECUTION_DATE = "task-flow.task.execution-date";
  public static final String FLOW_NAME = "task-flow.application-name";
  public static final String FLOW_EXECUTION_DATE = "task-flow.application.execution-date";

  private String schedulerName;
  private String taskId;
  private String taskIdExecutionDate;
  private String flowId;
  private String flowExecutionDate;

  private String jobDefUrl;
  private String jobExecUrl;
  private String flowDefUrl;
  private String flowExecUrl;

  private String jobName;
  private int workflowDepth;


  public TodoListScheduler(String appId, Properties properties, SchedulerConfigurationData schedulerConfData) {
    schedulerName = schedulerConfData.getSchedulerName();
    if (properties != null) {
      loadInfo(appId, properties);
    } else {
      // Use default value of data type
    }
  }

  private void loadInfo(String appId, Properties properties) {
    workflowDepth = 0; // No sub-workflow support

    // task-name
    taskId = properties.getProperty(TASK_EXECUTION_DATE);
    // 2016-06-27T01:30:00
    taskIdExecutionDate = properties.getProperty(TASK_EXECUTION_DATE);
    // application-name
    flowId = properties.getProperty(FLOW_NAME); //
    // 2016-06-27T00:00:00
    flowExecutionDate = properties.getProperty(FLOW_EXECUTION_DATE);
  }

  @Override
  public String getSchedulerName() {
    return schedulerName;
  }

  @Override
  public boolean isEmpty() {
    return taskId == null || taskIdExecutionDate == null || flowId == null || flowExecutionDate == null;
  }

  @Override
  public String getJobDefId() {
    return Utils.formatStringOrNull("%s/%s", flowId, taskId);
  }

  @Override
  public String getJobExecId() {
    return Utils.formatStringOrNull("%s/%s/%s/%s", flowId, flowExecutionDate, taskId, taskIdExecutionDate);
  }

  @Override
  public String getFlowDefId() {
    return Utils.formatStringOrNull("%s", flowId);
  }

  @Override
  public String getFlowExecId() {
    return Utils.formatStringOrNull("%s/%s", flowId, flowExecutionDate);
  }
  
  @Override
  public String getJobDefUrl() {
    return jobDefUrl;
  }

  @Override
  public String getJobExecUrl() {
    return jobExecUrl;
  }

  @Override
  public String getFlowDefUrl() {
    return flowDefUrl;
  }

  @Override
  public String getFlowExecUrl() {
    return flowExecUrl;
  }

  @Override
  public int getWorkflowDepth() {
    return workflowDepth;
  }

  @Override
  public String getJobName() {
    return jobName;
  }
}
