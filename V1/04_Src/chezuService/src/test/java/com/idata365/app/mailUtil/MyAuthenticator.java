package com.idata365.app.mailUtil;
import javax.mail.*;   

public class MyAuthenticator extends Authenticator{   
    String userName=null;   
    String password=null;   
        
    public MyAuthenticator(){   
    }   
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    } 
    
    public static void main(String[] args){   
        //这个类主要是设置邮件   
//    	System.setProperty("java.net.preferIPv4Stack", "true");
        MailSenderInfo mailInfo = new MailSenderInfo();     
        mailInfo.setMailServerHost("smtp.exmail.qq.com");    
        mailInfo.setMailServerPort("25");    
        mailInfo.setValidate(true);    
        mailInfo.setUserName("lanjinsheng@idata365.com");    
        mailInfo.setPassword("Lan2962143");//您的邮箱密码    
        mailInfo.setFromAddress("lanjinsheng@idata365.com");    
        mailInfo.setToAddress("lanjinsheng@idata365.com");    
        mailInfo.setSubject("设置邮箱标题 如http://www.guihua.org 中国桂花网");    
        mailInfo.setContent("设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站==");    
           //这个类主要来发送邮件   
        SimpleMailSender sms = new SimpleMailSender();   
            sms.sendTextMail(mailInfo);//发送文体格式    
   }  
}   