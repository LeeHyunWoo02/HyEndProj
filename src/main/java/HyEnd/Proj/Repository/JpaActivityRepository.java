package HyEnd.Proj.Repository;

import HyEnd.Proj.Entity.Activity;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaActivityRepository implements ActivityRepository {

    private final EntityManager em;

    public JpaActivityRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Activity activity) {
        em.persist(activity);
    }

    @Override
    public List<Activity> findAll() {
        return em.createQuery("select a from Activity a", Activity.class).getResultList();
    }

    @Override
    public Optional<Activity> findById(Long id) {
        Activity activity = em.find(Activity.class, id);
        return Optional.ofNullable(activity);
    }

    @Override
    public Optional<Activity> findByName(String activityName) {
        List<Activity> name = em.createQuery("select a from Activity a where a.activityName like :name", Activity.class)
                .setParameter("name", "%" + activityName + "%").getResultList();
        return name.stream().findAny();
    }
}
