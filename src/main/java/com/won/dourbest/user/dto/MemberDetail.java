package com.won.dourbest.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.won.dourbest.common.dto.MemberShipDTO;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDetail implements UserDetails {


    private int memberCode;  // 멤버코드
    private String memberId;  // 아이디
    private String memberPwd;  // 비번
    private String memberName; // 이름
    private String memberPhone; // 전화번호
    private String memberEmail; // 이메일
    private Date registDate;  //가입일
    private char withdrawalStatus;  //탈퇴여부
    private char adultStatus; // 성인여부
    private MemberShipDTO membership;  //멤버쉽

//    /* 주소 */
//    private AddressDTO address;
//
//    /*멤버십결제*/
//    private MemberShipCreditDTO membershipCredit ;
//
//    /* 쿠폰목록리스트 */
//    List<CouponListDTO> couponList;
//
//    /* 권한리스트 */
//    private List<MemberAuthListDTO> memberAuthList;
//
//    /* 좋아요 펀딩리스트 */
//    private List<LikeFundingDTO> likeFundingList;
//
//    /* 구매한 펀딩리스트 */
//    private List<PurchasedFundingListDTO> purchasedFundingList;




    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


}