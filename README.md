# Picture-Publishing-Service-Java-Spring
a website where registered and logged in users can upload pictures for acceptance or rejection. An administrator will login and review all submissions. Accepted pictures will be assigned a URL which can be accessed by all users (without login). Rejected pictures have their pictures removed but the metadata remains.

# Detailed Requirements
End-users can register. </br>
○ Registration information: email address, password</br>
  ● Landing page shows all accepted pictures' URLs</br>
○ NON-logged in users can click on the links to see the pictures</br>
● Logged in users can upload a picture along with following fields:</br>
○ description</br>
○ category</br>
○ attachment (up to 2 Mb) (jpg, png, gif only)</br>
● An administrative user can login to a separate admin page and can add other admin users</br>

● Administrative user sees list of uploaded pictures</br>
○ list of all uploaded, unprocessed pictures</br>

● Admin can select a picture to process it</br>
○  showing description, category, and displays
picture</br>
■ has two way "Accept", "Reject"</br>
○ if "Accept" is pressed, a URL is autogenerated for that picture, and can be
displayed on the Landing page</br>
○ if "Reject" is pressed, the picture file is removed from storage and the data record marked as
Rejected</br>
