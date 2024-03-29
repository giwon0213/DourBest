package com.won.dourbest.user.service;

import com.won.dourbest.common.exception.member.MemberModifyException;
import com.won.dourbest.common.exception.member.MemberRegistException;
import com.won.dourbest.common.exception.member.MemberRemoveException;
import com.won.dourbest.user.dto.AddressDTO;
import com.won.dourbest.user.dto.CheckMemberDTO;
import com.won.dourbest.user.dto.MemberDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberService extends UserDetailsService {


    /*회원 가입용 메소드*/
    int registMember(Map<String, Object> map);

    // 회원 아이디 중복 체크하기
    boolean idCheck(String memberId);

    // 이메일 체크하기
    boolean emailCheck(String memberEmail);

    public Optional<MemberDTO> findUser(String userId);

    // 회원 정보 수정용 메소드
    int updateMember(Map<String, Object> map);

    //회원 탈퇴용 메소드
    int quitMember(String memberId);

    int changePwd(MemberDTO member);


}
