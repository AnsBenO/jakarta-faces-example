package com.ansbeno.services;

import java.util.List;
import java.util.Optional;

// import jakarta.faces.bean.ApplicationScoped;

import com.ansbeno.entities.Product;
import com.ansbeno.util.HibernateUtil;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.SelectionQuery;

// @ManagedBean(name = "productService")
// @SessionScoped
// @ManagedBean(name = "productService")
// @SessionScoped
@Named
@ApplicationScoped
public class ProductService implements IService<Product, Long> {

      @Override
      public List<Product> findAll() {
            List<Product> products = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                  Query<Product> query = session.createQuery("from Product", Product.class);
                  products = query.getResultList();
            } catch (HibernateException e) {
                  e.printStackTrace();
            }
            return products;
      }

      @Override
      public List<Product> findByKeyword(String keyword) {

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                  SelectionQuery<Product> query = session.createSelectionQuery(
                              "from Product where LOWER(name) like :keyword",
                              Product.class);
                  query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
                  return query.getResultList();
            }
      }

      @Override
      public Optional<Product> findById(Long id) {
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                  Product product = session.get(Product.class, id);
                  return Optional.ofNullable(product);
            } catch (HibernateException e) {
                  e.printStackTrace();
                  return Optional.empty();
            }
      }

      @Override
      public Product save(Product entity) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                  session.beginTransaction();
                  session.merge(entity);
                  session.getTransaction().commit();
                  return entity;
            } catch (HibernateException e) {
                  e.printStackTrace();
                  throw e;
            }
      }

      @Override
      public void deleteById(Long id) {
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                  session.beginTransaction();
                  session.createMutationQuery("delete Product where id = :id")
                              .setParameter("id", id)
                              .executeUpdate();
                  session.getTransaction().commit();
            } catch (HibernateException e) {
                  e.printStackTrace();
            }
      }

      @Override
      public void delete(Product entity) {
            try {
                  Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                  session.beginTransaction();
                  session.remove(entity);
                  session.getTransaction().commit();
            } catch (HibernateException e) {
                  e.printStackTrace();
            }
      }

      public List<Product> findByCategory(String category) {
            List<Product> products = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                  Query<Product> query = session.createQuery("from Product where category.name = :category",
                              Product.class);
                  query.setParameter("category", category);
                  products = query.getResultList();
            } catch (HibernateException e) {
                  e.printStackTrace();
            }
            return products;
      }

}
