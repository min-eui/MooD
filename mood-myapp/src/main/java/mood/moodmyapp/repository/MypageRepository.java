package mood.moodmyapp.repository;

import mood.moodmyapp.domain.Friend;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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


    @Query("SELECT f FROM Friend  f WHERE f.userId= :myId AND f.friendId = :userId")
    Optional<String> findByFriendId(@Param("userId") String userId, @Param("myId")String myId);
}
