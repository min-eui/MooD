package mood.moodmyapp.jpaEntity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  // 클래스에 Auditing 기능 포함
public class BaseTimeModifyEntity {

    /* 실수로라도 변경되지 않도록 false 처리 */
    @Column(updatable = false)
    @CreatedDate    //Entity생성되어 저장될 때 시간이 자동저장됨.
    private LocalDateTime reg_date; // 생성일

    @LastModifiedDate
    private LocalDateTime update_date;  // 최근 수정일

}