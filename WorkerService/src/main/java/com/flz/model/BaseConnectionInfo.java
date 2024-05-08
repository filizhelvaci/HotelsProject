package com.flz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class BaseConnectionInfo {

    @Column(name="E_MAIL")
    private String Email;

    @Column(name="PHONE_NUMBER")
    private String PhoneNumber;

}
