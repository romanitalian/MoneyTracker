package net.romanitalian.moneytrackerapp.activities;

import android.support.test.espresso.ViewInteraction;
import android.test.ActivityInstrumentationTestCase2;

import net.romanitalian.moneytrackerapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

public class AddTransactionActivityTest extends ActivityInstrumentationTestCase2 {

    public AddTransactionActivityTest() {
        super(AddTransactionActivity_.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testAddTransaction() throws Exception {
        submitButton().check(matches(not(isEnabled()))); // not enabled
        onView(withHint(R.string.sum_hint)).perform(typeText("200"));
        onView(withHint(R.string.about_hint)).perform(typeText("Some description of purchase"));
        submitButton().check(matches((isEnabled()))); // enabled
    }

    private ViewInteraction submitButton() {
        return onView(withText(R.string.add));
    }
}