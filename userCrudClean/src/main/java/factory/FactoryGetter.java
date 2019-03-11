package factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public abstract class FactoryGetter {
    private static AbstractDaoFactory abstractDaoFactory;
    
    public static AbstractDaoFactory getAbstractDaoFactory() {
        if (abstractDaoFactory == null) {
            // Get root path
            String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
            Properties daoProps = new Properties();
            try {
                // Read config file
                daoProps.load(new FileInputStream(rootPath + "dao.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            // Read config field
            String factoryImplType = daoProps.getProperty("dao.implementation").toLowerCase();
            if (factoryImplType.contains("jdbc")) {
                abstractDaoFactory = new DaoJdbcFactory();
            } else {
                abstractDaoFactory = new DaoHibernateFactory();
            }
        }
        
        return abstractDaoFactory;
    }
}
