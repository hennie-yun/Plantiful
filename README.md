---
plan + tiful (계획으로 가득 찬) 
===
* * *
<br><br/>

![111](https://github.com/hennie-yun/Plantiful/assets/129652734/8b35c4c6-c7f1-4099-bcea-ef161ff955e1)


<br><br/>
## 🔗 Link
- **Deploy site :** 배포사이트 업로드 

- **Github Repository :** <br/>
https://github.com/hennie-yun/Plantiful <br/>
https://github.com/hennie-yun/vscodePlantiful
  

- **Presentation :** pdf 파일 첨부예정


<br><br/>
## 📖 Summary
> ```Introduce Project```
>  - [💡 Motivation](#-motivation)  
>  - [📌 Features](#-features)  
>  - [🛠 Stack](#-stack)
>  - [💿 Installation](#-installation)  
>  - [📸 Site Screenshot](#-site-screenshot)
>  - [🧑🏻‍💻 Team Member](#-team-member)
#
* * *
#
# Introduce Project
plan + tiful은 KOSTA 257기 파이널 프로젝트로 진행 되었습니다.
2023.06.12 ~ 2021.07.12 총 31일간 설계 및 개발하였으며, 5명의 팀원이 함께 하였습니다.
<br><br/>
## 💡 Motivation

팀원들과의 회의를 통해 단순히 개인 일정을 관리하고 체크하는 기능을 가진 기존 스케줄러 서비스와는 다른 효율적인 사이트를 만들고자 하였습니다. 

plan + tiful 과 기존 서비스와의 차별점은 아래와 같습니다.

-  **그룹기능 :** 사용자가 직접 그룹을 생성하여 개인 뿐만 아니라, 공통된 스케줄을 가진 사람들과 손쉽게 공유할 수 있습니다. 이를 통해 팀 프로젝트, 가족 일정, 친구들과의 약속 등 다양한 그룹에서 원활한 의사소통이 가능합니다. 

-  **OTT 파티 기능 :**  매달 정기적으로 결제하는 OTT 서비스도 스케줄에 포함시킬 수 있습니다. 사용자들은 자신이 구독하고 있는 OTT 사이트를 스케줄에 추가하고, 해당 OTT를 함께 시청할 파티원들을 모집 할 수 있습니다. 

-  **채팅기능 :**  OTT 모임이 생성 되었다면 자동으로 채팅 기능을 제공합니다. 스케줄에 관련된 의논이나 소통을 자유롭게 할 수 있으며 일정 관리와 소통이 한 곳에서 가능해집니다.

-  **결제서비스 :**  OTT 서비스를 위한 편리한 결제 서비스를 제공합니다. 사용자들은 스케줄에 추가된 OTT 구독에 대한 결제를 쉽게 처리할 수 있으며, 그룹원들과 함께 원활한 과금 관리가 가능합니다.

-  **공연예술 스케줄 기능 :** 사용자들은 각종 공연, 전시, 콘서트 등 다양한 문화 행사 정보를 확인하고, 관심 있는 이벤트를 발견하면 스케줄에 추가할 수 있는 기능을 제공합니다. 

이처럼 plan + tiful은 스케줄 관리에서 더 나아가 다양한 기능들을 통합하여 사용자들에게 편리한 경험을 제공하는데 중점을 두어 프로젝트를 진행 하였습니다. 

<br><br/>
## 📌 Features
- Kakao, Naver 소셜 로그인
- 이메일 인증
- kakao, naver 캘린더 연동
- kakao 메세지 보내기
- 실시간 채팅 
- 결제 기능 
- 본인인증  
<br><br/>
## 🛠 Stack
### ✔️ Front-End
- jQuery v.1.12.4
- Bootstrap v5.3.0
- FullCalendar v5.5.1
- DayJs
- vuetify
- webstomp-client
- sockjs-client


### ✔️ Back-End
- Java v17
- Maven v3.6.3
- Spring framework v5.3.7
- JackSon v2.12.3
- Lombok v1.18.20
- Spring Data JPA

### ✔️ DataBase
- Oracle v22.2.1

### ✔️ Server
- Apache Tomcat v9.0.43

### ✔️ Open API
- Kakao Login API
- Naver Login API
- Iamport API 
- KOPIS API
- Kakao Map API
- Kakao talk calendar API
- Naver calendar API
- Kakao messeage API

<br><br/>
## 💿 Installation
> #### ℹ️ Working with plan + tiful (this project) in your IDE
### Steps:
#### ✔️ Prerequisites
아래의 항목들이 설치되어 있어야 합니다.
- Java 8 or newer
- Maven 3.6.3 or 3.6+ (http://maven.apache.org/install.html)
- git command line tool (https://help.github.com/articles/set-up-git)
- Oracle 10.5+
- Tomcat 9.0.43 or 9+
- Your prefered IDE
    - Spring Tools Suite(STS)
    - Eclipse IDE
   
#### 1️⃣ 프로젝트 클론
~~~ 
git clone https://github.com/hennie-yun/Plantiful
~~~
---
#### 2️⃣ _STS_ _에서  
   1. clone 한 프로젝트를 import  
      ```File -> Import -> Maven -> Existing Maven project```
   2. Maven sources loading  
      ```프로젝트 우클릭 > Run As > Maven install```
      
#### 2️⃣ _Eclipse_ _에서  
   1. clone 한 프로젝트를 open  
      ```File -> Open```
   2. Maven sources loading  
      ```프로젝트 우클릭 > Maven > Generates sources and Update Folders```
      
---
#### 3️⃣ properties 파일 5개 생성 및 설정
   >> 1. src/main/resources/application.properties/```# oracle set```
   >   ~~~
   >   spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
   >   spring.datasource.url=jdbc:oracle:thin:@localhost:1521/xe
   >   spring.datasource.username=<YOUR_DB_USERNAME>
   >   spring.datasource.password=<YOUR_DB_PASSWORD>
   >   ~~~
   #
   >> 2. src/main/resources/application.properties/```#multipart```
   >   ~~~
   >   spring.servlet.multipart.location=C:/plantiful/
   >   spring.servlet.multipart.max-file-size=5MB
   >   ~~~
   #
   >> 3. src/main/resources/application.properties/```#mail```
   >   ~~~
   >   spring.mail.host=smtp.gmail.com
   >   spring.mail.port=587
   >   spring.mail.username=<YOUR_EMAIL>
   >   spring.mail.password=<YOUR_EMAILPWD>
   >   spring.mail.properties.mail.smtp.auth=true
   >   spring.mail.properties.mail.smtp.starttls.enable=true
   >   spring.mail.properties.mail.smtp.connectiontimeout=18000
   >   spring.mail.properties.mail.smtp.timeout=18000
   >   spring.mail.properties.mail.smtp.writetimeout=18000
   >   ~~~
   #
   >> 4. src/main/resources/application.properties/```#jpa```
   >   ~~~
   >   spring.jpa.generate-ddl=true
   >   spring.jpa.database=oracle
   >   spring.jpa.show-sql=true
   >   ~~~
   #
   >> 5. src/main/resources/application.properties/```#encoding```
   >   ~~~
   >   server.servlet.encoding.charset=UTF-8
   >   server.servlet.encoding.enabled=true
   >   server.servlet.encoding.force=true
   >   ~~~

---
#### 4️⃣ _STS_ or _Eclipse_ > Tomcat Configure 설정 후 > Run
<br><br/>
## 📸 Site Screenshot
[Join]
![image](https://github.com/hennie-yun/Plantiful/assets/129652734/13a74d55-a7b5-406c-8de0-68e264011a83)
[Login]
![image](https://github.com/hennie-yun/Plantiful/assets/129652734/0ba616eb-5ad0-4208-bfdb-337506386c29)
[FindPwd]
![image](https://github.com/hennie-yun/Plantiful/assets/129652734/769f8fc5-4a63-410b-bd9c-026615959b0c)
[Main]
![image](https://github.com/hennie-yun/Plantiful/assets/129652734/2e6bfcc8-31de-4196-80aa-046adfdbb822)

[Making Group]
![image](https://github.com/hennie-yun/Plantiful/assets/129652734/a95a7010-f99e-4c9e-87b6-2ac56856ba0b)

## 🧑🏻‍💻 Team Member
- 윤해현 : https://github.com/hennie-yun
- 정근희 : https://github.com/rmsgml7553
- 이주원 : https://github.com/leejuwon92
- 남영우 : https://github.com/youngwoo0624
- 이서연 : https://github.com/seoyeonDev


