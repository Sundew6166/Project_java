**_สรุปสิ่งที่พัฒนาในแต่ละสัปดาห์หรือแต่ละครั้งที่ commit_**
    <br>- week 1 2020/09/14 เป็นการ add maven เข้าไปในโปรเจ็ค 
    <br>- week 1 2020/09/18 มีการสร้าง package simple และสร้าง class signInController และ สร้าง SignIn.fxml ไปไว้ที่ directory resources 
                        <br>ใน class Main มีการใช้คำสั่งเชื่อมไปยังหน้า SignIn.fxml ใน pom.xml มีการใส่คำสั่ง plugin เพื่อใช้งาน
    <br>- week 2 2020/09/21 ออกแบบ GUI ในแต่ละหน้าที่ใช้ทำงานอย่างคร่าวๆ และตั้งค่าปุ่มในการเชื่อมไปยังหน้าต่างๆ มีการเพิ่มคำสั่งใน pom.xml
    <br>- week 2 2020/09/23 มีการแก้ไข groupId จาก sample เป็น SundewCondo และแก้ไขคำสั่งเชื่อมหน้าใน class StaffChoiceController 
    <br>- week 3 2020/09/29 สร้าง package controllers, models และย้าย class controller ต่างๆไปไว้ใน package controller ส่วน package models 
                        <br>มีการสร้าง class Account, AccountList เพื่อเก็บ account และ add ใส่ arrayList เพิ่มการทำงานในหน้า Sign In 
                        <br>เช็ค Username and Password เพื่อเข้าไปทำงานต่อไป 
    <br>- week 4 2020/10/06 มีการสร้าง directory data และสร้างไฟล์ accounts.csv เพื่อเก็บ account ลบ class LoginController, accountList 
                        <br>และทำการสร้าง class SignInController ใหม่ และเขียน code การ Sign In เข้าใช้งานใหม่ สร้าง package service 
    <br>- week 5 2020/10/16 สร้างไฟล์ room.csv ใน directory data เพิ่มการทำงานของแต่หน้าหน้า controller แก้ไขการเก็บ account ใน Class Account 
                        <br>แก้ไข GUI เพิ่ม class room ใน package models 
    <br>- week 6 2020/10/19 เพิ่ม Controller และ เพิ่มไฟล์ StringConfiguration ใน package service เพิ่่ม README.md 
    
    -------------------------------------------------------------------------------------------------------------------------------------------

<br>**_การวางโครงสร้างไฟล์_**
    <br>Directory data 
       <br> - เก็บ file accounta.csv ข้อมูลของ account ที่เข้า sign in ระบบ
       <br> - เก็บ file room.csv ข้อมูลของห้องในคอนโด
    <br>Directory src/main แบ่ง directory ย่อยได้อีก 2 ส่วน 
        <br>1. Directory java/SundewCondo มี Directory ย่อยอีก 3 ส่วน คือ 
            <br>1.1 directory controllers 
            <br>1.2 directory models
           <br>1.3 directory service
            <br>และ มีไฟล์ README.md, Main.java
        <br>2. Directory resources 
            <br>- เก็บไฟล์ที่เป็นนามสกุล fxml (.fxml)
            <br>- Directory image เก็บรูปต่างๆ
   
    -------------------------------------------------------------------------------------------------------------------------------------------

<br>**_วิธีการติดตั้งโปรแกรม_** 
    <br>- กรณีที่ไม่สามารถเปิดโปรแกรมได้จากการ double click ให้เลือกเปิด terminal, bash, commant prompt แล้วใช้คำสั่ง java -jar 6210450717.jar
    
    -------------------------------------------------------------------------------------------------------------------------------------------

<br>**_วิธีเริ่มต้นใช้งาน และ username/password สำหรับทดสอบการเข้าสู่ระบบ_**
    <br>- administrator Username : admin Password : 1111
    <br>- เจ้าหน้าที่ส่วนกลาง Username : s0001 Password : 2222, Username : s0002 Password : 1234