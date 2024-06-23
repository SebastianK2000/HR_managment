package org.example.dao;

import org.example.model.JobHistory;
import org.example.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class JobHistoryDAO {

    public void saveJobHistory(JobHistory jobHistory) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(jobHistory);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<JobHistory> getAllJobHistories() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from JobHistory", JobHistory.class).list();
        }
    }

}
