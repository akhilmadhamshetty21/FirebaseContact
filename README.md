# FirebaseContact  

## Part 1: SignUp  
Create the Signup screen with the following requirements:
1. Clicking the “Cancel” button should finish the Signup Screen and start the Login Screen.
2. The user should provide their first name, last name, email, password and password
confirmation. Preform the required validation(the given password and the repeated
password must match). Clicking the “Sign Up” button should submit the user’s
information to firebase signup.  
        a) If the signup is not successful display an error message indicating the error
        message received from firebase.  
        b) If the signup is successful, then display a Toast indicating that the user has been
        created. Then start the Contacts List Screen and finish the Signup Screen.  

## Part 2: Login  
This is the launcher screen of you app.The requirements are as follows:     
3. The user should provide their email and password. The provided credentials should
be used to authenticate the user using firebase login. Clicking the “Login” button
should submit the login information into firebase to verify the user’s credentials.  
a) If the user is successfully logged in then start the Contacts List Screen, and
finish the Login Screen.  
b) If the user is not successfully logged in, then show a toast message indicating
that the login was not successful.  
4. Clicking the “Sign Up” button should start the Signup Screen Figure 1(b), and finish
the login Screen.  

## Part 3: Contacts List:  
The contact list should display a ListView or RecyclerView showing the list of contacts
retrieved from firebase. Below are the requirements:  
1. Retrieve the contacts stored for the currently logged in user from firebase. Note that
this is a multi user app and each user should have access to only their stored
contacts.  
2. The list of contacts should be displayed using a ListView or RecyclerView.  
3. Long press on a list item should delete the contact and refresh the list.  
4. Clicking on the “Create New Contact” button should display the “Create New
Contact” screen.  
5. Clicking on the “Logout” button should logout the current user, start the “Login”
screen and finish the current screen.  
## Part 4: Create New Contact :  
This screen is used to create a new contact, The requirements are as follows:
1. Clicking the camera icon should open the camera app and you need to take a profile
photo. Set the photo at the place of the camera icon, see Figure 1(f). If you do not
take the photo, you can still save the contact, but with the default image provided as
a support file.    
2. Upon clicking the “Submit” button the new contact should be created and sent to
firebase to be stored under the contacts for the currently logged in user. Finally the
screen should be finished, which will show the “Contacts List”.    
       • The “Contacts List” screen should be refreshed to display the updated list.  
