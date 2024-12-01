package HyEnd.Proj.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // 요청 시 사용
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String gender;

    @Column
    private Long height;

    @Column
    private Long weight;

    @Column
    private Long smm;

    @Column
    private Long bfp;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<DietLog> dietLogList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ActivityLog> activityLogList = new ArrayList<>();
}
