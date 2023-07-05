package com.won.dourbest.user.mypage.controller;

import com.won.dourbest.common.dto.CategoryDTO;
import com.won.dourbest.common.dto.CommonResponse;
import com.won.dourbest.common.dto.Pagination;
import com.won.dourbest.common.dto.SearchCriteria;
import com.won.dourbest.user.dto.*;
import com.won.dourbest.user.mypage.service.MypageCommonService;
import com.won.dourbest.user.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    private final MypageCommonService commonService;

    @GetMapping
    public String mypage(Model model, @AuthenticationPrincipal MemberImpl member){

        log.info("member={}", member);
        /* 세션에서 멤버 가져와서 id 값을 이용하자 */
        String userId = member.getUsername();
        log.info("userId={}",userId);

        MypageDTO mypageInfo = mypageService.myPageinfo(userId);

        model.addAttribute("mypageInfo", mypageInfo);
        log.info("mypageInfo={}", mypageInfo);


        return "user/mypage/mypage";
    }

    @GetMapping("/coupon")
    public String couponPage(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model){

        /* 세션에서 멤버 가져와서 id 값을 이용하자 */
        String userId = member.getUsername();

        Pagination pagination = new Pagination(criteria, mypageService.listTotalCount(criteria, userId, "sellerInquire"));

        List<MemberCouponList> list = mypageService.allCoupon(criteria, userId);

        log.info("list={}", list);

        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);

        return "user/mypage/coupon";
    }

    @GetMapping("/seller-inquire")
    public String selleInquire(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model){

        //세션으로부터 받자
        String userId = member.getUsername();

        Pagination pagination = new Pagination(criteria, mypageService.listTotalCount(criteria, userId, "sellerInquire"));

        List<MemberSellerInquireDTO> list = mypageService.sellerInquire(criteria, userId);
        log.info("list={}", list);

        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);

        return "user/mypage/sellerInquire";
    }

    @GetMapping("/inquire")
    public String adminInquire(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model){

        //세션으로부터 받자
        String userId = member.getUsername();

        Pagination pagination = new Pagination(criteria, mypageService.listTotalCount(criteria, userId, "adminInquire"));

        List<MemberInquireListDTO> list = mypageService.adminInquire(criteria, userId);
        log.info("list={}", list);

        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);

        return "user/mypage/adminInquire";
    }

    @GetMapping("/report")
    public String reportList(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model){

        //세션으로부터 받자
        String userId = member.getUsername();

        Pagination pagination = new Pagination(criteria, mypageService.listTotalCount(criteria, userId, "report"));

        List<MemberReportListDTO> list = mypageService.reportList(criteria, userId);
        log.info("list={}", list);

        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);

        return "user/mypage/report";
    }


    @GetMapping("/like-funding")
    public String likeFundingList(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model){

        //세션으로부터 받자
        String userId = member.getUsername();

        Pagination pagination = new Pagination(criteria, mypageService.listTotalCount(criteria, userId, "like"));

        List<LikeFundingDTO> list = mypageService.likeFundingList(criteria, userId);
        log.info("list={}", list);
        List<CategoryDTO> category = commonService.fundingCategory();

        model.addAttribute("category", category);
        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);

        return "user/mypage/like";
    }

    @PostMapping(value = "/coupon/regist", produces = "application/json")
    @ResponseBody
    public ResponseEntity<CommonResponse> couponRegist(@RequestBody MemberCouponList code){
        log.info("code={}", code);

        mypageService.couponRegister(code.getCouponListCode());

        CommonResponse response = new CommonResponse(true,"쿠폰등록성공");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}