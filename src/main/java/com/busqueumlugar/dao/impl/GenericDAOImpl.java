package com.busqueumlugar.dao.impl;

import com.busqueumlugar.dao.GenericDAO;








import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class GenericDAOImpl<T, Type extends Serializable>  implements  GenericDAO<T, Type> {
	
	private Class<T> persistentClass;	
	
	@Autowired
    private SessionFactory sessionFactory;    
	
	public Session session(){			
        return  this.sessionFactory.getCurrentSession();
    }
	
	public Session openSession(){		
        return  this.sessionFactory.openSession();
    }
	
	public GenericDAOImpl(Class<T> persistentClass){
		super();
		this.persistentClass = persistentClass;
	}
	
	
	public void beginTransaction() {
		session().beginTransaction();
	}

	
	public void commitTransaction() {
		session().beginTransaction().commit();
	}

	
	
	public void save(T entity) {
	     Session ss = this.sessionFactory.openSession();
	     Transaction tx = null ;
	     try {
	         	tx = ss.beginTransaction();         
	            ss. saveOrUpdate(entity);
	         	ss.flush();
	            tx.commit();
	       }
	      catch (Exception e) {
	         if (tx!=null) tx.rollback();
	         throw e;
	       }
	        finally {
	     ss .close();
	      }		
	}
	
	public void update(T entity) {
	     Session ss = this.sessionFactory.openSession();
	     Transaction tx = null ;
	     try {
	         	tx = ss.beginTransaction();
	         	ss.merge(entity);
	         	ss.flush();
	            tx.commit();
	       }
	      catch (Exception e) {
	         if (tx!=null) tx.rollback();
	         throw e;
	       }
	        finally {
	     ss .close();
	      }		
	}

	
	public void delete(Class<T> persistentClass, Long id) {
		
		 Session ss = null;
	     Transaction tx = null ;
	     try {	
	    	 	ss = this.sessionFactory.openSession();	    	 	
	    	 	Criteria criteria = ss.createCriteria(persistentClass);
	         	criteria.add(Restrictions.eq("id", id));
	         	@SuppressWarnings("unchecked")
				T entity = (T) criteria.uniqueResult();
	         	ss.delete(entity);
	         	ss.flush();
	       }
	      catch (Exception e) {
	    	  this.sessionFactory.getCurrentSession().beginTransaction().rollback();
	         throw e;
	       }
	        finally {
	     ss .close();
	      }
	}
	
	public void delete(T entity) {
		
		Session ss = this.sessionFactory.openSession();
	     Transaction tx = null ;
	     try {	
	         	tx = ss.beginTransaction();	         	
	            ss.delete(entity);
	            tx.commit();
	       }
	      catch (Exception e) {
	         if (tx!=null) tx.rollback();
	         throw e;
	       }
	        finally {
	     ss .close();
	      }
	}

	

	public List<T> listAll() {
		
		Session ss = this.sessionFactory.openSession();
	     Transaction tx = null ;
	     try {
	         	tx = ss.beginTransaction();         
	         	Criteria criteria = session().createCriteria(persistentClass);
	         	return criteria.list(); 
	            
	       }
	      catch (Exception e) {
	         if (tx!=null) tx.rollback();
	         throw e;
	       }
	        finally {
	        		
	        	ss .close();
	      }
		
		/*session().beginTransaction();
		Criteria criteria = session().createCriteria(persistentClass);
		return criteria.list();*/
	}

}
