package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public boolean update(int id, Task task) {
        Session session = sf.openSession();
        var isUpdate = false;
        try {
            session.getTransaction().begin();
            Query<Task> query = session.createQuery(
                    """
                        UPDATE Task
                        SET name = :name, description = :description, done = :done
                        WHERE id = :id
                        """
                        ).setParameter("name", task.getName())
                         .setParameter("description", task.getDescription())
                         .setParameter("done", task.isDone())
                         .setParameter("id", task.getId());
            isUpdate = 0 < query.executeUpdate();
            session.update(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return isUpdate;
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        var isDelete = false;
        try {
            session.getTransaction().begin();
            Query<Task> query = session.createQuery(
                    """
                        DELETE FROM Task
                        WHERE id = :id
                        """
            ).setParameter("id", id);
            isDelete = 0 < query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return isDelete;
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
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Optional<Task> optionalTask = Optional.empty();
        try {
            session.getTransaction().begin();
            Query<Task> query = session.createQuery(
                    "FROM Task WHERE id = :id", Task.class)
                    .setParameter("id", id);
            optionalTask = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return optionalTask;
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

    @Override
    public boolean complete(int id) {
        Session session = sf.openSession();
        var isComplete = false;
        try {
            session.getTransaction().begin();
            Query<Task> query = session.createQuery(
                    "UPDATE Task SET done = true WHERE id = :id")
                    .setParameter("id", id);
            isComplete = 0 < query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return isComplete;
    }
}
