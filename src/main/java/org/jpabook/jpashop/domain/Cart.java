package org.jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
