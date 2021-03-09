import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Hibernate_Controller {

    public Session getSession( ) {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(CarParts.class);
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(Shop.class);
        configuration.addAnnotatedClass(Workers.class);
        configuration.addAnnotatedClass(Order.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();

        return session;
    }


    public static void main(String[] args){

    }
}
