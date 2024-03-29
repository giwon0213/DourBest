package com.won.dourbest.main.model.dao;

import com.won.dourbest.common.dto.Pagination;
import com.won.dourbest.common.dto.SearchCriteria;
import com.won.dourbest.main.model.dto.CategoryFundingDTO;
import com.won.dourbest.main.model.service.MainService;
import com.won.dourbest.user.dto.LikeFundingDTO;
import com.won.dourbest.user.dto.MemberCouponList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainMapperTest {

  @Autowired MainMapper mainMapper;
  @Autowired
  MainService mainService;

  @Test
  void fundingCategory() {
    SearchCriteria criteria = new SearchCriteria();
    criteria.setStatus("view");
    System.out.println("criteria = " + criteria);
    System.out.println("criteria.getPage() = " + criteria.getPage());
    System.out.println("criteria.getPageSize() = " + criteria.getPageSize());
    Pagination pagination = new Pagination(criteria,mainMapper.listCount(criteria,"open"));
    System.out.println("criteria.getRowStart() = " + criteria.getRowStart());
    System.out.println("criteria.getRowEnd() = " + criteria.getRowEnd());
    System.out.println("criteria.gegetSearchTypetRowEnd() = " + criteria.getSearchType());

    List<CategoryFundingDTO> list = mainMapper.openFundingCate(criteria);
    System.out.println("list = " + list);
    System.out.println("mainMapper.listCount(criteria) = " + mainMapper.listCount(criteria,"open"));

  }

  @Test
  void listCount() {
    SearchCriteria criteria = new SearchCriteria();
    criteria.setStatus("view");
    System.out.println("criteria = " + criteria);
    System.out.println("criteria.getPage() = " + criteria.getPage());
    System.out.println("criteria.getPageSize() = " + criteria.getPageSize());
    Pagination pagination = new Pagination(criteria,mainMapper.listCount(criteria,"open"));

    System.out.println("pagination = " + pagination);

    System.out.println("criteria.getRowStart() = " + criteria.getRowStart());
    System.out.println("criteria.getRowEnd() = " + criteria.getRowEnd());
    System.out.println("criteria.gegetSearchTypetRowEnd() = " + criteria.getSearchType());

    int i = mainService.totalCount(criteria,"open");
    List<CategoryFundingDTO> open = mainService.openFundingList(criteria);
    System.out.println("open = " + open);
    System.out.println("i = " + i);
  }

  @Test
  void test2(){
    List<LikeFundingDTO> funding = mainMapper.openSlide();
    System.out.println("funding = " + funding);
  }

  @Test
  void test3(){
//    List<LikeFundingDTO> likeFundingDTOS = mainMapper.ToplikeFundings();
//    System.out.println("likeFundingDTOS = " + likeFundingDTOS);


    List<LikeFundingDTO> likeFundingDTOS = mainService.ToplikeFundings();
    System.out.println("likeFundingDTOS = " + likeFundingDTOS);

  }




}