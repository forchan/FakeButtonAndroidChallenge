package com.challenge.chris.fakebutton.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.challenge.chris.fakebutton.model.FakeButtonUser;
import com.challenge.chris.fakebutton.rest.FakeButtonApiService;
import com.challenge.chris.fakebutton.rest.FakeButtonApiUtil;
import com.challenge.chris.fakebutton.R;
import com.challenge.chris.fakebutton.model.FakeButtonUserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Chris on 3/2/18.
 *
 * FakeButtonActivity is the main activity for this simplified application created for Button's
 * Android Intern Coding Challenge.
 *
 * https://www.usebutton.com/developers/android-intern-coding-challenge/
 */

public class FakeButtonActivity extends AppCompatActivity {

    private static final String TAG = FakeButtonActivity.class.getSimpleName();
    private static final String HOME_PAGE_MESSAGE = "Simplified FakeButton application: you can " +
            "create a user or look up a user by candidate parameter!";
    private static final String CREATE_USER_PAGE_MESSAGE = "Please fill in this form to create a " +
            "new user.";
    private static final String CREATE_USER_SUCCESS = "User created successfully!";
    private static final String CREATE_USER_FAIL = "Sorry, user could not be created. " +
            "Some fields are missing/invalid or email for this candidate already exists.";
    private static final String LIST_USER_PAGE_MESSAGE = "Please enter a candidate parameter to " +
            "look up their users.";
    private static final String LIST_USER_BY_CANDIDATE_SUCCESS = "Here are the users for that " +
            "candidate!";
    private static final String LIST_USER_BY_CANDIDATE_FAIL = "Sorry, could not list users for " +
            "that candidate. Please try another candidate parameter.";

    private Button mCreateUserButton;
    private Button mListUserButton;
    private EditText mCandidateEditText;
    private EditText mEmailEditText;
    private EditText mNameEditText;
    private TextView mDisplayInfoMessage;
    private TextView mResponseMessage;

    /**
     * BottomNavigationView has 3 valid items. Home, Create User, and List Users. Selecting each
     * one will set the corresponding page attributes to visible and the other page attributes to
     * invisible. Attributes include textViews, editTexts, and buttons.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Switch statement handles 3 cases.
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    clearAllEditTexts();
                    mDisplayInfoMessage.setText(HOME_PAGE_MESSAGE);
                    mResponseMessage.setText("");
                    setCreateUserFieldsView(View.INVISIBLE);
                    setListUserFieldsView(View.INVISIBLE);
                    return true;
                case R.id.navigation_create_user:
                    clearAllEditTexts();
                    mDisplayInfoMessage.setText(CREATE_USER_PAGE_MESSAGE);
                    mResponseMessage.setText("");
                    setListUserFieldsView(View.INVISIBLE);
                    setCreateUserFieldsView(View.VISIBLE);

                    // Button for triggering createNewUser()
                    mCreateUserButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String nameInput = mNameEditText.getText().toString();
                            String emailInput = mEmailEditText.getText().toString();
                            String candidateInput = mCandidateEditText.getText().toString();
                            createNewUser(nameInput, emailInput, candidateInput);
                        }
                    });
                    return true;
                case R.id.navigation_list_users:
                    clearAllEditTexts();
                    mDisplayInfoMessage.setText(LIST_USER_PAGE_MESSAGE);
                    mResponseMessage.setText("");
                    setCreateUserFieldsView(View.INVISIBLE);
                    setListUserFieldsView(View.VISIBLE);

                    // Button for triggering listUsersByCandidate()
                    mListUserButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String candidateInput = mCandidateEditText.getText().toString();
                            listUsersByCandidate(candidateInput);
                        }
                    });
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_button);

        mDisplayInfoMessage = findViewById(R.id.displayInfoMessage);
        mResponseMessage = findViewById(R.id.displayResponseMessage);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mCreateUserButton = findViewById(R.id.createUserButton);
        mListUserButton = findViewById(R.id.listUserButton);
        mCandidateEditText = findViewById(R.id.candidateEditText);
        mEmailEditText = findViewById(R.id.emailEditText);
        mNameEditText = findViewById(R.id.nameEditText);
    }

    /**
     * createNewUser() creates a POST REST call using the FakeButtonApiService. If it receives a
     * successful create user response, it will display CREATE_USER_SUCCESS and the response message
     * to screen via mResponseMessage and mDisplayInfoMessage. Otherwise, it will display the
     * CREATE_USER_FAIL message.
     *
     * @param name
     * @param email
     * @param candidate
     */
    private void createNewUser(String name, String email, String candidate) {
        FakeButtonApiService fakeButtonApiService = FakeButtonApiUtil.getAPIService();
        FakeButtonUser testUser = new FakeButtonUser(name, email, candidate);


        Call<FakeButtonUserResponse> call = fakeButtonApiService.createUser(testUser);
        call.enqueue(new Callback<FakeButtonUserResponse>() {
            @Override
            public void onResponse(Call<FakeButtonUserResponse> call,
                                   Response<FakeButtonUserResponse> response) {

                // If response is valid and not null, we clear user input and view response.
                // Otherwise, we leave user input and output CREATE_USER_FAIL message.
                if (response.body() != null) {
                    String responseMessage = response.body().toString();
                    mDisplayInfoMessage.setText(CREATE_USER_SUCCESS);
                    mResponseMessage.setText(responseMessage);
                    clearAllEditTexts();
                } else {
                    mResponseMessage.setText(CREATE_USER_FAIL);
                }

            }

            @Override
            public void onFailure(Call<FakeButtonUserResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }

    /**
     * listUsersByCandidate() creates a GET REST call using the FakeButtonApiService. The response
     * will be a list of users for the candidate. If the candidate exists it will display that
     * list of users. If the candidate doesn't exist, it will display an empty list. Otherwise,
     * it will display the LIST_USER_BY_CANDIDATE_FAIL message.
     *
     * @param candidate
     */
    private void listUsersByCandidate(String candidate) {
        FakeButtonApiService fakeButtonApiService = FakeButtonApiUtil.getAPIService();

        Call<List<FakeButtonUserResponse>> call =
                fakeButtonApiService.getUsersByCandidate(candidate);
        call.enqueue(new Callback<List<FakeButtonUserResponse>>() {
            @Override
            public void onResponse(Call<List<FakeButtonUserResponse>> call,
                                   Response<List<FakeButtonUserResponse>> response) {

                // If not null, we clear user input and view response. Otherwise, leave user input.
                if (response.body() != null) {
                    Log.i(TAG, "we make it to list users call!");
                    String responseMessage = response.body().toString();
                    mDisplayInfoMessage.setText(LIST_USER_BY_CANDIDATE_SUCCESS);
                    mResponseMessage.setText(responseMessage);
                    clearAllEditTexts();
                } else {
                    mResponseMessage.setText(LIST_USER_BY_CANDIDATE_FAIL);
                }

            }

            @Override
            public void onFailure(Call<List<FakeButtonUserResponse>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    // Sets all fields in the Create User page view to int view
    private void setCreateUserFieldsView(int view) {
        mCreateUserButton.setVisibility(view);
        mCandidateEditText.setVisibility(view);
        mEmailEditText.setVisibility(view);
        mNameEditText.setVisibility(view);
    }

    // Sets all fields in the List User page view to int view
    private void setListUserFieldsView(int view) {
        mListUserButton.setVisibility(view);
        mCandidateEditText.setVisibility(view);
    }

    // Clears all edit texts from all pages.
    private void clearAllEditTexts() {
        mCandidateEditText.setText("");
        mEmailEditText.setText("");
        mNameEditText.setText("");
    }
}
