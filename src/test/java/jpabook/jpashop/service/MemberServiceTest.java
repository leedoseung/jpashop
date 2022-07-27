package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = Member.builder()
                .name("duvis")
                .address(new Address("seoul", "222", "jamsil"))
                .build();

        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(saveId));
    
    }
    
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given

        Member member1 = Member.builder()
                .name("duvis")
                .build();

        Member member2 = Member.builder()
                .name("duvis")
                .build();

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("에러가 발생한다.");
    
    }

}