package HyEnd.Proj.Controller;

import HyEnd.Proj.Entity.Activity;
import HyEnd.Proj.Service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/activities") // 공통 경로
public class ActivityController {

    ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    // localhost:8080/activities/save?name=jogging&met=4
    @PostMapping("/save")
    @ResponseBody
    public Long saveActivity(@RequestParam("name") String name, @RequestParam("met") int met) {
        Activity activity = new Activity();
        activity.setActivityName(name);
        activity.setMetValue(met);
        System.out.println(met + name + " " + activity.getId());
        return activityService.save(activity);
    }

    // localhost:8080/activities/all
    @GetMapping("/all")
    @ResponseBody
    public List<Activity> getActivitiesAll() {
        // db 활동내역 전부 받아보기
        return activityService.findAll();
    }

    // localhost:8080/activities/id?id=1
    @GetMapping("/id")
    @ResponseBody
    public Optional<Activity> getActivityById(@RequestParam("id") Long id) {
        return activityService.findById(id);
    }

    // localhost:8080/activities/name?name=push up
    @GetMapping("/name")
    @ResponseBody
    public Optional<Activity> getActivityByName(@RequestParam("name") String name) {
        System.out.println(name);
        return activityService.findByName(name.trim());
    }
}
