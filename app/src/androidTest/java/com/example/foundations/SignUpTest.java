package com.example.foundations;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    @Rule
    public ActivityTestRule<SignUp> signUpActivityTestRule
            = new ActivityTestRule<SignUp>(SignUp.class);
    private SignUp signUpActivity = null;



    @Before
    public void setUp() throws Exception {
        signUpActivity = signUpActivityTestRule.getActivity();
    }

    @Test
    public void hasTitle() {
        onView(withId(R.id.signup)).check(matches(withText(R.string.welcome)));
    }

    @Test
    public void hasUsername() {
        onView(withId(R.id.username)).check(matches(withText(R.string.username)));
    }

    @Test
    public void hasLicenseNumber() {
        onView(withId(R.id.licenseNumber)).check(matches(withText(R.string.license_number)));
    }

    @Test
    public void hasCompanyName() {
        onView(withId(R.id.companyName)).check(matches(withText(R.string.company_name)));
    }

    @Test
    public void hasAddress() {
        onView(withId(R.id.address)).check(matches(withText(R.string.address)));
    }

    @Test
    public void hasPhone() {
        onView(withId(R.id.phone)).check(matches(withText(R.string.phone)));
    }

    @After
    public void tearDown() throws Exception {
        signUpActivity = null;
    }
}