package org.example.repository;

import org.example.configuration.HibernateConfiguration;
import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class UserRepository implements AutoCloseable{

    private SessionFactory sessionFactory = HibernateConfiguration.createSessionFactory();
    private EntityManagerFactory entityManagerFactory = HibernateConfiguration.entityManagerFactory();

    public void save(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(user);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Optional<User> findByEmail(String email) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.createQuery("select u from User u " + " where u.email = :email", User.class)
                .setParameter("email", email).getSingleResult();

        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(user);
    }

    public Boolean existsByEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("select case when count(c) > 0 " +
                "then true" +
                " else false end" +
                " from User c where  c.email = :email", Boolean.class)
                .setParameter("email", email);

        Boolean result = (Boolean) query.getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

    public List<User> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<User> users = entityManager.createQuery("select u from User u", User.class).getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();
        return users;
    }

    @Override
    public void close() throws Exception {
        sessionFactory.close();
        entityManagerFactory.close();
    }
}
