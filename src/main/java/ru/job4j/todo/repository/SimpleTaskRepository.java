package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SimpleTaskRepository implements TaskRepository {
    private final SessionFactory sf;

    public SimpleTaskRepository(TaskStoreRepository taskStoreRepository) {
        this.sf = taskStoreRepository.getSF();
    }

    @Override
    public Task create(Task task) {
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            task.setCreated(LocalDateTime.now());
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public void update(int id, Task task) {
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            session.update(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) {
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            Task task = new Task();
            task.setId(id);
            session.delete(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        List<Task> taskList = new ArrayList<>();
        try {
            session.getTransaction().begin();
            Query<Task> query = session.createQuery(
                    "FROM Task", Task.class);
            taskList = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return taskList;
    }

    @Override
    public Task findById(int id) {
        Session session = sf.openSession();
        Task task = null;
        try {
            session.getTransaction().begin();
            Query<Task> query = session.createQuery(
                    "FROM Task WHERE id = :id", Task.class)
                    .setParameter("id", id);
            task = query.getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public List<Task> findDoneTrue() {
        Session session = sf.openSession();
        List<Task> taskList = new ArrayList<>();
        try {
            session.getTransaction().begin();
            Query<Task> query = session.createQuery(
                    "FROM Task WHERE done = true", Task.class);
            taskList = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return taskList;
    }

    @Override
    public List<Task> findDoneFalse() {
        Session session = sf.openSession();
        List<Task> taskList = new ArrayList<>();
        try {
            session.getTransaction().begin();
            Query<Task> query = session.createQuery(
                    "FROM Task WHERE done = false", Task.class);
            taskList = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return taskList;
    }
}
