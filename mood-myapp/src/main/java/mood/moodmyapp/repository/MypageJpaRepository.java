package mood.moodmyapp.repository;

import mood.moodmyapp.domain.Friend;
import mood.moodmyapp.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MypageJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<Friend, Long>{


    //List<Friend> findMyFriendByUserId(String userId); //내 친구 목록 불러오기
    Friend save(Friend friend);
    List<Friend> findAllByUserId(String userId);

    //Optional<String> findByFriendId(String userId, String myId);
}
