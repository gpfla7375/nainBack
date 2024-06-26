package io.paioneer.nain.resume.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.paioneer.nain.resume.jpa.entity.ActivityEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto {
    private Long activityNo;
    private Long resumeNo; // *entity 연결 유의
    private String activityName;
    private String activityDescription;
    private String organizer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
    private Date endDate;

    public ActivityEntity toEntity(){
        return ActivityEntity.builder()
                .activityNo(this.activityNo)
                .resumeNo(this.resumeNo)
                .activityName(this.activityName)
                .activityDescription(this.activityDescription)
                .organizer(this.organizer)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
