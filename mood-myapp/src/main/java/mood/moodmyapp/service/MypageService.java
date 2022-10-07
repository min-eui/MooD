package mood.moodmyapp.service;

import mood.moodmyapp.domain.Friend;
import mood.moodmyapp.domain.Member;
import mood.moodmyapp.repository.MemberRepository;
import mood.moodmyapp.repository.MypageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class MypageService {

    @Autowired
    MypageRepository mypageRepository;

    @Autowired
    MemberRepository memberRepository;

    public MypageService(MypageRepository mypageRepository, MemberRepository memberRepository) {
        this.mypageRepository = mypageRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 내친구 리스트 조회
     * @param userId
     * @return
     */

    public List<Friend> findAllByUserId(String userId) {

        List<Friend> friendList = mypageRepository.findAllByUserId(userId);

        return friendList;
    }

    /**
     * 친구 찾기
     * @param userId
     * @return
     */

    public String searchFriend(String userId){

        System.out.println("mypageService userId : " + userId);
        Member friend = memberRepository.findFriendByUserId(userId);
        String foundFriend = friend.getUserId();
        return foundFriend;
    }


    /**
     * 친구 추가하기
     * @param userId,friendId
     * @return
     */

    public void makeFriend(String userId, String friendId){

        Friend friend = new Friend();
        friend = Friend.builder()
                        .userId(userId)
                                .friendId(friendId)
                                        .build();

        mypageRepository.save(friend);

    }


    public Optional existFriend(String userId,String friendId) {
        Optional isFriend = mypageRepository.findByFriendIdIs(userId,friendId);
        System.out.println("service friendId : "+friendId );
        System.out.println("service userId : "+userId );
        return isFriend;
    }

    public int deleteFriend(String userId, String delId) {

        int delRes = mypageRepository.deleteByUserIdAndFriendId(userId, delId);

        return  delRes;
    }
}
