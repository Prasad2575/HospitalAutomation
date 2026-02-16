package com.hospital.tests;

import com.hospital.base.BaseTest;
import com.hospital.listeners.TestListener;
import com.hospital.model.HospitalInfo;
import com.hospital.pages.HomePage;
import com.hospital.pages.HospitalDetailsPage;
import com.hospital.pages.SearchResultsPage;
import com.hospital.utils.ExcelUtil;
import com.hospital.utils.ScreenshotUtil;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Listeners(TestListener.class)
public class HospitalTest extends BaseTest {

    @Test
    public void fetchOpen24x7HospitalDetails_AndSaveToExcel() {

        int limit = 2; // change to 10 anytime

        HomePage homePage = new HomePage(driver);
        homePage.selectCity("Bangalore");
        homePage.searchHospital();

        SearchResultsPage resultsPage = new SearchResultsPage(driver);
        List<String> urls = resultsPage.getFirstNOpen24x7HospitalUrls(limit);

        Assert.assertEquals(urls.size(), limit,
                "Could not fetch " + limit + " Open 24x7 hospital URLs. Found: " + urls.size());

        List<HospitalInfo> hospitals = new ArrayList<>();

        for (int i = 0; i < urls.size(); i++) {

            String url = urls.get(i);
            driver.get(url);

            HospitalDetailsPage detailsPage = new HospitalDetailsPage(driver);

            // ✅ Wait hospital page title
            String hospitalName = detailsPage.getHospitalName();

            // ✅ Make sure phone is visible and loaded
            detailsPage.ensurePhoneNumberVisible();

            // ✅ Scroll phone into view so screenshot includes it
            detailsPage.scrollToPhoneNumber();

            // ✅ Screenshot after phone is visible
            String shotName = String.format("%02d_%s", (i + 1), hospitalName);
            String shotPath = ScreenshotUtil.takeScreenshotToFolder(
                    driver,
                    "target/screenshots/hospitals",
                    shotName
            );

            // ✅ Fetch details
            HospitalInfo info = detailsPage.fetchHospitalInfo(url, i + 1);

            hospitals.add(info);

            System.out.println("\n" + info);
            System.out.println("   Screenshot: " + shotPath);
        }

        List<String[]> rows = ExcelUtil.buildHospitalRows(hospitals);

        String excelPath = "target/HospitalData_Open24x7.xlsx";
        ExcelUtil.writeHospitalData(excelPath, "Open24x7", rows);
    }
}