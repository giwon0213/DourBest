package com.won.dourbest.user.mypage.controller;

import com.won.dourbest.admin.account.dto.AdminInquiriesDTO;
import com.won.dourbest.common.dto.CategoryDTO;
import com.won.dourbest.common.dto.CommonResponse;
import com.won.dourbest.common.dto.Pagination;
import com.won.dourbest.common.dto.SearchCriteria;
import com.won.dourbest.seller.dto.SellerInquiryDTO;
import com.won.dourbest.user.dto.*;
import com.won.dourbest.user.mypage.service.MypageCommonService;
import com.won.dourbest.user.mypage.service.MypageService;
import com.won.dourbest.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    private final MypageCommonService commonService;

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String mypage(Model model, @AuthenticationPrincipal MemberImpl member) {

        log.info("member={}", member);
        /* 세션에서 멤버 가져와서 id 값을 이용하자 */
        String userId = member.getUsername();
        log.info("userId={}", userId);
        Map<String, Object> mypageInfo = mypageService.myPageinfo(userId);


        model.addAttribute("mypageInfo",(MypageMainDTO) mypageInfo.get("mypageMain"));
        model.addAttribute("delivery", mypageInfo.get("delivery"));

        return "user/mypage/mypage";
    }

    @GetMapping("/coupon")
    public String couponPage(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model) {

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
    public String selleInquire(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model) {

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
    public String adminInquire(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model) {

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
    public String reportList(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model) {

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
    public String likeFundingList(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model) {

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

    @GetMapping("/purchase-funding")
    public String purchaseList(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model) {

        //세션으로부터 받자
        String userId = member.getUsername();

        Pagination pagination = new Pagination(criteria, mypageService.listTotalCount(criteria, userId, "purchase"));

        List<PurchasedFundingListDTO> list = mypageService.purchaseList(criteria, userId);
        log.info("list={}", list);
        List<CategoryDTO> category = commonService.fundingCategory();

        model.addAttribute("category", category);
        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);

        return "user/mypage/purchase";
    }

    @GetMapping("/review")
    public String reviewList(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model) {

        //세션으로부터 받자
        String userId = member.getUsername();
        MemberDTO memberDTO = memberService.findUser(userId).orElseThrow();

        Pagination pagination = new Pagination(criteria, mypageService.listTotalCount(criteria, userId, "purchase"));

        List<PurchasedFundingListDTO> list = mypageService.purchaseList(criteria, userId);
        log.info("list={}", list);

        model.addAttribute("memberCode", memberDTO.getMemberCode());
        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);

        return "user/mypage/review";
    }


    @GetMapping("/point")
    public String pointList(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model) {

        //세션으로부터 받자
        String userId = member.getUsername();

        Pagination pagination = new Pagination(criteria, mypageService.listTotalCount(criteria, userId, "point"));

        List<MemberPointDTO> list = mypageService.pointList(criteria, userId);
        log.info("list={}", list);

        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);

        return "user/mypage/point";
    }

    @PostMapping(value = "/coupon/regist", produces = "application/json")
    @ResponseBody
    public ResponseEntity<CommonResponse> couponRegist(@RequestBody MemberCouponList code) {
        log.info("code={}", code);

        mypageService.couponRegister(code.getCouponListCode());

        CommonResponse response = new CommonResponse(true, "쿠폰등록성공");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 회원 정보
    @GetMapping("/current")
    public MemberDTO currentMember(@AuthenticationPrincipal MemberImpl member, Model model) {
        MemberDTO findMember = memberService.findUser(member.getMemberId()).orElseThrow();
        log.info("member={}", findMember);
        model.addAttribute("member", member);
        return findMember;


    }

    // 회원 정보 변경
    @GetMapping("checkMember")
    public String checkmember(@AuthenticationPrincipal MemberImpl user, Model model) {

        MypageMainDTO info = mypageService.info(user.getUsername());

        model.addAttribute( "user", user.getUsername());
        model.addAttribute( "info", info);


        return "/user/mypage/checkMember";

    }

    // 비밀번호 변경 전 재로그인 페이지 이동
    @GetMapping("checkMemberPw")
    public String checkmemberPw(@AuthenticationPrincipal MemberImpl user, Model model) {
        MypageMainDTO info = mypageService.info(user.getUsername());

        model.addAttribute("user", user.getUsername());
        model.addAttribute( "info", info);


        return "/user/mypage/checkMemberPw";

    }

    @GetMapping("changeInfo")
    public String changeInfo(@AuthenticationPrincipal MemberImpl user, Model model) {

        MypageMainDTO info = mypageService.info(user.getUsername());
        MemberDTO mypageInfo = memberService.findUser(user.getMemberId()).orElseThrow();

        model.addAttribute("user", user.getUsername());
        model.addAttribute( "info", info);
        model.addAttribute( "mypageInfo", mypageInfo);

        return "user/mypage/changeInfo";

    }

    //   회원정보 수정
    @PostMapping("changeInfo")
    public String changeInfo(@AuthenticationPrincipal MemberImpl user, @ModelAttribute MemberDTO member, @ModelAttribute AddressDTO address) {

        MemberDTO findMember = memberService.findUser(user.getMemberId()).orElseThrow();  // 현재 회원 정보담은 것
        member.setMemberId(user.getMemberId());  //회원 id만 불러오기
        address.setAddressCode(findMember.getAddress().getAddressCode());  // 회원 주소코드만 불러오기 (쿼리문때문에)

        Map<String, Object> map = new HashMap<>(); // 객체담을 맵 생성
        map.put("member", member);
        map.put("address", address);
//        log.info("MemberDTO : ",member.toString());
//        log.info("AddressDTO : ",address.toString());
        memberService.updateMember(map);  //객체 담은 후 서비스로 전송

        return "redirect:/mypage";

    }

    // 회원 탈퇴 ================================================================================================

    //탈퇴전 본인 확인 메소드
    @GetMapping("beforequitMember")
    public String checkmemberDelete(@AuthenticationPrincipal MemberImpl user, Model model) {


        MypageMainDTO info = mypageService.info(user.getUsername());

        model.addAttribute("user", user);
        model.addAttribute("info", info);


        return "/user/mypage/checkMemberDelete";

    }

    // 회원탈퇴 메소드
    @GetMapping("/quitMember")    //이동할 페이지
    public String quitMember(@AuthenticationPrincipal MemberImpl user, Model model) {

        MypageMainDTO info = mypageService.info(user.getUsername());

        model.addAttribute("user", user);
        model.addAttribute("info", info);
        System.out.println("user = " + user);
        MemberDTO mypageInfo = memberService.findUser(user.getMemberId()).orElseThrow();
        model.addAttribute("mypageInfo", mypageInfo);  //멤버 배송지 모두 담겨있음.
//        model.addAttribute("mypageInfo", mypageInfo);
        System.out.println("mypageInfo.getMemberId() ======================== " + mypageInfo.getMemberId());

        return "/user/mypage/quitMember";
    }


//     회원 탈퇴  =====================================================================================================
    @GetMapping("/quitMemberS")
    public String quitMember(@AuthenticationPrincipal MemberImpl user) {

        System.out.println("user ======================== " + user);
        MemberDTO mypageInfo = memberService.findUser(user.getMemberId()).orElseThrow();
        System.out.println("mypageInfo = " + mypageInfo);
        int success = memberService.quitMember(mypageInfo.getMemberId());
        System.out.println(" success ======================== " + success );

        if(success == 1) {

        }

        SecurityContextHolder.clearContext();
        return "redirect:/user/quitMember-success" ;

    }


    @GetMapping("/purchase-funding/{id}")
    public String OrderDetail(@AuthenticationPrincipal MemberImpl user, @PathVariable int id, Model model){

        Map<String, Object> result = mypageService.OrderAndCreditInfo(user.getUsername(), id);

        model.addAttribute("order",(OrderFundingDTO) result.get("order"));
        model.addAttribute("credit",(OrderCreditDTO) result.get("credit"));
        model.addAttribute("contactCategory", result.get("contactCategory"));
        model.addAttribute("reviewCount", result.get("reviewCount"));

        return "user/order/funding-detail";
    }

    @GetMapping("/changePwd")    //이동할 페이지
    public String changePwd(@AuthenticationPrincipal MemberImpl user, Model model){

        MypageMainDTO info = mypageService.info(user.getUsername());

        model.addAttribute("user", user.getUsername());
        model.addAttribute( "info", info);

        return "user/mypage/changePwd";
    }


    @PostMapping ("/changePwd")    //이동할 페이지
    public String changePwd(@AuthenticationPrincipal MemberImpl member, @RequestParam String pwd, @RequestParam String pwdCheck){

        MemberDTO member1 = new MemberDTO();
        MemberDTO findMember = memberService.findUser(member.getMemberId()).orElseThrow();
        member1.setMemberId(findMember.getMemberId());  // 기존 회원 id 불러오기 쿼리문필요


        // 두가지가 트루인걸 확인!
        if(pwd.equals(pwdCheck)){

            member1.setMemberPwd(passwordEncoder.encode(pwd));  // 비밀번호 암호화 안되면 로그인 안됨.
            memberService.changePwd(member1);
            return "redirect:/mypage";
        }

        return "user/mypage/changePwd";
    }

    @PostMapping("/profile")
    @ResponseBody
    public void uploadProfile(MultipartFile profile, @AuthenticationPrincipal MemberImpl member){
        String root = "C:\\dev\\fundingImg\\";
        String profilePath = root + "profile";

        System.out.println("id = " + member.getUsername());
        System.out.println("profile = " + profile.getOriginalFilename());

        File mkdir = new File(profilePath);
        if(!mkdir.exists()) {
            mkdir.mkdirs();
        }

        String originFileName = profile.getOriginalFilename();
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        String savedName = UUID.randomUUID().toString().replace("-","") + ext;

        try {
            profile.transferTo(new File(profilePath + "\\" + savedName));

            mypageService.changeProfile(new ProfileDTO(member.getUsername(),savedName));

        } catch (IOException e) {
            new File(profilePath + "\\" + savedName).delete();
        }

    }


    @GetMapping("/myFunding")
    public String myFundingPage(@AuthenticationPrincipal MemberImpl member, @ModelAttribute("cri") SearchCriteria criteria, Model model){
        //세션으로부터 받자
        String userId = member.getUsername();

        Pagination pagination = new Pagination(criteria, mypageService.listTotalCount(criteria, userId, "myFunding"));

        List<LikeFundingDTO> list = mypageService.myFundingList(criteria, userId);

        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);


        return "user/mypage/myFunding";
    }





    // 문의사항 상세페이지 ================================================================================================

    @GetMapping("/admin-inquire/{id}")    //이동할 페이지
    public String inquireView(@AuthenticationPrincipal MemberImpl user ,@PathVariable("id") int id, Model model){
//        System.out.println("id  ============================ " + id);
        MemberDTO findMember = memberService.findUser(user.getMemberId()).orElseThrow();
        AdminInquiriesDTO inquir = mypageService.QnaInqurireAnwser(findMember.getMemberCode(), id);

        if( inquir.getAnswerContent() != null) {

            model.addAttribute("member",findMember);
            model.addAttribute("inquir" ,inquir);
            model.addAttribute("showAnswer", true);
//        System.out.println("membercode ============================ " + findMember.getMemberCode());
//        System.out.println("inquir ============================ " + inquir);


        } else {
            model.addAttribute("member",findMember);
            model.addAttribute("inquir" ,inquir);
            model.addAttribute("showAnswer", false);

        }

        return "user/mypage/inquireVeiw";
    }

    // 판매자 문의사항 상세페이지 ================================================================================================
    @GetMapping("/seller-inquire/{id}")    //이동할 페이지
    public String QnaSellerInquire(@AuthenticationPrincipal MemberImpl user ,@PathVariable("id") int id, Model model){
        System.out.println("id  ============================ " + id);
        MemberDTO findMember = memberService.findUser(user.getMemberId()).orElseThrow();
        SellerInquiryDTO inquir = mypageService.QnaSellerInquire(findMember.getMemberCode(), id);

        if( inquir.getAnswerContent() != null) {

            model.addAttribute("member",findMember);
            model.addAttribute("inquir" ,inquir);
            model.addAttribute("showAnswer", true);
        System.out.println("membercode ============================ " + findMember);
        System.out.println("inquir ============================ " + inquir);


        } else {
            model.addAttribute("member",findMember);
            model.addAttribute("inquir" ,inquir);
            model.addAttribute("showAnswer", false);

        }

        return "user/mypage/inquireSeller";
    }

    // 신고 상세페이지 ================================================================================================

    @GetMapping("/modifiy-inquire/{id}")    //이동할 페이지
    public String NotifyInquire(@AuthenticationPrincipal MemberImpl user ,@PathVariable("id") int id, Model model){
        System.out.println("id  ============================ " + id);
        MemberDTO findMember = memberService.findUser(user.getMemberId()).orElseThrow();
        MemberReportListDTO inquir = mypageService.NotifyInquire(findMember.getMemberCode(), id);

        if( inquir.getReportAnswer() != null) {

            model.addAttribute("member",findMember);
            model.addAttribute("inquir" ,inquir);
            model.addAttribute("showAnswer", true);
            System.out.println("membercode ============================ " + findMember);
            System.out.println("inquir ============================ " + inquir);


        } else {

            model.addAttribute("member",findMember);
            model.addAttribute("inquir" ,inquir);
            model.addAttribute("showAnswer", false);

        }

        return "user/mypage/inquireNotify";
    }


}
