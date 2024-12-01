package HyEnd.Proj.Service;

import HyEnd.Proj.DTO.ActivitiesGroupDTO;
import HyEnd.Proj.DTO.ActivityLogWithDetailsDTO;
import HyEnd.Proj.DTO.RequestActivitiesDTO;
import HyEnd.Proj.Entity.Activity;
import HyEnd.Proj.Entity.ActivityLog;
import HyEnd.Proj.Entity.ActivityLogDetail;
import HyEnd.Proj.Entity.User;
import HyEnd.Proj.Repository.ActivityLogRepository;
import HyEnd.Proj.Repository.ActivityRepository;
import HyEnd.Proj.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ActivityLogService {
    @Autowired ActivityLogRepository activityLogRepository;
    @Autowired ActivityRepository activityRepository;
    @Autowired UserRepository userRepository;

    public List<ActivityLog> findAll() {
        return activityLogRepository.findAll();
    }

    public Optional<ActivityLogWithDetailsDTO> findById(Long id) {
        return activityLogRepository.findLogById(id);
    }

    public Long save(RequestActivitiesDTO requestActivitiesDTO) throws NoSuchElementException {
        List<ActivitiesGroupDTO> groupList = requestActivitiesDTO.getGroup();
        String userId = requestActivitiesDTO.getUserId();
        Date date = requestActivitiesDTO.getDate();
        User user = userRepository.findByUserId(userId);

        long weight = user.getWeight();

        ActivityLog activityLog = new ActivityLog();
        activityLog.setActivityDate(date);
        activityLog.setUser(user);
        activityLogRepository.saveLog(activityLog);

        double totalCaloriesBurned = 0;
        for (ActivitiesGroupDTO groupDTO : groupList) {
            Activity activity = activityRepository.findById(groupDTO.getId())
                    .orElseThrow(() -> new NoSuchElementException("Activity not found with ID: " + groupDTO.getId()));
            long duration = groupDTO.getDuration();
            int metValue = activity.getMetValue();
            ActivityLogDetail activityLogDetail = new ActivityLogDetail();

            double caloriesBurned = calculateCalories(metValue,weight,duration);
            activityLogDetail.setActivity(activity);
            activityLogDetail.setDuration(duration);
            activityLogDetail.setCaloriesBurned(caloriesBurned);
            activityLogDetail.setActivityLog(activityLog);

            activityLogRepository.saveLogDetail(activityLogDetail);
            totalCaloriesBurned += caloriesBurned;
        }
        activityLog.setTotalCaloriesBurned(totalCaloriesBurned);
        activityLogRepository.updateLog(activityLog);
        return activityLog.getId();
    }

    private Double calculateCalories(int met, long weight, long duration) {
        double timePerO2used =  duration * (3.5 * weight * met);
        return Math.floor(timePerO2used * 5/1000 * 100) / 100;
    }
}
