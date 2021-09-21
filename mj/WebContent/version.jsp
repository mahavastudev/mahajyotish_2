<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
============================================================================================================================================================
Version 2.5.2.0
1.Menus for Downlads : Changeed in both MJ & AMRC.
2.AMRC,MJ multiple role ids allowed from property file.
3.MVCODE add while adding user from Add User.
4.View User will show result on search.
============================================================================================================================================================
Version 2.5.1.1
Handling for Multiple Admin login

============================================================================================================================================================
Version 2.5.1.0 
Condition Handled :- SING UP WITH DIFFERENT INTERFACE
TEST CASE 1
	if user is not subscribe to system :- sign up , activation link, validation with email, register for role
TEST CASE 2
	if user is already subscribe with same role, then show message of already subscribe.
TEST CASE 3
	if user is already subscribe for another role, then no activation mail sent to user, only subscribe with new role.
TEST CASE 4
	if user is not subscribe in soho system , then we can’t subscribe the user to system.
============================================================================================================================================================
Version 2.5.0.0 
Condition Handled :-

Login jsp for AMRC ROLE : jsp/loginAMRC.jsp : only allow AMRC user 
Login jsp for ADMIN ROLE : jsp/loginAdmin.jsp : only allow ADMIN USER [admin@gmail.com] if valid cred 
Login jsp for MahaJyotish ROLE : jsp/loginMahajyotish.jsp : only allow MahaJyotish Role

Create horoscope_metadata[table] method to save horoscope on generation of kindle (needs to be created table schema dump )

Write footer in each page of PDF



How to deploy separate war for roles define above:-
Changes after war deployment :-

Change welcome file name in web.xml 
Change content in index.jsp file according to web.xml


We can still do login for admin just type admin.action after projectName each type when you want to log in



alter table adminlogin add column role_id_2 int(10) not null default -1 after role_id;

============================================================================================================================================================ 
Version 2.4.0.3 
new login work 

============================================================================================================================================================


============================================================================================================================================================ 
Version 2.4.0.2 Circle name in center & 3 page pdf astro script table header name changed,
after generate kundli hide cuspchat and show [Download/Email your horoscope from menu options.]


============================================================================================================================================================
version 2.4.0.1
change property file parameter related to generate pdf 
change header and footer in pdf (basic kundli)
============================================================================================================================================================
version 2.3.0.1
========================================================================================================================================================
version 2.3.0.0
change structure of a table in adminlogin and astro_request_log table , change the hit of parasharlight url accoding to the add transit and transit location
============================================================================================================================================================
version 2.2.0.0
correct vimshotarri chart , update side nav bar links,  add basic kundli pdf in kundli pdf ,add transit kundli state city and country in generate kundli module 
============================================================================================================================================================
version 2.1.0.0
Transit Kundli Details in pdf with seperate kundli Circle image for transit.
============================================================================================================================================================
version 2.4.0.0
Added Anticlockwise circle on role based. (To enable this select   Circle AntiClockWise in modify role) .
Solved Vimshottari Dasha table on click calculation.
Added Generate kundli without login. [Generate Kundli Without Login]
change hit of signup and trouble in login form 
============================================================================================================================================================
</body>
</html>