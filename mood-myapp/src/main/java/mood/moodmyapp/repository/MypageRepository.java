package mood.moodmyapp.repository;

import mood.moodmyapp.domain.Friend;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MypageRepository extends MypageJpaRepository {


    @Override
    Friend save(Friend friend);

    @Query("SELECT f FROM Friend  f WHERE f.userId = :userId")
    List<Friend> findFriendByUserId(@Param("userId") String userId);  //내친구 리스트 불러오기

    @Query("SELECT f FROM Friend  f WHERE f.userId = :userId")
    List<Friend> findAllByUserId(String userId);



    @Query("SELECT f FROM Friend  f WHERE f.userId= :userId AND f.friendId = :friendId")
    Optional<String> findByFriendIdIs(@Param("userId") String userId, @Param("friendId")String friendId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Friend  f WHERE f.userId= :userId AND f.friendId = :friendId")
    int deleteByUserIdAndFriendId(@Param("userId")String userId, @Param("friendId")String friendId);
}
