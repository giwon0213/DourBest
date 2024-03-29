package com.won.dourbest.admin.funding.service;

import com.won.dourbest.admin.common.SelectCriteria;
import com.won.dourbest.admin.funding.dto.AdminFundingDTO;
import com.won.dourbest.admin.funding.dto.AdminSellerRegistDTO;
import com.won.dourbest.admin.funding.dto.ApprovedDTO;

import java.util.List;
import java.util.Map;

public interface FundingListService {

    // 오픈 예정 펀딩 목록
    List<AdminFundingDTO> selectAllWillopen(SelectCriteria selectCriteria);
    // 진행중인 펀딩 목록
    List<AdminFundingDTO> selectAllOngoing(SelectCriteria selectCriteria);
    // 완료된 펀딩 목록
    List<AdminFundingDTO> selectAllFinished(SelectCriteria selectCriteria);

    // 승인된 펀딩 목록
    List<AdminFundingDTO> selectAllapproved(SelectCriteria selectCriteria);

    // 신청한 펀딩 목록
    List<AdminFundingDTO> selectAllApplied(SelectCriteria selectCriteria);

    // 판매자 신청 목록
    List<AdminSellerRegistDTO> selectAllSellerRegiList(SelectCriteria selectCriteria);

    int selectTotalPage(Map<String, String> searchMap);

    // 판매자 권한 등록
    String insertSellerRegist(String memberId);
    // 승인
    String updateDeleteWillopen(String choiceValue);
    // 반려
    String Approval(String choiceValue);
    // 삭제
    String delete(String choiceValue);


    String insertFunding(ApprovedDTO approved);

    // 펀딩 삭제
    String dropFunding(ApprovedDTO appored);

    String sellerDrop(String memberId);

    int selectOngoingTotalPage(Map<String, String> searchMap);
}
