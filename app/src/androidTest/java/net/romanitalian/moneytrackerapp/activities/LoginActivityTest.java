package net.romanitalian.moneytrackerapp.activities;

import android.support.test.espresso.ViewInteraction;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import net.romanitalian.moneytrackerapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;


public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity_> {

    public LoginActivityTest() {
        super(LoginActivity_.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    @SmallTest
    public void testLoginButtonState() {
        okButton().check(matches(not(isEnabled())));
        onView(withHint(R.string.enter_login)).perform(typeText("aaa"));
        onView(withHint(R.string.enter_password)).perform(typeText("123"));
        okButton().check(matches(isEnabled()));
    }

    private ViewInteraction okButton() {
        return onView(withText(R.string.action_sign_in));
    }
}