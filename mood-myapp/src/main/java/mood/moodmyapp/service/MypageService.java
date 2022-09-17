package mood.moodmyapp.service;

import mood.moodmyapp.domain.Friend;
import mood.moodmyapp.domain.Member;
import mood.moodmyapp.repository.MemberRepository;
import mood.moodmyapp.repository.MypageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List getMyFriendList(String userId) {

        List friendList = memberRepository.findFriendByUserId(userId);

        return friendList;
    }

    /**
     * 친구 찾기
     * @param userId
     * @return
     */

    public List<Member> searchFriend(String userId){

        System.out.println("mypageService userId : " + userId);
        List<Member> foundFriend = memberRepository.findFriendByUserId(userId);
        return foundFriend;
    }


    /**
     * 친구 추가하기
     * @param friend
     * @return
     */

    public Friend makeFriend(Friend friend){

        Friend addFriend = mypageRepository.save(friend);
        return addFriend;
    }



}
