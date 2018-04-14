package com.ticket.czc.tickets.service.implservice;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderDeadlineCheck implements Job {


    private RemoveOverDueOrder removeOverDueOrder=new RemoveOverDueOrder();
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        removeOverDueOrder.removeOverDueOrders();
    }
}
