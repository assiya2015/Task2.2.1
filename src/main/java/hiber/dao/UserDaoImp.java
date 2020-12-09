package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      Query queryCar = sessionFactory.getCurrentSession().createQuery("SELECT id FROM Car where model =:model and series =:series");
      queryCar.setParameter("model", model);
      queryCar.setParameter("series", series);
      long id = (long) queryCar.uniqueResult();
      Query queryUser = sessionFactory.getCurrentSession().createQuery("FROM User where car_id =:id");
      queryUser.setParameter("id", id);
      User user = (User) queryUser.uniqueResult();
      return user;
   }

}
