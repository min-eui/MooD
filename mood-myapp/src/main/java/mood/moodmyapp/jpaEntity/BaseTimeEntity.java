package mood.moodmyapp.jpaEntity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  // 클래스에 Auditing 기능 포함
   public class BaseTimeEntity {

    @CreatedDate    //Entity생성되어 저장될 때 시간이 자동저장됨.
    private LocalDateTime reg_date; // 등록한 날짜

    /*@LastModifiedDate
    private LocalDateTime modifiedDate;*/


}
