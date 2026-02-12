package com.hospital.tests;

import com.hospital.base.BaseTest;
import com.hospital.pages.HomePage;
import com.hospital.pages.SearchResultsPage;
import com.hospital.pages.HospitalDetailsPage;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


import com.hospital.base.BaseTest;
import com.hospital.listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)

public class HospitalTest extends BaseTest {

    @Test
    public void fetch10Open24x7HospitalDetails_AndSaveToExcel() {

        HomePage homePage = new HomePage(driver);
        homePage.selectCity("Bangalore");
        homePage.searchHospital();

        SearchResultsPage resultsPage = new SearchResultsPage(driver);
        List<String> urls = resultsPage.getFirstNOpen24x7HospitalUrls(2);

        Assert.assertEquals(urls.size(), 2,
                "Could not fetch 10 Open 24x7 hospital URLs. Found: " + urls.size());

        // Prepare rows for Excel
        // Header row
        java.util.List<String[]> rows = new java.util.ArrayList<>();
        rows.add(new String[]{"S.No", "Hospital Name", "Beds", "Rating", "Phone", "URL"});

        System.out.println("\n========= Top 10 Open 24x7 Hospital Details =========");

        for (int i = 0; i < urls.size(); i++) {

            String url = urls.get(i);
            driver.get(url);

            HospitalDetailsPage detailsPage = new HospitalDetailsPage(driver);

            String name = detailsPage.getHospitalName();
            int beds = detailsPage.getBedsCount();
            double rating = detailsPage.getRating();
            String phone = detailsPage.getPhoneNumber();

            System.out.println("\n" + (i + 1) + ") " + name);
            System.out.println("   Beds   : " + beds);
            System.out.println("   Rating : " + rating);
            System.out.println("   Phone  : " + phone);
            System.out.println("   URL    : " + url);

            // Add row to excel list
            rows.add(new String[]{
                    String.valueOf(i + 1),
                    name,
                    String.valueOf(beds),
                    String.valueOf(rating),
                    phone,
                    url
            });
        }

        // Save to Excel (in project folder)
        String excelPath = "target/HospitalData_Open24x7.xlsx";
        com.hospital.utils.ExcelUtil.writeHospitalData(excelPath, "Open24x7", rows);
    }

}
