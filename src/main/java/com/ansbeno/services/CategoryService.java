package com.ansbeno.services;

import java.util.List;
import java.util.Optional;

import com.ansbeno.entities.Category;
import com.ansbeno.util.HibernateUtil;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.SelectionQuery;

@Named
@ApplicationScoped
public class CategoryService implements IService<Category, Long> {

      @Override
      public List<Category> findAll() {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                  Query<Category> query = session.createQuery("from Category", Category.class);
                  return query.getResultList();
            }
      }

      @Override
      public List<Category> findByKeyword(String keyword) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                  SelectionQuery<Category> query = session.createSelectionQuery(
                              "from Category where lower(name) like :keyword",
                              Category.class);
                  query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
                  return query.getResultList();
            }
      }

      @Override
      public Optional<Category> findById(Long id) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                  Category category = session.get(Category.class, id);
                  return Optional.ofNullable(category);
            }
      }

      public Optional<Category> findByName(String name) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                  SelectionQuery<Category> query = session.createSelectionQuery(
                              "from Category where lower(name) = :name",
                              Category.class);
                  query.setParameter("name", name.toLowerCase());
                  Optional<Category> category = query.uniqueResultOptional();
                  return category;
            }
      }

      @Override
      public Category save(Category entity) {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                  transaction = session.beginTransaction();
                  session.merge(entity);
                  transaction.commit();
                  return entity;
            } catch (Exception e) {
                  if (transaction != null) {
                        transaction.rollback();
                  }
                  e.printStackTrace();
                  throw e;
            }
      }

      @Override
      public void deleteById(Long id) {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                  try {
                        transaction = session.beginTransaction();
                        session.createMutationQuery("delete Category where id = :id")
                                    .setParameter("id", id)
                                    .executeUpdate();
                        transaction.commit();
                  } catch (Exception e) {
                        if (transaction != null) {
                              transaction.rollback();
                        }
                        e.printStackTrace();
                  }
            } catch (Exception e) {
                  e.printStackTrace();
                  throw e;
            }
      }

      @Override
      public void delete(Category entity) {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                  transaction = session.beginTransaction();
                  session.remove(entity);
                  transaction.commit();
            } catch (Exception e) {
                  if (transaction != null) {
                        transaction.rollback();
                  }
                  e.printStackTrace();
                  throw e;
            }
      }

      public List<String> findAllNames() {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {

                  return session.createSelectionQuery("select name from Category", String.class).list();

            }
      }

}
