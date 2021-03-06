import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Hibernate_Controller {

    public Session getSession() {
        Configuration configuration =new Configuration().configure( "scenes/hibernate.cfg.xml" );
        configuration.addAnnotatedClass(CzescSamochodowa.class);
        configuration.addAnnotatedClass(Kategoria.class);
        configuration.addAnnotatedClass(Sklep.class);
        configuration.addAnnotatedClass(Pracownik.class);
        configuration.addAnnotatedClass(Zamowienie.class);
        ServiceRegistry serviceRegistry =new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();

        return session;
    }


    public static void main(String[] args){

    }
}
