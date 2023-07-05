package com.won.dourbest.admin.account.service;

import com.won.dourbest.admin.account.dto.*;
import com.won.dourbest.admin.common.SelectCriteria;

import java.util.List;
import java.util.Map;


public interface AdminService {



    // 모든 회원 목록 조회
    List<AccountDTO> selectAllaccountList(SelectCriteria selectCriteria);
    // 탈퇴한 회원 조회
    List<withdrawnMemberDTO> selectAllwithdrawnList();

    // 펀딩 결제 내역 조회
    List<FundingPaymentDTO> selectAllFunPaymentList();

    // 멤버십 결제 내역 조회
    List<MembershipPaymentDTO> selectAllmemPaymentList();

    // 블랙리스트 조회
    List<BlcaklistDTO> selectallBlackList();


    // 관리자 계정 조회
    List<AdminAccountDTO> selectAllAdminAccount();

    int selectTotalPage(Map<String, String> searchMap);

    // 관리자 계정 생성
    String registAdmin(AdminRegistDTO adminRegist);


    // 아이디로 검색
    AccountDTO selectSearchId(String searchId);
}
