package HyEnd.Proj.Repository;

import HyEnd.Proj.Entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository {
    void save(Activity activity);
    Optional<Activity> findById(Long id);
    Optional<Activity> findByName(String name);
    List<Activity> findAll();

}
