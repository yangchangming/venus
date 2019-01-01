
package venus.frames.orm.hibernate.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import venus.VenusHelper;
import venus.frames.orm.hibernate.PageableHibernateTemplate;

/**
 * Convenient super class for Hibernate data access objects.
 *
 * <p>Requires a SessionFactory to be set, providing a HibernateTemplate
 * based on it to subclasses. Can alternatively be initialized directly via
 * a HibernateTemplate, to reuse the latter's settings like SessionFactory,
 * exception translator, flush mode, etc.
 *
 * <p>This base class is mainly intended for HibernateTemplate usage
 * but can also be used when working with SessionFactoryUtils directly,
 * e.g. in combination with HibernateInterceptor-managed Sessions.
 * Convenience <code>getSession</code> and <code>closeSessionIfNecessary</code>
 * methods are provided for that usage style.
 * 
 * <p>This class will create its own PageableHibernateTemplate if only a SessionFactory
 * is passed in. The allowCreate flag on that HibernateTemplate will be true by
 * default. A custom HibernateTemplate instance can be used through overriding
 * <code>createHibernateTemplate</code>.
 *
 * @author Sundaiyong
 * @since 05.01.2005
 */
public abstract class HibernateDaoSupport implements InitializingBean {

	protected final Log logger = LogFactory.getLog(getClass());

	private PageableHibernateTemplate hibernateTemplate;

	private static final String DEFAULT_SESSIONFACTORY_BEAN_ID = "sessionFactory";

	/**
	 * Set the Hibernate SessionFactory to be used by this DAO.
	 * Will automatically create a HibernateTemplate for the given SessionFactory.
	 * @see #createHibernateTemplate
	 * @see #setHibernateTemplate
	 */
	public final void setSessionFactory(SessionFactory sessionFactory) {
	  this.hibernateTemplate = createHibernateTemplate(sessionFactory);
	}

	/**
	 * Create a HibernateTemplate for the given SessionFactory.
	 * Only invoked if populating the DAO with a SessionFactory reference!
	 * <p>Can be overridden in subclasses to provide a HibernateTemplate instance
	 * with different configuration, or a custom HibernateTemplate subclass.
	 * @param sessionFactory the Hibernate SessionFactory to create a HibernateTemplate for
	 * @return the new HibernateTemplate instance
	 * @see #setSessionFactory
	 */
	protected PageableHibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
		return new PageableHibernateTemplate(sessionFactory);
	}

	/**
	 * Return the Hibernate SessionFactory used by this DAO.
	 */
	public final SessionFactory getSessionFactory() {
		if (this.hibernateTemplate != null ){
    		if(this.hibernateTemplate.getSessionFactory()!=null ){
    			return this.hibernateTemplate.getSessionFactory();
    		}else{
    			SessionFactory sf = (SessionFactory) VenusHelper.getBean(DEFAULT_SESSIONFACTORY_BEAN_ID);
    		
    			this.hibernateTemplate.setSessionFactory(sf);
    			
    			return getHibernateTemplate().getSessionFactory();
    		}
    	
    	}else{
    		
    		SessionFactory sf = (SessionFactory) VenusHelper.getBean(DEFAULT_SESSIONFACTORY_BEAN_ID);
    		
    		setHibernateTemplate(createHibernateTemplate(sf));
			
			return getHibernateTemplate().getSessionFactory();
   	
    	}
	}

	/**
	 * Set the HibernateTemplate for this DAO explicitly,
	 * as an alternative to specifying a SessionFactory.
	 * @see #setSessionFactory
	 */
	public final void setHibernateTemplate(PageableHibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * Return the HibernateTemplate for this DAO,
	 * pre-initialized with the SessionFactory or set explicitly.
	 */
	public final PageableHibernateTemplate getHibernateTemplate() {
		
		if (this.hibernateTemplate == null ){
    		
    		SessionFactory sf = (SessionFactory) VenusHelper.getBean(DEFAULT_SESSIONFACTORY_BEAN_ID);
    		
    		setHibernateTemplate(createHibernateTemplate(sf));
   	
    	}
		
		return hibernateTemplate;
	}

	public final void afterPropertiesSet() throws Exception {
		if (this.hibernateTemplate == null) {
			throw new IllegalArgumentException("sessionFactory or hibernateTemplate is required");
		}
		initDao();
	}

	/**
	 * Subclasses can override this for custom initialization behavior.
	 * Gets called after population of this instance's bean properties.
	 * @throws Exception if initialization fails
	 */
	protected void initDao() throws Exception {
	}


	/**
	 * Get a Hibernate Session, either from the current transaction or
	 * a new one. The latter is only allowed if the "allowCreate" setting
	 * of this bean's HibernateTemplate is true.
	 * @return the Hibernate Session
	 * @throws DataAccessResourceFailureException if the Session couldn't be created
	 * @throws IllegalStateException if no thread-bound Session found and allowCreate false
	 */
	protected final Session getSession()
	    throws DataAccessResourceFailureException, IllegalStateException {
		return getSession(this.hibernateTemplate.isAllowCreate());
	}

	/**
	 * Get a Hibernate Session, either from the current transaction or
	 * a new one. The latter is only allowed if "allowCreate" is true.
	 * @param allowCreate if a new Session should be created if no thread-bound found
	 * @return the Hibernate Session
	 * @throws DataAccessResourceFailureException if the Session couldn't be created
	 * @throws IllegalStateException if no thread-bound Session found and allowCreate false
	 */
	protected final Session getSession(boolean allowCreate)
	    throws DataAccessResourceFailureException, IllegalStateException {
		return (!allowCreate ?
		    SessionFactoryUtils.getSession(getSessionFactory(), false) :
				SessionFactoryUtils.getSession(getSessionFactory(),
				                               this.hibernateTemplate.getEntityInterceptor(),
																			 this.hibernateTemplate.getJdbcExceptionTranslator()));
	}

	/**
	 * Convert the given HibernateException to an appropriate exception from
	 * the org.springframework.dao hierarchy. Will automatically detect
	 * wrapped SQLExceptions and convert them accordingly.
	 * <p>Delegates to the convertHibernateAccessException method of this
	 * DAO's HibernateTemplate.
	 * @param ex HibernateException that occured
	 * @return the corresponding DataAccessException instance
	 * @see #setHibernateTemplate
	 * @see venus.frames.orm.hibernate.HibernateTemplate#convertHibernateAccessException
	 */
	protected final DataAccessException convertHibernateAccessException(HibernateException ex) {
		return this.hibernateTemplate.convertHibernateAccessException(ex);
	}

	/**
	 * Close the given Hibernate Session if necessary, created via this bean's
	 * SessionFactory, if it isn't bound to the thread.
	 * @param session Session to close
	 * @see venus.frames.orm.hibernate.SessionFactoryUtils#closeSessionIfNecessary
	 */
	protected final void closeSessionIfNecessary(Session session) {
		SessionFactoryUtils.releaseSession(session, getSessionFactory());
	}

}
