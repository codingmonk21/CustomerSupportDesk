# CustomerSupportDesk
CustomerSupportDesk is a unique solution for adding Customer-Support module in any android app. The growth of any business purely depends on customer satisfaction, but the sustainance of growth in long term simply depends on the quality of support offered to help customers overcome the issues they face. Customer-Support, which is often neglected and taken for granted can sometime take a huge toll and make the users step out of the product in no time. The motive behind building this library is to address this common issue faced by all app developers so that they don't lose their users and keep increasing the userbase by offering a user-friendly and quality Customer-Support service. 

The good thing about this library is that it is fully built on firebase! 
Yes, you don't need any backend infrastructure to use this library. Right from filling the user details to raising a complaint/suggestion/feedback and resolving those tickets by the admin, everything happens on firebase.

The library also provides the solution for monitoring and resolving the tickets raised by users by providing a built in admin section! 
So, you need not even think of building an admin panel to monitor and maintain the tickets raised by the users. It can be seen on the go and in real time.

And the best part is, all the above said features comes in just ten lines of code!

# Usage
The library has two sections,
1) Customer-Support section
2) Admin section

Using the admin section is optional and is upto the choice of the developer. But its recommended to use, as it comes handy on the go.

# Customer-Support Section

<h4> Step 1: </h4>
  Create a project in firebase console.
<h4> Step 2: </h4>
  Add the below code in the project level gradle under the dependencies{} block,
  
          classpath 'com.google.gms:google-services:3.1.0'
  
  Add the below code in module level gradle outside the dependencies{} block,
  
          apply plugin: 'com.google.gms.google-services'
  
  Add the below code in module level gradle inside defaultConfig{} block,
  
          multiDexEnabled true
          
  Add the below code in the project level gradle inside repositories block{},
          
          maven { url 'https://jitpack.io' }
          
<h4> Step 3: </h4>
  Download the google.json file from the project created in firebase console and paste it in 'app' folder of your project
  
<h4> Step 4: </h4>
  Add the below code in the module level gradle to include CustomerSupportDesk library,
  
          compile 'com.github.dev-prajwal21:CustomerSupportDesk:1.0'
  
<h4> Step 5: </h4>
  Place a button anywhere in the app according to your design to navigate to customer-support screen.
  <h4> Note: </h4> 
  Its not necessary that you need to use only button. It can be any view.

<h4> Step 6: </h4> 
  Get a reference to sharedPreferenceHelper object in the activity/fragment where the button (or any view) used to               navigate to customer-support screen.
  
  Implement the UserRegistrationListener in the activity/fragment where the button(or any view) used to navigate to             customer-support screen as shown below,
      
      public class MainActivity extends AppCompatActivity implements UserRegistrationListener {

        private SharedPreferenceHelper sharedPreferenceHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          sharedPreferenceHelper = new SharedPreferenceHelper(this, Config.SP_ROOT_NAME);
        
        }

        @Override
        public void registrationStatus(boolean isUserRegistered) {
        
        }
        
      }

<h4> Step 7: </h4>
  > Copy paste the below code in the interface method registrationStatus(),
        
        if (isUserRegistered) {
            if (sharedPreferenceHelper.getBoolean(Config.SP_ADMIN_LOGIN, false)) {
                sharedPreferenceHelper.putBoolean(Config.SP_ADMIN_LOGIN, false);
            }
            startActivity(new Intent(this, CustomerDashboard.class));
        } else {
            startActivity(new Intent(this, ProfileFillUpActivity.class));
        }
        
<h4> Step 8: </h4> 
  Get a reference for the button (or any view) used to navigate to customer-support screen and attach a click listener to it.

      contactSupportBtn = (Button) findViewById(R.id.contactSupportBtn);
      contactSupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            
            }
        });
        
  Copy paste the below code inside the listener,
      
      UserRegistration userRegistration = new UserRegistration(MainActivity.this);
      userRegistration.setUserRegistrationListener(MainActivity.this);
      userRegistration.isUserRegistered("12345");
      
  Thats it!! Now your app has a customer-support module integrated to it.<br/> 
  Click on the button and fill the user details to proceed to the dashboard and add complaints, suggestions, feedback.
  
  Each time a complaint/suggestion/feedback is added, you can view the data in the firebase console. 
  
# Admin Section

  To add admin facility to monitor and resolve the tickets raised, you can use the same login system your app uses and           navigate to admin section. 
  
  Authentication of admin can be done via webservice or you can even simply hardcode the admin credentials within the app so     that if the admin credentials are given it is navigated to admin dashboard.
  
  As an example here I'm using a button to simply go to admin section. 
  Modify this according to your need in the login screen so that a web-service or hardcoded values of admin credentials can be   used to navigate to admin dashboard.
  
      adminBtn = (Button) findViewById(R.id.adminBtn);
      adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferenceHelper.putBoolean(Config.SP_ADMIN_LOGIN, true);
                startActivity(new Intent(MainActivity.this, AdminDashboard.class));
            }
        });
        
  <h4> Note: </h4>
  While logging in as admin it is mandatory to use the below line of code and then start the AdminDashboard activity, 
            
      sharedPreferenceHelper.putBoolean(Config.SP_ADMIN_LOGIN, true);
