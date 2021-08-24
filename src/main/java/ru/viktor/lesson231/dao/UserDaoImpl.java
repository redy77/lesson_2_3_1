package ru.viktor.lesson231.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.viktor.lesson231.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUser(Long id) {

        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.id= :id", User.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void editUser(String name, int age, String email) {
        TypedQuery<User> query = entityManager.createQuery("update User set name = :name, age = :age, email = :email", User.class);
        query.setParameter("name", name);
        query.setParameter("age", age);
        query.setParameter("email", email);
        query.executeUpdate();
    }
    @Override
    public void deleteUser(Long id) {
        entityManager.remove(getUser(id));
    }
}
