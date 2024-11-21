package HyEnd.Proj.Service;

import HyEnd.Proj.Entity.Activity;
import HyEnd.Proj.Repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActivityService {
    ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    public Optional<Activity> findById(Long id) {
        return activityRepository.findById(id);
    }

    public Optional<Activity> findByName(String name) {
        return activityRepository.findByName(name);
    }

    public Long save(Activity activity) {
        activityRepository.findByName(activity.getActivityName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 활동입니다.");
                });

        activityRepository.save(activity);
        return activity.getId();
    }
}
