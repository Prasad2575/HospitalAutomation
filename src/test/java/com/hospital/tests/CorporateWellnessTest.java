package com.hospital.tests;

import com.hospital.base.BaseTest;
import com.hospital.pages.CorporateWellnessPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CorporateWellnessTest extends BaseTest {

    @Test
    public void captureWarningMessageForInvalidCorporateWellnessForm() {

        CorporateWellnessPage cw = new CorporateWellnessPage(driver);

        // open corporate wellness
        cw.open();

        // fill invalid details
        cw.fillInvalidDetails(
                "12@@",          // invalid name
                "",              // empty org
                "123",           // invalid contact
                "abc@gmail"      // invalid email
        );

        // In many cases submit remains disabled because of validation/recaptcha
        boolean disabled = cw.isSubmitDisabled();
        System.out.println("Submit Disabled? = " + disabled);

        String emailClass = cw.getEmailFieldClass();
        System.out.println("Email Field Class = " + emailClass);

        Assert.assertTrue(emailClass.toLowerCase().contains("error") || disabled,
                "Expected validation error (error class) OR submit disabled, but not found.");
    }
}
