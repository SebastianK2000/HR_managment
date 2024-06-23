package org.example.service;

import org.example.dao.JobHistoryDAO;
import org.example.model.JobHistory;

import java.util.List;

public class JobHistoryService {

    private JobHistoryDAO jobHistoryDAO = new JobHistoryDAO();

    public void saveJobHistory(JobHistory jobHistory) {
        jobHistoryDAO.saveJobHistory(jobHistory);
    }

    public List<JobHistory> getAllJobHistories() {
        return jobHistoryDAO.getAllJobHistories();
    }
}