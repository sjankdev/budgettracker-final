# SPENDER (Budget Tracker System) (Java/Spring Web Application) 💰

The goal is to create a fully responsive web-based Budget Tracker application based on the ***Model View Controller (MVC) Architecture*** made using Thymeleaf.

I implemented token-based authentication with Spring Security & JWT. Users have forms for login and registration. 

![image](https://user-images.githubusercontent.com/120321222/212981035-4fe4e137-7bff-46a6-a737-731b90788a28.png)
![image](https://user-images.githubusercontent.com/120321222/212980990-6405946a-1f12-4f0b-b07e-6c97ecc9944e.png)

I added validation for all fields during registration and login. 

After the user has successfully logged in, he is forwarded to the home page.

![image](https://user-images.githubusercontent.com/120321222/212981323-15e63bc0-e67a-4c5a-bd71-90224f9277c9.png)

The home page has a logo, navigation menu, header and two buttons for quick user navigation through the pages.

The user also has a wallets page. On that page, user can see the total balance calculated from all the wallets. He can delete each of the wallets as well as update them, and also has the option to add a new wallet. 

![image](https://user-images.githubusercontent.com/120321222/212983172-857cd025-b028-41a5-9c2b-de0a111d6339.png)

This is form to update wallet:

![image](https://user-images.githubusercontent.com/120321222/212982221-7fc79f57-9af7-4a74-a0c8-8bc7c9b6c5a5.png)

The user can also create transactions for each wallet separately, i.e. spending. Each wallet contains an option for income and expense transaction.

Tis is form for income transaction:

![image](https://user-images.githubusercontent.com/120321222/212982661-4968e1c9-cb1a-4778-a198-9c6dc0545b06.png)

The user can again see the name of the wallet with which he will make the transaction as well as his balance.
After that, he enters the amount, which cannot be negative, a comment as desired, selects the date, and the income transaction category.

Finally, there is a button to confirm the transaction, to switch to the expense transaction, and to cancel it.

Here is the form for expense transaction:


![image](https://user-images.githubusercontent.com/120321222/212982987-7c06bed4-1608-49c7-afc8-f8f59e30dd20.png)
