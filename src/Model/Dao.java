package Model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Dao {
    // 创建 SessionFactory
    private final SessionFactory sessionFactory;
    public Dao() {
        Configuration configuration = new Configuration().configure("resources/hibernate.cfg.xml");
        this.sessionFactory = configuration.buildSessionFactory();
    }
    //增加联系人
    public void addPeople(Contracts contracts) {
        try (Session session = sessionFactory.openSession()) {
            // 开启事务
            Transaction transaction = session.beginTransaction();
            // 执行数据库操作
            session.save(contracts);
            // 提交事务
            transaction.commit();
        }
    }
    //模糊查询联系人
    public List<Contracts> searchPeoplesByTitle(String keyword) {
        try (Session session = sessionFactory.openSession()) {
            // 开启事务
            Transaction transaction = session.beginTransaction();
            // 使用 CriteriaBuilder 构建查询
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Contracts> criteriaQuery = builder.createQuery(Contracts.class);
            Root<Contracts> root = criteriaQuery.from(Contracts.class);
            // 添加模糊查询条件
            criteriaQuery.where(builder.or(builder.like(root.get("name"), "%" + keyword + "%"),
                    builder.like(root.get("phone"), "%" + keyword + "%"),
                    builder.like(root.get("address"), "%" + keyword + "%")));
            // 执行查询
            List<Contracts> contractsList = session.createQuery(criteriaQuery).list();
            // 提交事务
            transaction.commit();
            return contractsList;
        }
    }
    //更新联系人
    public void updatePeople(Contracts updatedContracts) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(updatedContracts);
            transaction.commit();
        }
    }
    //删除联系人
    public void deletePeople(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Contracts contracts = session.get(Contracts.class, id);
            if(contracts!=null)
                session.delete(contracts);
            transaction.commit();
        }
    }
    //查询所有人
    public List<Contracts> getAllPeoples() {
        try (Session session = sessionFactory.openSession()) {
            // 开启事务
            Transaction transaction = session.beginTransaction();
            // 使用 CriteriaBuilder 构建查询
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Contracts> Query = builder.createQuery(Contracts.class);
            Root<Contracts> root = Query.from(Contracts.class);
            Query.select(root);
            // 执行查询
            List<Contracts> contractsList = session.createQuery(Query).list();
            // 提交事务
            transaction.commit();
            return contractsList;
        }
    }
}
